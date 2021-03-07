package ee.icd0004_2020f_weatherproject.end_to_end.step_definitions;

import ee.icd0004_2020f_weatherproject.MainProgram;
import io.cucumber.java.After;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MainStepDefinitions {

    public static final String OUTPUT_DIR_PATH = "src/test/resources/end_to_end/output_dir/";

    public static MainProgram program = new MainProgram(OUTPUT_DIR_PATH);

    private String inputFilePath;

    /**
     * Custom parameter {cities} will take 1 up to 10 city names
     * Cities should be in double quotes
     * Multiple cities should be separated by comma (space after comma is optional)
     */
    @ParameterType("(\".*\"(,( )?)?){1,10}")
    public List<String> cities(String cities){
        if (cities == null || cities.trim().length() == 0) return new ArrayList<>();
        return Arrays.stream(cities.split(",( )?"))
                .map(city -> city.trim().replace("\"", ""))
                .collect(Collectors.toList());
    }

    @After
    public void cleanUpOutputDirectory() throws IOException {
        FileUtils.cleanDirectory(new File(OUTPUT_DIR_PATH));
    }

    @Given("input file {string} path \\(relative to resource directory)")
    public void give_input_file_path(String inputFilePath) throws IOException {
        this.inputFilePath = inputFilePath;
    }

    @When("^We use produceWeatherReportForInputData$")
    public void apply_report_generation() {
        program.produceWeatherReportForInputData(inputFilePath);
    }

    @Then("We should get report(s) for {cities}")
    public void should_have_amount_of_reports_equal_to_amount_of_valid_cities_in_input_file(List<String> existingCities) {
        int expectedReportsAmount = existingCities.size();

        File outputDirectory = new File(OUTPUT_DIR_PATH);
        File[] producedFiles = outputDirectory.listFiles();
        assertNotNull("If this fails, then output directory can't be listed for some reason", producedFiles);

        String message = String.format("%1$s report file%2$s should be produced out of input file with %1$s existing cit%2$s",
                expectedReportsAmount,
                expectedReportsAmount == 1 ? "" : "s");

        assertEquals(message, expectedReportsAmount, producedFiles.length);

        List<String> fileNames =  Arrays.stream(producedFiles).map(File::getName).collect(Collectors.toList());

        for (String expectedCityName : existingCities) {
            assertTrue("Report file for city " + expectedCityName + " should contain city name in its name"
                            + "\n list of files: " + fileNames,
                    fileNames.stream().anyMatch(fileName -> fileName.contains(expectedCityName)));
        }
    }

    @Then("We shouldn't get any report")
    public void should_not_be_any_report_for_non_existing_city() {
        int expectedReportsAmount = 0;

        File outputDirectory = new File(OUTPUT_DIR_PATH);
        File[] producedFiles = outputDirectory.listFiles();
        assertNotNull("If this fails, then output directory can't be listed for some reason", producedFiles);

        assertEquals("No report should ne produced out of fake city/cities", expectedReportsAmount, producedFiles.length);
    }
}
