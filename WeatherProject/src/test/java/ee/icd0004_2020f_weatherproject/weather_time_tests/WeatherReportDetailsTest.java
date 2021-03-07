package ee.icd0004_2020f_weatherproject.weather_time_tests;

import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.WeatherReportDetails;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherReportDetailsTest {


    private static WeatherTime weatherTime;

    @BeforeClass
    public static void setUp() {
        weatherTime = new WeatherTime(new WeatherApi());
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_unknown_city_requested() {
        String unknownCity = "Nju Jork";

        weatherTime.getWeatherReportDetails(unknownCity);
    }


    @Test
    public void should_have_given_city_details() {
        String city = "Narva";

        WeatherReportDetails weatherReportDetails = weatherTime.getWeatherReportDetails(city);

        assertThat(weatherReportDetails.getCity())
                .isEqualTo(city);
    }

    @Test
    public void should_have_given_city_coordinates_in_details() {
        String city = "Tallinn";

        WeatherReportDetails weatherReport = weatherTime.getWeatherReportDetails(city);

        assertThat(weatherReport.getCoordinates())
                .isEqualTo("59.44, 24.75");
    }

    @Test
    public void should_have_default_temp_unit_in_climate_when_no_unit_specified() {
        String city = "Tallinn";

        String defaultTempUnit = "Celsius";

        WeatherReportDetails currentWeatherReport = weatherTime.getWeatherReportDetails(city);

        assertThat(currentWeatherReport.getTemperatureUnit())
            .isEqualTo(defaultTempUnit);
    }

    @Test
    public void should_have_specified_temp_unit_in_main_details() {
        String city = "Tallinn";
        TemperatureUnit unit = TemperatureUnit.FAHRENHEIT;

        WeatherReportDetails weatherReportDetails = weatherTime.getWeatherReportDetails(city, unit);

        assertThat(weatherReportDetails.getTemperatureUnit())
                .isEqualTo(unit.getStringRepresentation());
    }

}
