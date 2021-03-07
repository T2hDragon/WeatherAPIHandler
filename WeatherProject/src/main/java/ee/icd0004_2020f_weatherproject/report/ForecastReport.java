package ee.icd0004_2020f_weatherproject.report;

import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForecastReport {

    CurrentWeatherReport[] forecast;

    public ForecastReport(CurrentWeatherReport[] forecast) {
        this.forecast = forecast;
    }

}
