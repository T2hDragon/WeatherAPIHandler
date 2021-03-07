package ee.icd0004_2020f_weatherproject.api_tests;

import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.api.response.ForecastEntity;
import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.api.response.report.Coordinates;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.exceptions.ApiResponseException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeatherApiTest {

    private static final TemperatureUnit STD_UNIT = TemperatureUnit.CELSIUS;
    private static WeatherApi weatherApi;

    @BeforeClass
    public static void setUp() {
        weatherApi = new WeatherApi();
    }

    // When requesting form http://api.openweathermap.org/data/2.5/weather

    @Test(expected = ApiResponseException.class)
    public void should_throw_exception_when_requested_current_weather_for_unknown_city() {
        String unknownCity = "QWERTY";
        WeatherApi api = new WeatherApi();
        api.getWeatherEntity(unknownCity, TemperatureUnit.CELSIUS);
    }

    @Test
    public void should_have_city_name_in_current_weather_response() {
        String expectedCityName = "London";

        WeatherEntity response = weatherApi.getWeatherEntity(expectedCityName, STD_UNIT);


        assertTrue("City name should be set in response", isSet(response.getName()));


        String responseCityName = response.getName();

        assertEquals("Should be response for requested city", expectedCityName, responseCityName);
    }

    @Test
    public void should_have_city_coordinates_in_current_weather_response() {
        String cityName = "London";
        Coordinates expectedCityCoordinates = new Coordinates(-0.13, 51.51);

        WeatherEntity response = weatherApi.getWeatherEntity(cityName, STD_UNIT);
        assertTrue("City coordinates should be set in response", isSet(response.getCoord()));

        Coordinates responseCityCoordinates = response.getCoord();

        assertEquals(cityName + "latitude and longitude should be " + expectedCityCoordinates.toString(),
                expectedCityCoordinates,
                responseCityCoordinates);
    }

    @Test
    public void should_have_main_weather_details_in_current_weather_response() {
        String cityName = "London";

        WeatherEntity response = weatherApi.getWeatherEntity(cityName, STD_UNIT);
        assertTrue("Temperature should be set in response", isSet(response.getMain().getTemp()));
        assertTrue("Pressure should be set in response", isSet(response.getMain().getPressure()));
        assertTrue("Humidity should be set in response", isSet(response.getMain().getHumidity()));
    }

    // When requesting form http://api.openweathermap.org/data/2.5/forecast

    @Test (expected = ApiResponseException.class)
    public void should_throw_exception_when_requested_forecast_for_unknown_city() {
        String unknownCity = "QWERTY";
        WeatherApi api = new WeatherApi();
        api.getForecastEntity(unknownCity, TemperatureUnit.CELSIUS);
    }

    @Test
    public void should_have_city_name_in_forecast_response() {
        String expectedCityName = "London";

        ForecastEntity response = weatherApi.getForecastEntity(expectedCityName, STD_UNIT);

        assertTrue("City name should be set in response", isSet(response.getCity()));

        String responseCityName = response.getCity().getName();

        assertEquals("Should be response for requested city", expectedCityName, responseCityName);
    }

    @Test
    public void should_have_city_coordinates_in_forecast_response() {
        String cityName = "London";
        Coordinates expectedCityCoordinates = new Coordinates(-0.13, 51.51);

        ForecastEntity response = weatherApi.getForecastEntity(cityName, STD_UNIT);

        Coordinates responseCityCoordinates = response.getCity().getCoord();

        assertTrue(cityName + "Latitude should be close to (precision: 0.1)" + expectedCityCoordinates.getLat(),
                closeTo(expectedCityCoordinates.getLat(), responseCityCoordinates.getLat()));

        assertTrue(cityName + " Longitude should be close to (precision: 0.1)" + expectedCityCoordinates.getLon(),
                closeTo(expectedCityCoordinates.getLon(), responseCityCoordinates.getLon()));
    }

    @Test
    public void should_have_country_of_city_in_forecast_response() {
        String cityName = "London";
        String expectedCountryOfGivenCity = "GB";

        ForecastEntity response = weatherApi.getForecastEntity(cityName, STD_UNIT);

        String responseCountryOfGivenCity = response.getCity().getCountry();

        assertEquals("Should be GB (Great Britain) as a country of London",
                expectedCountryOfGivenCity,
                responseCountryOfGivenCity);
    }

    @Test
    public void should_have_total_of_40_weather_measurements_in_forecast_response() {
        String cityName = "London";
        int expectedMeasurementsAmount = 40;

        ForecastEntity response = weatherApi.getForecastEntity(cityName, STD_UNIT);

        WeatherEntity[] forecast = response.getList();
        int actualMeasurementsAmount = forecast.length;

        assertEquals("Should be 5 day forecast",
                expectedMeasurementsAmount,
                actualMeasurementsAmount);
    }

    @Test
    public void should_have_weather_measurement_frequency_be_three_hour_in_forecast_response() {
        String cityName = "London";
        int expectedDayMeasurementsAmount = 24 / 3;

        ForecastEntity response = weatherApi.getForecastEntity(cityName, STD_UNIT);

        WeatherEntity[] forecast = response.getList();
        int actualDayMeasurementsAmount = (int) Arrays.stream(forecast)
                .map(WeatherEntity::getDate)
                .map(forecastDate -> getDatePart(forecastDate, Calendar.HOUR_OF_DAY))
                .distinct()
                .count();

        assertEquals("Should be 5 day forecast",
                expectedDayMeasurementsAmount,
                actualDayMeasurementsAmount);
    }


    private static <T> boolean isSet(T value) {
        return value != null;
    }

    private static boolean closeTo(double a, double b) {
        return Math.abs(a - b) < 0.1;
    }

    /**
     * get a part of the date (like hour/day/..) using Calendar and its inner int constants values.
     * @param date given date
     * @param calendarFlag Calendar constant value
     * @return part of given date
     */
    private static int getDatePart(Date date, int calendarFlag) {
        return new Calendar
                .Builder()
                .setInstant(date)
                .build()
                .get(calendarFlag);
    }

}
