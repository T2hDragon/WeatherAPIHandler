package ee.icd0004_2020f_weatherproject.weather_time_tests.mock.dto;

import lombok.Data;

import java.util.List;

@Data
public class PreCalculated {
    private List<Float> averageTempValues;
    private List<Integer> averageHumidityValues;
    private List<Integer> averagePressureValues;
}
