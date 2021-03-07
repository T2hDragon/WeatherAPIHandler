package ee.icd0004_2020f_weatherproject.api.response.report;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

    private String name;

    private Coordinates coord;

    private String country;

}
