package ee.icd0004_2020f_weatherproject;

import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.api.response.ForecastEntity;
import ee.icd0004_2020f_weatherproject.api.response.IApiEntity;
import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.enums.TimeRange;
import ee.icd0004_2020f_weatherproject.exceptions.ApiResponseException;
import ee.icd0004_2020f_weatherproject.exceptions.NetworkConnectionException;
import ee.icd0004_2020f_weatherproject.report.CurrentWeatherReport;
import ee.icd0004_2020f_weatherproject.report.ForecastReport;
import ee.icd0004_2020f_weatherproject.report.CompleteWeatherReport;
import ee.icd0004_2020f_weatherproject.report.WeatherReportDetails;

import java.util.List;

public class WeatherTime {

    private static final TemperatureUnit DEFAULT_TEMP_UNIT = TemperatureUnit.CELSIUS;
    private static final TimeRange DEFAULT_TIME_RANGE = TimeRange.FULL_DAY;

    private final WeatherApi weatherApi;

    public WeatherTime(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public WeatherReportDetails getWeatherReportDetails(String city, TemperatureUnit units) {
        WeatherEntity weatherEntity = getWeatherEntity(city, units);

        return new WeatherReportDetails(
                weatherEntity.getName(),
                weatherEntity.getCoord(),
                units
        );
    }

    public WeatherReportDetails getWeatherReportDetails(String city) {
        return getWeatherReportDetails(city, DEFAULT_TEMP_UNIT);
    }

    public CurrentWeatherReport getCurrentWeatherReport(String city, TemperatureUnit units) {
        WeatherEntity weatherEntity = getWeatherEntity(city, units);

        return new CurrentWeatherReport(weatherEntity.getMain());
    }

    public CurrentWeatherReport getCurrentWeatherReport(String city) {
        return getCurrentWeatherReport(city, DEFAULT_TEMP_UNIT);
    }

    public ForecastReport getForecastReport(String city, TemperatureUnit units, TimeRange timeRange) {
        ForecastEntity forecastEntity = getForecastEntity(city, units);

        CurrentWeatherReport[] forecast = new CurrentWeatherReport[3];
        for (int i = 0; i < 3; i++) {
            List<WeatherEntity> oneDayMeasurements = forecastEntity.takeNthDayMeasurements(i + 1);
            forecast[i] = CurrentWeatherReport
                    .computeAverageWeatherAndGetReport(oneDayMeasurements, timeRange);
        }

        return new ForecastReport(forecast);
    }

    public ForecastReport getForecastReport(String city, TemperatureUnit units) {
        return getForecastReport( city, units, DEFAULT_TIME_RANGE);
    }

    public ForecastReport getForecastReport(String city) {
        return getForecastReport( city, DEFAULT_TEMP_UNIT, DEFAULT_TIME_RANGE);
    }

    public CompleteWeatherReport getCompleteWeatherReport(String city){
        return getCompleteWeatherReport(city, DEFAULT_TEMP_UNIT);
    }

    public CompleteWeatherReport getCompleteWeatherReport(String city, TemperatureUnit units){
        WeatherReportDetails weatherReportDetails = getWeatherReportDetails(city, units);
        CurrentWeatherReport currentWeatherReport = getCurrentWeatherReport(city, units);
        ForecastReport forecastReport = getForecastReport(city, units);

        return new CompleteWeatherReport(
                weatherReportDetails,
                currentWeatherReport,
                forecastReport.getForecast());
    }

    private WeatherEntity getWeatherEntity(String city, TemperatureUnit units) {
        return getTargetEntity(WeatherEntity.class, city, units);
    }

    private ForecastEntity getForecastEntity(String city, TemperatureUnit units) {
        return getTargetEntity(ForecastEntity.class, city, units);
    }


    private<T extends IApiEntity> T getTargetEntity(Class<T> targetClass, String city, TemperatureUnit units) {
        T entity;
        try {
            entity = weatherApi.getTargetClassEntity(targetClass, city, units);
            if (entity == null) throw new RuntimeException("Nothing returned by API");
        } catch (NetworkConnectionException conExc) {
            throw new RuntimeException("Check your network connection");
        } catch (ApiResponseException apiExc) {
            throw new RuntimeException("Error on api layer: " + apiExc.getMessage());
        }
        return entity;
    }

}
