package ee.icd0004_2020f_weatherproject.end_to_end;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/ee/icd0004_2020f_weatherproject/end_to_end/features",
        glue = "ee.icd0004_2020f_weatherproject.end_to_end.step_definitions",
        plugin = {"pretty"}
)
public class MainProgramTest {

    @BeforeClass
    public static void createOutputDirectory() throws IOException {
        var t = Paths.get("");
        t = t.toAbsolutePath();
        System.out.println(t);
        Path path = Paths.get(t + "/src/test/resources/end_to_end/output_dir/");
        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignore) { }
        //TODO Figure out better way to create directory in runtime
    }
}
