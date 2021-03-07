package ee.icd0004_2020f_weatherproject.IO;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.icd0004_2020f_weatherproject.report.CompleteWeatherReport;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

public class DataManagement {

    String[] cities;


    public boolean hasEntries(){
        return cities.length != 0;
    }

    public void readFromCitiesFile(String path){
        cities = new String[path.length()];
        ClassLoader loader = getClass().getClassLoader();

        FileReader fr = null;
        try {
            File file = new File(Objects.requireNonNull(loader.getResource(path)).toURI());
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                cities[index] = line;
                index++;
            }
        }catch (URISyntaxException ue) {
            throw new RuntimeException("Invalid path structure: " + path);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open file at path :" + path);
        } finally {
            close(fr);
        }

    }


    public String[] getCities() {
        return cities.clone();
    }

    public static void close(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't close Closeable object");
        }
    }

    public void writeToFile(String path, CompleteWeatherReport completeWeatherReport) {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter file = null;
        try {
            file = new FileWriter(path);
            file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(completeWeatherReport));
            file.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found at path: " + path);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to make CompleteWeatherReport into json. \n" + completeWeatherReport.toString());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write to file");
        } finally {
            close(file); // file should be closed in the end
        }
    }
}
