package ee.icd0004_2020f_weatherproject.weather_time_tests.mock;

import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.api.response.report.Climate;
import ee.icd0004_2020f_weatherproject.api.response.report.Coordinates;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.CurrentWeatherReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentWeatherReportMockTest {

    @Mock
    WeatherApi weatherApiMock;

    public static WeatherEntity createWeatherEntityWithDefaultValues() {
        WeatherEntity weatherEntityData = new WeatherEntity();
        Climate climate = new Climate();
        climate.setTemp(-5f);
        climate.setHumidity(75);
        climate.setPressure(985);

        weatherEntityData.setName("Tallinn");
        weatherEntityData.setCoord(new Coordinates(24.75, 59.44));

        weatherEntityData.setMain(climate);

        return weatherEntityData;
    }

    @Test
    public void should_have_given_temperature_in_current_weather_report() {
        String city = "Tallinn";
        float temperature = -10000;
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();
        currentWeatherEntityDataStub.setName(city);
        currentWeatherEntityDataStub.getMain().setTemp(temperature);

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);

        assertThat(currentWeatherReport.getTemperature())
                .isEqualTo(temperature);
    }

    @Test
    public void should_have_given_humidity_in_current_weather_report() {
        String city = "Tallinn";
        int humidity = 999;
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();
        currentWeatherEntityDataStub.setName(city);
        currentWeatherEntityDataStub.getMain().setHumidity(humidity);

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);

        assertThat(currentWeatherReport.getHumidity())
                .isEqualTo(humidity);
    }

    @Test
    public void should_have_given_pressure_in_current_weather_report() {
        String city = "Tallinn";
        int pressure = 10;
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        WeatherEntity currentWeatherEntityDataStub = createWeatherEntityWithDefaultValues();
        currentWeatherEntityDataStub.setName(city);
        currentWeatherEntityDataStub.getMain().setPressure(pressure);

        when(weatherApiMock.getTargetClassEntity(eq(WeatherEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(currentWeatherEntityDataStub);

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);

        assertThat(currentWeatherReport.getPressure())
                .isEqualTo(pressure);
    }

}

