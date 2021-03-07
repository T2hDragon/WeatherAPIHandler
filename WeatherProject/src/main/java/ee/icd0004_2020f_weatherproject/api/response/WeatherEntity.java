package ee.icd0004_2020f_weatherproject.api.response;

import ee.icd0004_2020f_weatherproject.api.response.report.Climate;
import ee.icd0004_2020f_weatherproject.api.response.report.Coordinates;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherEntity implements IApiEntity {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String name;

    private Coordinates coord;

    private Climate main;

    private String dt_txt;

    public Date getDate() {
        try {
            return DATE_FORMAT.parse(dt_txt);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse date " + dt_txt + " using pattern " + DATE_FORMAT.toPattern());
        }
    }


}
