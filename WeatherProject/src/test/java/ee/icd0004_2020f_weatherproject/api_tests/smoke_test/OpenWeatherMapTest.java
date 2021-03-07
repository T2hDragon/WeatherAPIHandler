package ee.icd0004_2020f_weatherproject.api_tests.smoke_test;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

/**
 * https://openweathermap.org/api smoke tests
 */
public class OpenWeatherMapTest {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5";
    private static final String WEATHER_LOCATION = "/weather";
    private static final String FORECAST_LOCATION = "/forecast";
    private static final String KEY = "72c35644996b8dcfe52b463b487b6407";

    @Test
    public void when_current_weather_called_with_city_name_then_return_http_200() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .statusCode(200);
    }

    @Test
    public void when_current_weater_called_with_city_that_does_not_exist_then_return_http_404() {
        given()
                .queryParam("q", "Wololoo")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .statusCode(404);
    }

    @Test
    public void should_have_coordinates_data_in_current_weather_response() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .body("$", hasKey("coord"))
                .body("coord", hasKey("lon"))
                .body("coord", hasKey("lat"))
                .body("coord.lon", equalTo(24.41f));
    }

    @Test
    public void should_have_city_name_in_current_weather_response() {
        given()
                .queryParam("q", "Tallinn")
                .queryParam("appid", KEY)
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .body("$", hasKey("name"))
                .body("name", equalTo("Tallinn"));
    }

    @Test
    public void should_have_city_country_data_in_current_weather_response() {
        given()
                .queryParam("q", "Tallinn")
                .queryParam("appid", KEY)
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .body("$", hasKey("sys"))
                .body("sys", hasKey("country"))
                .body("sys.country", equalTo("EE"))
                .body("sys", hasKey("sunrise"))
                .body("sys", hasKey("sunset"));
    }

    @Test
    public void should_give_city_from_specified_country_in_current_weather_response() {
        String msg = "Paris with US specification should give Paris in United States";
        given()
                .queryParam("q", "Paris, US")
                .queryParam("appid", KEY)
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .body("sys.country", describedAs(msg, equalTo("US")));
    }

    @Test
    public void should_have_weather_description_data_in_current_weather_response() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .body("$", hasKey("weather"))
                .body("weather", hasSize(1)) // has only one weather object for current moment
                .body("weather[0]", hasKey("main"))
                .body("weather[0]", hasKey("description"));
    }

    @Test
    public void should_have_main_weather_data_in_current_weather_response() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + WEATHER_LOCATION)
                .then()
                .body("$", hasKey("main"))
                .body("main", hasKey("temp"))
                .body("main", hasKey("feels_like"))
                .body("main", hasKey("temp_min"))
                .body("main", hasKey("temp_max"))
                .body("main", hasKey("pressure"))
                .body("main", hasKey("humidity"));
    }

    @Test
    public void when_forecast_called_with_city_name_then_return_http_200() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + FORECAST_LOCATION)
                .then()
                .statusCode(200);
    }

    @Test
    public void when_forecast_called_with_city_that_does_not_exist_then_return_http_404() {
        given()
                .queryParam("q", "Wololoo")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + FORECAST_LOCATION)
                .then()
                .statusCode(404);
    }

    @Test
    public void should_have_city_data_in_forecast_response() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + FORECAST_LOCATION)
                .then()
                .body("$", hasKey("city"))
                .body("city", hasKey("name"))
                .body("city", hasKey("coord"))
                .body("city", hasKey("country"));
    }

    @Test
    public void should_have_forecast_data_in_forecast_response() {
        given()
                .queryParam("q", "Keila")
                .queryParam("appid", KEY)
                .queryParam("units", "metric")
                .when()
                .get(API_URL + FORECAST_LOCATION)
                .then()
                .body("$", hasKey("list"))
                .body("list", hasSize(40));
    }
}
