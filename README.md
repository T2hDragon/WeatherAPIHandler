# ICD0004 - Course Project - 2020 fall

Graded project for ICD0004 automated testing course.

# Project authors
- Karmo Alteberg
- Juri Geiman

# Tech stack
- Java
- Maven
- AssertJ
- JUnit
- Lombok
- Mockito
- Json-Simple
- Jersey client
- Jackson databind
- Commons IO
- Cucumber IO

# How to build, run, test
1. Build
    1. Add input data file(s) into `main/resources/input_files` directory.
       <br/> **Input file requirements:**
        - Should have `.txt` extension
        - Cities should be separated with line breaks
          <br/> *Example file can be found in `main/resources/input_files`*
    2. Be sure you are in the`WeahterProject`directory (with pom.xml)
    3. \- mvn compile

2. Run application from command line, generate report file.
    1. Be sure you are in the`WeahterProject`directory (with pom.xml)
    2. \- mvn exec:java -Dexec.args="*your file name goes here. In case of multiple files separate names with space*"
       <br/> *Example: `mvn exec:java -Dexec.args="example.txt"` or just `mvn exec:java -Dexec.args="example"`*
    3. Produced reports can be found in `WeatherProject/reports` directory.

3. Run tests
    - All tests can be found under the`test`directory
    - To run all tests from cmd, run `mvn clean test` command (from `WeahterProject`directory)
    - to run specific test from cmd, run `mvn -Dtest=TestClassNameGoesHere test` command (from `WeahterProject`directory)

# Additional notes
- Used java 11 for development of this application
- Each time you add new files with input data into `main/resources/input_files` and want to get report(s) out of this files data, be sure to run `mvn compile` before actually run program
- Since program execution using `mvn` and execution using `java` are different, you can't run `MainProgram` using `javac ... -> java ...` (output directory will not be found and error will be produced)   
