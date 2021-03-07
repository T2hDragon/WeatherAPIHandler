package ee.icd0004_2020f_weatherproject.api.response.report;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Climate {
    private Float temp;
    private Float feels_like;
    private Integer temp_min;
    private Integer temp_max;
    private Integer pressure;
    private Integer humidity;


}
