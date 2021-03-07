package ee.icd0004_2020f_weatherproject.IO_tests;

import ee.icd0004_2020f_weatherproject.IO.DataManagement;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadFromFileTest {
    private static final DataManagement dataManagement = new DataManagement();

    @BeforeClass
    public static void setUp() {

        String path = "file_read/input.txt";
        dataManagement.readFromCitiesFile(path);
    }

    @Test
    public void is_able_to_read_from_file() {
        assertThat(dataManagement.hasEntries());

    }

    @Test
    public void is_able_to_re_read_from_starting_file() {
        assertThat(dataManagement.hasEntries());
    }

    @Test
    public void is_able_to_read_all_names_from_file() {
        String[] expectedCityNames = new String[]{
                "Narva",
                "Tallinn",
                "Stockholm"};

        for (String city :
                expectedCityNames) {
            assertThat(Arrays.asList(dataManagement.getCities()).contains(city));
        }
    }
}
