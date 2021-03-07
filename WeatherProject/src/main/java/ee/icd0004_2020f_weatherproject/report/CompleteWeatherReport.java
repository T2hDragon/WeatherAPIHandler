package ee.icd0004_2020f_weatherproject.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompleteWeatherReport {
    private WeatherReportDetails weatherReportDetails;

    private CurrentWeatherReport currentWeatherReport;

    private CurrentWeatherReport[] forecastReport;

}
