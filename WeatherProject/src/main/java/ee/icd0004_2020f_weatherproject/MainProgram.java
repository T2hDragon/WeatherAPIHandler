package ee.icd0004_2020f_weatherproject;

import ee.icd0004_2020f_weatherproject.IO.DataManagement;
import ee.icd0004_2020f_weatherproject.api.WeatherApi;
import ee.icd0004_2020f_weatherproject.report.CompleteWeatherReport;

public class MainProgram {

    public static final String EXTENSION = ".json";

    public static final String DEFAULT_OUTPUT_DIR = "reports/";

    private final String outputDirectory;

    public MainProgram(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public MainProgram() {
        this.outputDirectory = DEFAULT_OUTPUT_DIR;
    }

    public void produceWeatherReportForInputData(String pathToInputDataFile) {
        produceWeatherReportForInputData(pathToInputDataFile, false);
    }

    public void produceWeatherReportForInputData(String pathToInputDataFile, boolean shouldPrintMessages) {
        DataManagement dataManager = new DataManagement();
        WeatherTime reportGenerator = new WeatherTime(new WeatherApi());

        dataManager.readFromCitiesFile(pathToInputDataFile);
        for (String city : dataManager.getCities()) {
            if (city == null || city.trim().equals("")) continue;

            if (shouldPrintMessages) System.out.println("Constructing report for " + city);
            try {
                CompleteWeatherReport report = reportGenerator.getCompleteWeatherReport(city);

                String outputFileName = getFileNameForWeatherReport(report);

                String outputPath = outputDirectory + outputFileName;

                dataManager.writeToFile(outputPath, report);

                if (shouldPrintMessages) System.out.println("\u001B[32mDone!\u001B[0m");
            } catch (Exception e) {
                // main program should not fail
                System.out.println("Report could not be produced. \u001B[31m" + e.getClass().toString() + ": " + e.getMessage() + "\u001B[0m");
            } finally {
                System.out.println();
            }
        }
    }

    private static String getFileNameForWeatherReport(CompleteWeatherReport report) {
        String city = report.getWeatherReportDetails().getCity();
        String startDate = report.getCurrentWeatherReport().getDate();
        String endDate = report.getForecastReport()[2].getDate();

        return city + "_(" + startDate + '—' + endDate + ')' + EXTENSION;
    }

    private static String getCompleteInputFilePath(String inputFileName) {
        if (!inputFileName.matches(".+\\.txt$")) {
            inputFileName += ".txt";
        }
        return inputFileName;
    }

    /**
     * Runnable for console. Steps:
     * 1) Put input files into "main/resources/input_files" directory. Input files should have .txt extension
     * 2) - mvn compile
     * 3) - mvn exec:java -Dexec.args="{your file name goes here. In case of multiple files separate names with space}"
     * @param args input data file names
     */
    public static void main(String[] args) {
        MainProgram program = new MainProgram();
        for (String inputFileName : args) {
            try {
                inputFileName = getCompleteInputFilePath(inputFileName);
                System.out.println(inputFileName);
                program.produceWeatherReportForInputData("input_files/" + inputFileName, true);
            } catch (NullPointerException e) {
                System.out.println("Could not find given file name in \u001B[34minput_files\u001B[0m directory");
            } catch (Exception e) {
                System.out.println("\u001B[31m" + e.getClass().toString() + ": " + e.getMessage() + "\u001B[0m\n");
            }
        }
    }
}
