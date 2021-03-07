package ee.icd0004_2020f_weatherproject.IO_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.icd0004_2020f_weatherproject.IO.DataManagement;
import ee.icd0004_2020f_weatherproject.report.CompleteWeatherReport;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Integration testing with Mockito.
 */
@RunWith(MockitoJUnitRunner.class)
public class WriteToFileTest {

    private static final DataManagement dataManagement = new DataManagement();
    private final CompleteWeatherReport completeWeatherReport = readJsonFileToObject(expectedResultPath);
    private static final String testOutputPath = "src/test/resources/write_to_file/output_target.json";

    private static final String expectedResultPath = "write_to_file/expected_result.json";
    @SneakyThrows
    @After
    public void clearOutputFile() {
        File file = new File(testOutputPath);
        if (file.exists()){
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.setLength(0);
        }
    }

    @Test
    public void canCreateFile() {
        File file = new File(testOutputPath);
        file.delete();
        dataManagement.writeToFile(testOutputPath, completeWeatherReport);
        assertThat(file.exists());
    }


    @Test
    public void canWriteToFile() {
        File file = new File(testOutputPath);
        dataManagement.writeToFile(testOutputPath, completeWeatherReport);
        assertThat(file.length() !=0);

    }

    @Test
    public void writesCorrectly() {
        dataManagement.writeToFile(testOutputPath, completeWeatherReport);
        try {
            File expectedResultFile = new File("src/test/resources/" + expectedResultPath);
            File outputFile = new File(testOutputPath);
            assertTrue(FileUtils.contentEquals(expectedResultFile, outputFile));
        } catch (IOException e) {
            fail();
        }

    }


    private CompleteWeatherReport readJsonFileToObject(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        CompleteWeatherReport tempCompleteWeatherReport;
        ClassLoader loader = getClass().getClassLoader();

        loader.getResource(path);
        {
            try {
                File file = new File(Objects.requireNonNull(loader.getResource(path)).toURI());

                tempCompleteWeatherReport = objectMapper.readValue(file, CompleteWeatherReport.class);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to find file");
            }
        }
        return tempCompleteWeatherReport;
    }


}


