package ee.icd0004_2020f_weatherproject.weather_time_tests.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.api.response.ForecastEntity;
import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.api.response.report.Climate;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.ForecastReport;
import ee.icd0004_2020f_weatherproject.weather_time_tests.mock.dto.Measurement;
import ee.icd0004_2020f_weatherproject.weather_time_tests.mock.dto.StubDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForecastReportMockTest {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String PATH_TO_STUB_DATA = "mock/forecast/ForecastStub.json";
    private StubDTO stubData;

    @Mock
    WeatherApi weatherApiMock;

    @Test
    public void should_have_correct_average_temperatures_in_forecast_record() {
        String city = "Fuffu";
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        ForecastEntity forecastEntityDataStub = createForecastEntityWithStubValues(PATH_TO_STUB_DATA);

        List<Float> averageTemperatures = stubData.getFullDay().getAverageTempValues();

        when(weatherApiMock.getTargetClassEntity(eq(ForecastEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(forecastEntityDataStub);

        ForecastReport forecastReport = weatherTime.getForecastReport(city);

        float forecastTemp;
        float preCalculatedAverage;
        for (int i = 0; i < 3; i++) {
            forecastTemp = forecastReport.getForecast()[i].getTemperature();
            preCalculatedAverage = averageTemperatures.get(i);
            assertEquals("On day " + (i + 1) + " average temperature should be "
                    + preCalculatedAverage + " but was " + forecastTemp,
                    preCalculatedAverage,
                    forecastTemp,
                    0.1);
        }
    }

    @Test
    public void should_have_correct_average_humidity_values_in_forecast_record() {
        String city = "Duddaak";
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        ForecastEntity forecastEntityDataStub = createForecastEntityWithStubValues(PATH_TO_STUB_DATA);

        List<Integer> averageHumidityValues = stubData.getFullDay().getAverageHumidityValues();

        when(weatherApiMock.getTargetClassEntity(eq(ForecastEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(forecastEntityDataStub);

        ForecastReport forecastReport = weatherTime.getForecastReport(city);

        int forecastHumidity;
        int preCalculatedAverage;
        for (int i = 0; i < 3; i++) {
            forecastHumidity = forecastReport.getForecast()[i].getHumidity();
            preCalculatedAverage = averageHumidityValues.get(i);
            assertEquals("On day " + (i + 1) + " average humidity should be "
                            + preCalculatedAverage + " but was " + forecastHumidity,
                    preCalculatedAverage,
                    forecastHumidity,
                    0);
        }
    }

    @Test
    public void should_have_correct_average_pressure_values_in_forecast_record() {
        String city = "Burunduk";
        WeatherTime weatherTime = new WeatherTime(weatherApiMock);

        ForecastEntity forecastEntityDataStub = createForecastEntityWithStubValues(PATH_TO_STUB_DATA);

        List<Integer> averagePressureValues = stubData.getFullDay().getAveragePressureValues();

        when(weatherApiMock.getTargetClassEntity(eq(ForecastEntity.class), anyString(), any(TemperatureUnit.class)))
                .thenReturn(forecastEntityDataStub);

        ForecastReport forecastReport = weatherTime.getForecastReport(city);

        int forecastPressure;
        int preCalculatedAverage;
        for (int i = 0; i < 3; i++) {
            forecastPressure = forecastReport.getForecast()[i].getPressure();
            preCalculatedAverage = averagePressureValues.get(i);
            assertEquals("On day " + (i + 1) + " average pressure should be "
                            + preCalculatedAverage + " but was " + forecastPressure,
                    preCalculatedAverage,
                    forecastPressure,
                    0);
        }
    }





    private void loadStubData(String path) {

        ClassLoader loader = getClass().getClassLoader();
        try {
            File stubJson = new File(loader.getResource(path).toURI());
            ObjectMapper om = new ObjectMapper();
            stubData = om.readValue(stubJson, StubDTO.class);
            stubData.setResourcePath(path);
        } catch (URISyntaxException uriExc) {
            throw new RuntimeException("Could not read stub data from given path " + path);
        } catch (IOException ioExc) {
            throw new RuntimeException("Could not convert stub data into data transfer obj");
        }
    }

    private WeatherEntity createWeatherEntityWithGivenValues(float temp, int humidity, int pressure, int dayNumber) {
        WeatherEntity entity = new WeatherEntity();
        Climate main = new Climate();
        entity.setMain(main);

        main.setTemp(temp);
        main.setHumidity(humidity);
        main.setPressure(pressure);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, dayNumber);
        Date requestedDay = calendar.getTime();

        entity.setDt_txt(DATE_FORMAT.format(requestedDay));

        return entity;
    }

    private ForecastEntity createForecastEntityWithStubValues(String pathToStubData) {
        if (stubData == null || !stubData.getResourcePath().equals(pathToStubData)) {
            loadStubData(pathToStubData);
        }
        List<Measurement> stubMeasurements = stubData.getMeasurements();
        WeatherEntity[] list = new WeatherEntity[stubMeasurements.size()];
        int i = 0;
        for (Measurement measurement : stubMeasurements) {
            int dayNum= i == 0 ? 0 : i < 4 ? 1 : i < 7 ? 2 : 3;
            list[i] = createWeatherEntityWithGivenValues(measurement.getTemp(), measurement.getHumidity(),
                    measurement.getPressure(), dayNum);
            i++;
        }

        ForecastEntity forecastEntity = new ForecastEntity();
        forecastEntity.setList(list);
        return forecastEntity;
    }

}
