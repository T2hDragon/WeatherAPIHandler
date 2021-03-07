package ee.icd0004_2020f_weatherproject.weather_time_tests;

import ee.icd0004_2020f_weatherproject.WeatherTime;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.report.CompleteWeatherReport;
import ee.icd0004_2020f_weatherproject.report.CurrentWeatherReport;
import ee.icd0004_2020f_weatherproject.report.ForecastReport;
import ee.icd0004_2020f_weatherproject.report.WeatherReportDetails;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class CompleteWeatherReportTest {

    private static final TemperatureUnit STD_UNIT = TemperatureUnit.CELSIUS;
    private static final String REPORT_DATE_FORMAT = "yyyy-MM-dd";
    private static WeatherTime weatherTime;
    private static final String city = "New York";

    @BeforeClass
    public static void setUp() {
        weatherTime = new WeatherTime(new WeatherApi());
    }

    @Test
    public void should_have_complete_weather_report_be_set() {

        CompleteWeatherReport completeWeatherReport = weatherTime.getCompleteWeatherReport(city);

        assertThat(completeWeatherReport)
            .isNotNull();
    }

    @Test
    public void should_have_current_weather_report_in_complete_weather_report() {
        CompleteWeatherReport completeWeatherReport = weatherTime.getCompleteWeatherReport(city);

        assertEquals(completeWeatherReport.getCurrentWeatherReport().getClass(),CurrentWeatherReport.class);
        assertThat(completeWeatherReport.getCurrentWeatherReport())
                .isNotNull();
    }


    @Test
    public void should_have_forecast_report_in_complete_weather_report() {
        CompleteWeatherReport completeWeatherReport = weatherTime.getCompleteWeatherReport(city);

        assertEquals(completeWeatherReport.getForecastReport().getClass(), CurrentWeatherReport[].class);
        assertThat(completeWeatherReport.getForecastReport())
                .isNotNull();
    }


    @Test
    public void should_have_weather_report_details_in_complete_weather_report() {
        CompleteWeatherReport completeWeatherReport = weatherTime.getCompleteWeatherReport(city);

        assertEquals(completeWeatherReport.getWeatherReportDetails().getClass(), WeatherReportDetails.class);
        assertThat(completeWeatherReport.getWeatherReportDetails())
                .isNotNull();
    }
}
