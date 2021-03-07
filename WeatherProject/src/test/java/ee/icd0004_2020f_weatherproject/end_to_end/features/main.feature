Feature: Weather Report Program

  Scenario: Should produce one report
    Given input file "end_to_end/input_dir/one_existing_city.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We should get report for "Tallinn"

  Scenario: Should produce only one report for city
    Given input file "end_to_end/input_dir/one_city_many_times.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We should get reports for "Madrid"

  Scenario: Should produce report for each city
    Given input file "end_to_end/input_dir/three_existing_cities.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We should get reports for "Warsaw", "Krakow", "Berlin"

  Scenario: Should not produce report for non-existing city
    Given input file "end_to_end/input_dir/one_fake_city.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We shouldn't get any report

  Scenario: Should not produce report for non-existing cities
    Given input file "end_to_end/input_dir/three_fake_cities.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We shouldn't get any report

  Scenario: Should produce reports only for existing cities
    Given input file "end_to_end/input_dir/two_real_one_fake.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We should get reports for "Helsinki", "Vantaa"

  Scenario: Should skip empty lines
    Given input file "end_to_end/input_dir/city_empty_empty_empty_city.txt" path (relative to resource directory)
    When We use produceWeatherReportForInputData
    Then We should get reports for "London", "Oslo"