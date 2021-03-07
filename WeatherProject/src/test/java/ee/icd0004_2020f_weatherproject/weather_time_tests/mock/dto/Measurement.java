package ee.icd0004_2020f_weatherproject.weather_time_tests.mock.dto;

import lombok.Data;

@Data
public class Measurement {
    private String dt_txt;
    private float temp;
    private int humidity;
    private int pressure;
}
