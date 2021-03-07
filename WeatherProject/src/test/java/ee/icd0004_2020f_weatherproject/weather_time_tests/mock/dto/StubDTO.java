package ee.icd0004_2020f_weatherproject.weather_time_tests.mock.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StubDTO {

    private String resourcePath;

    private List<Measurement> measurements;
    private PreCalculated full_day;
    private PreCalculated daytime;

    public PreCalculated getFullDay() {
        return full_day;
    }

}

