package ee.icd0004_2020f_weatherproject.api.response.bad_response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BadResponse {

    public String cod;
    public String message = "unknown error";

}
