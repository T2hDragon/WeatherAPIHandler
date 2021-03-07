package ee.icd0004_2020f_weatherproject.report;

import ee.icd0004_2020f_weatherproject.api.response.report.Coordinates;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherReportDetails {

    private String city;

    private String coordinates;

    private String temperatureUnit;

    public WeatherReportDetails(String cityName, Coordinates cityCoordinates, TemperatureUnit tempUnit) {
        city = cityName;
        temperatureUnit = tempUnit.getStringRepresentation();
        if (cityCoordinates == null || cityCoordinates.isUnset()) {
            throw new RuntimeException("City coordinates are corrupted. Value: " + cityCoordinates);
        }
        coordinates = cityCoordinates.toString();

    }

}
