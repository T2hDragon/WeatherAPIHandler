package ee.icd0004_2020f_weatherproject.weather_time_tests.mock;

import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.api.response.report.Coordinates;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.WeatherReportDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Integration testing with Mockito.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherReportDetailsMockTest {

    @Mock
    WeatherApi weatherApiMock;

    public static WeatherEntity createWeatherEntityWithDefaultValues() {
        WeatherEntity weatherEntityData = new WeatherEntity();
        weatherEntityData.setName("");
        weatherEntityData.setCoord(new Coordinates(0d, 0d));

        return weatherEntityData;
    }

    @Test
    public void should_return_weather_report_for_given_city() {
        String city = "Tokyo";
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();
        currentWeatherEntityDataStub.setName(city);

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        WeatherReportDetails weatherReportDetails = weatherTime.getWeatherReportDetails(city);

        assertThat(weatherReportDetails.getCity())
                .isEqualTo(city);

    }

    @Test
    public void should_have_given_city_coordinates_in_main_details() {
        String city = "Kyoto";
        Coordinates cityCoordinates = new Coordinates(24.75, 59.44);
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();
        currentWeatherEntityDataStub.setName(city);
        currentWeatherEntityDataStub.setCoord(cityCoordinates);

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        WeatherReportDetails weatherReportDetails = weatherTime.getWeatherReportDetails(city);

        assertThat(weatherReportDetails.getCoordinates())
                .isEqualTo("59.44, 24.75");
    }

    @Test
    public void should_have_default_temp_unit_in_main_details_when_no_unit_specified() {
        String city = "Nagasaki";
        TemperatureUnit defaultTempUnit = TemperatureUnit.CELSIUS;
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        WeatherReportDetails weatherReportDetails = weatherTime.getWeatherReportDetails(city);

        assertThat(weatherReportDetails.getTemperatureUnit())
                .isEqualTo(defaultTempUnit.getStringRepresentation());
    }

    @Test
    public void should_have_specified_temp_unit_in_main_details() {
        String city = "Tallinn";
        TemperatureUnit unit = TemperatureUnit.FAHRENHEIT;
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        WeatherReportDetails weatherReportDetails = weatherTime.getWeatherReportDetails(city, unit);

        assertThat(weatherReportDetails.getTemperatureUnit())
                .isEqualTo(unit.getStringRepresentation());
    }

}


