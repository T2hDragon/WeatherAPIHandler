package ee.icd0004_2020f_weatherproject.report;

import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.api.response.report.Climate;
import ee.icd0004_2020f_weatherproject.enums.TimeRange;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ee.icd0004_2020f_weatherproject.DateTimeFunctions.areSameDays;
import static ee.icd0004_2020f_weatherproject.DateTimeFunctions.getHourOfDay;

@Data
@NoArgsConstructor
public class CurrentWeatherReport {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private String date;
    private Float temperature;
    private int humidity;
    private int pressure;

    public CurrentWeatherReport(Climate climateTemperatureDetails) {
        temperature = climateTemperatureDetails.getTemp();
        humidity = climateTemperatureDetails.getHumidity();
        pressure = climateTemperatureDetails.getPressure();

        date = new SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().getTime());
    }

    public CurrentWeatherReport(float temperature, int humidity, int pressure, Date date) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;

        this.date = new SimpleDateFormat(DATE_PATTERN).format(date);
    }

    /**
     * take list of Weather Entities (measurements) and compute weather attributes average values
     * (taking into account only measurements in given time range).
     * Return new weather report with computed average values.
     * Throws exception, if measurements do not belong to same day ({@code CurrentWeatherRecord} should have one particular day)
     * @param weatherMeasurements provided weather measurements
     * @param range time range in which average values are computed
     * @return new CurrentWeatherRecord with one day's weather average values
     */
    public static CurrentWeatherReport computeAverageWeatherAndGetReport(
            List<WeatherEntity> weatherMeasurements, TimeRange range) {

        if (weatherMeasurements == null || weatherMeasurements.size() == 0) {
            throw new RuntimeException("Please, specify measurements");
        }
        Date measurementsDate = weatherMeasurements.get(0).getDate();
        if (!weatherMeasurements.stream().allMatch(measurement -> areSameDays(measurement.getDate(), measurementsDate))) {
            throw new RuntimeException("Please, give one day's weather measurements to get reasonable result");
        }

        weatherMeasurements = weatherMeasurements.stream()
                .filter(measurement -> {
                    int hour = getHourOfDay(measurement.getDate());
                    return range.isInTimeRange(hour);
                }).collect(Collectors.toList());

        float temperature = roundToPrecision(weatherMeasurements.stream()
                .mapToDouble(measurement -> measurement.getMain().getTemp())
                .average().orElse(0));
        int humidity = (int) Math.round(weatherMeasurements.stream()
                .mapToInt(measurement -> measurement.getMain().getHumidity())
                .average().orElse(0));
        int pressure = (int) Math.round(weatherMeasurements.stream()
                .mapToInt(measurement -> measurement.getMain().getPressure())
                .average().orElse(0));

        return new CurrentWeatherReport(temperature, humidity, pressure, measurementsDate);
    }

    private static float roundToPrecision(double a) {
        // https://www.baeldung.com/java-round-decimal-number
        BigDecimal bd = new BigDecimal(Double.toString(a));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
