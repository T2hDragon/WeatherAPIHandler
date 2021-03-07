package ee.icd0004_2020f_weatherproject.api;


import lombok.Data;

@Data
public class WeatherApiQueryParameters {

    private final String cityParam = "q";
    private final String keyParam = "appid";
    private final String unitParam = "units";
    private final String latitudeParam = "lat";
    private final String longitudeParam = "lon";
}
