package ee.icd0004_2020f_weatherproject.weather_time_tests;

import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.CurrentWeatherReport;
import ee.icd0004_2020f_weatherproject.report.ForecastReport;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ForecastReportTest {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private static WeatherTime weatherTime;

    @BeforeClass
    public static void setUp() {
        weatherTime = new WeatherTime(new WeatherApi());
    }

    @Test
    public void should_have_forecast_be_set() {
        String city = "Riga";

        ForecastReport forecastReport = weatherTime.getForecastReport(city);

        assertThat(forecastReport)
                .isNotNull();

    }

    @Test
    public void should_have_three_day_forecast_in_current_forecast_report() {
        String city = "Riga";

        int expectedForecastLength = 3;

        ForecastReport forecastReport = weatherTime.getForecastReport(city);
        CurrentWeatherReport[] forecast = forecastReport.getForecast();

        assertEquals(expectedForecastLength, forecast.length);
    }

    @Test
    public void should_not_have_current_day_in_forecast_report() {
        String city = "Moskva";

        String currentDate = new SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().getTime());

        ForecastReport forecastReport = weatherTime.getForecastReport(city);
        CurrentWeatherReport[] forecast = forecastReport.getForecast();

        List<String> dates = Arrays.stream(forecast)
                .map(measurement -> measurement.getDate())
                .collect(Collectors.toList());

        assertFalse(String.format("current date %s should not be listed in forecast", currentDate),
                dates.contains(currentDate));
    }

    @Test
    public void should_have_temperature_measurement_for_each_day_in_forecast_report() {
        String city = "Tartu";

        ForecastReport forecastReport = weatherTime.getForecastReport(city, TemperatureUnit.CELSIUS);
        CurrentWeatherReport[] forecast = forecastReport.getForecast();

        Float temperature;
        int i = 0;
        for (CurrentWeatherReport dayMeasurements : forecast) {
            temperature = dayMeasurements.getTemperature();
            assertThat(temperature)
                    .overridingErrorMessage(String.format("For day %d temperature was not set", ++i))
                    .isNotNull()
                    .overridingErrorMessage(String.format(
                            "For day %d temperature value (%s) was impossibly low for given city %s",
                            i,
                            temperature,
                            city))
                    .isGreaterThan(-80)
                    .overridingErrorMessage(String.format(
                            "For day %d temperature value (%s) was impossibly high for given city %s",
                            i,
                            temperature,
                            city))
                    .isLessThan(100);
        }
    }

    @Test
    public void should_have_humidity_measurement_for_each_day_in_forecast_report() {
        String city = "Tallinn";

        ForecastReport forecastReport = weatherTime.getForecastReport(city);
        CurrentWeatherReport[] forecast = forecastReport.getForecast();

        int humidity;
        int i = 0;
        for (CurrentWeatherReport dayMeasurements : forecast) {
            humidity = dayMeasurements.getHumidity();
            assertThat(humidity).overridingErrorMessage(
                    "Humidity percentage with probability of 99% should fall in a range 1-100%, but was "
                            + humidity + " at day " + ++i)
                    .isGreaterThan(0)
                    .isLessThan(101);
        }
    }

    @Test
    public void should_have_pressure_measurement_for_each_day_in_forecast_report() {
        String city = "Tallinn";

        ForecastReport forecastReport = weatherTime.getForecastReport(city);
        CurrentWeatherReport[] forecast = forecastReport.getForecast();

        int pressure;
        int i = 0;
        for (CurrentWeatherReport dayMeasurements : forecast) {
            pressure = dayMeasurements.getPressure();
            assertThat(pressure).overridingErrorMessage(
                    "Air pressure in Estonia (city: Tallinn) is very unlikely to not be in range (930hPa - 1065hPa), but was "
                            + pressure + " at day" + ++i
                            + "\n Source: (https://www.ilmateenistus.ee/kliima/rekordid/ohurohk/?lang=en)")
                    .isGreaterThan(929)
                    .isLessThan(1066);
        }
    }

}
