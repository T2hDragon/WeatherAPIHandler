package ee.icd0004_2020f_weatherproject.api.response;

import ee.icd0004_2020f_weatherproject.api.response.report.City;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.*;
import java.util.stream.Collectors;

import static ee.icd0004_2020f_weatherproject.DateTimeFunctions.areSameDays;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastEntity implements IApiEntity {

    private WeatherEntity[] list;

    private City city;

    /**
     * Get all measurements of chosen day
     * @param day chosen day
     * @return measurements of chosen day
     */
    public List<WeatherEntity> takeDayWeatherMeasurements(Date day) {
        if (list == null) return new ArrayList<>();
        return Arrays.stream(list)
                .filter(weatherMeasurement -> areSameDays(weatherMeasurement.getDate(), day))
                .collect(Collectors.toList());
    }

    /**
     * Get all weather measurements of n-th day counting from current.
     * So if {@code dayNumber} equals 0, then take all today's weather measurements listed in {@code list}
     * @param n day index taking current day as 0
     * @return measurements of nth day
     */
    public List<WeatherEntity> takeNthDayMeasurements(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, n);
        Date requestedDay = calendar.getTime();
        return takeDayWeatherMeasurements(requestedDay);
    }

}
