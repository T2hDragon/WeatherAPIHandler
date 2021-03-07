package ee.icd0004_2020f_weatherproject.api.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

    private Double lon;
    private Double lat;

    @Override
    public String toString() {
        return String.format("%s, %s", lat, lon);
    }

    public boolean isUnset() {
        return lon == null || lat == null;
    }
}
