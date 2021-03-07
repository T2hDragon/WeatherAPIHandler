package ee.icd0004_2020f_weatherproject.api.response.report;

import lombok.Data;

@Data
public class WeatherDescription {
    private int id;
    private String main;
    private String description;
    private String icon;
}
