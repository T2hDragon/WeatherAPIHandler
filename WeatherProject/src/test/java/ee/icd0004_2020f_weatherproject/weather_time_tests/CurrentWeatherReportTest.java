package ee.icd0004_2020f_weatherproject.weather_time_tests;

import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.CurrentWeatherReport;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrentWeatherReportTest {

    private static final TemperatureUnit STD_UNIT = TemperatureUnit.CELSIUS;
    private static final String REPORT_DATE_FORMAT = "yyyy-MM-dd";
    private static WeatherTime weatherTime;

    @BeforeClass
    public static void setUp() {
        weatherTime = new WeatherTime(new WeatherApi());
    }

    @Test
    public void should_have_current_weather_report_be_set() {
        String city = "New York";

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);

        assertThat(currentWeatherReport)
            .isNotNull();
    }

    @Test
    public void should_have_current_date_in_current_weather_report() {
        String city = "Tallinn";

        String expectedDate = new SimpleDateFormat(REPORT_DATE_FORMAT).format(Calendar.getInstance().getTime());

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);
        String actualDate = currentWeatherReport.getDate();

        assertEquals("current date " + expectedDate + " should be set (requested by city only)",
                expectedDate,
                actualDate);

        currentWeatherReport = weatherTime.getCurrentWeatherReport(city, STD_UNIT);
        actualDate = currentWeatherReport.getDate();

        assertEquals("current date " + expectedDate + " should be set (requested by city, temp unit specified)",
                expectedDate,
                actualDate);
    }

    @Test
    public void should_have_temperature_in_current_weather_report() {
        String city = "Tallinn";

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);

        assertThat(currentWeatherReport.getTemperature())
                .overridingErrorMessage("Temperature should be set in Current Weather Report")
                .isNotNull();
    }

    @Test
    public void should_have_Celsius_as_default_temperature_unit_in_current_weather_report() {
        String city = "Tallinn";

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city, STD_UNIT);
        float temperatureInCelsius = currentWeatherReport.getTemperature();

        currentWeatherReport = weatherTime.getCurrentWeatherReport(city);
        float temperatureInDefaultUnits = currentWeatherReport.getTemperature();

        assertEquals("If no unit was specified, should have Temperature in Celsius in Current Weather Report",
                temperatureInCelsius,
                temperatureInDefaultUnits,
                1d);
    }

    @Test
    public void should_have_temperature_in_provided_unit_in_current_weather_report() {
        String city = "Tallinn";

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city, STD_UNIT);
        float temperatureInCelsius = currentWeatherReport.getTemperature();


        float celsiusConvertedToFahrenheit = (temperatureInCelsius * 9f / 5) + 32;

        currentWeatherReport = weatherTime.getCurrentWeatherReport(city, TemperatureUnit.FAHRENHEIT);
        float temperatureInFahrenheits = currentWeatherReport.getTemperature();

        assertEquals("If Fahrenheit was specified, should have Temperature close to "
                        + celsiusConvertedToFahrenheit + ", but was " + temperatureInFahrenheits,
                celsiusConvertedToFahrenheit,
                temperatureInFahrenheits,
                3d);


        float celsiusConvertedToKelvin = temperatureInCelsius + 273.15f;

        currentWeatherReport = weatherTime.getCurrentWeatherReport(city, TemperatureUnit.KELVIN);
        float temperatureInKelvin = currentWeatherReport.getTemperature();

        assertEquals("If Kelvin was specified, should have Temperature close to "
                        + celsiusConvertedToKelvin + ", but was " + temperatureInKelvin,
                celsiusConvertedToKelvin,
                temperatureInKelvin,
                3d);
    }

    @Test
    public void should_have_humidity_in_current_weather_report() {
        String city = "Tallinn";

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);
        int humidity = currentWeatherReport.getHumidity();

        assertThat(humidity).overridingErrorMessage(
                "Humidity percentage with probability of 99% should fall in a range 1-100%, but was "
                        + humidity)
                .isGreaterThan(0)
                .isLessThan(101);
    }

    @Test
    public void should_have_pressure_in_current_weather_report() {
        String city = "Tallinn";

        CurrentWeatherReport currentWeatherReport = weatherTime.getCurrentWeatherReport(city);
        int pressure = currentWeatherReport.getPressure();

        assertThat(pressure).overridingErrorMessage(
                "Air pressure in Estonia (city: Tallinn) is very unlikely to not be in range (930hPa - 1065hPa), but was "
                        + pressure + "\n Source: (https://www.ilmateenistus.ee/kliima/rekordid/ohurohk/?lang=en)")
                .isGreaterThan(929)
                .isLessThan(1066);
    }

}
