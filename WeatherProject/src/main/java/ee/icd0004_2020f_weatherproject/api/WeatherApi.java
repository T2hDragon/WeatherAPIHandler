package ee.icd0004_2020f_weatherproject.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import ee.icd0004_2020f_weatherproject.api.response.ForecastEntity;
import ee.icd0004_2020f_weatherproject.api.response.IApiEntity;
import ee.icd0004_2020f_weatherproject.api.response.WeatherEntity;
import ee.icd0004_2020f_weatherproject.api.response.bad_response.BadResponse;
import ee.icd0004_2020f_weatherproject.enums.TemperatureUnit;
import ee.icd0004_2020f_weatherproject.exceptions.ApiResponseException;
import ee.icd0004_2020f_weatherproject.exceptions.NetworkConnectionException;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import static com.sun.jersey.api.client.Client.create;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

public class WeatherApi {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String CURRENT_WEATHER_LOCATION = "/weather";
    private static final String FORECAST_LOCATION = "/forecast";

    private static final String API_KEY = "72c35644996b8dcfe52b463b487b6407";
    private static final Client client = getConfiguredClient();
    private static final WeatherApiQueryParameters queryParams = new WeatherApiQueryParameters();

    /** Currently used only for convenient WeatherApi tests */
    public WeatherEntity getWeatherEntity(String cityName, TemperatureUnit units) {
        String resourceUrl = BASE_URL + CURRENT_WEATHER_LOCATION;

        return makeApiRequest(WeatherEntity.class, resourceUrl, cityName, units);
    }

    /** Currently used only for convenient WeatherApi tests */
    public ForecastEntity getForecastEntity(String cityName, TemperatureUnit units) {
        String resourceUrl = BASE_URL + FORECAST_LOCATION;

        return makeApiRequest(ForecastEntity.class, resourceUrl, cityName, units);
    }

    /**
     * Generification of {@code getWeatherEntity()} and {@code getForecastEntity()}
     * Only classes that implement IApiEntity can be provided to construct given class entity from Api response
     */
    public <T extends IApiEntity> T getTargetClassEntity(Class<T> targetClass, String cityName, TemperatureUnit units) {
        String resourceUrl = BASE_URL;
        resourceUrl += targetClass == WeatherEntity.class ? CURRENT_WEATHER_LOCATION : FORECAST_LOCATION;

        return makeApiRequest(targetClass, resourceUrl, cityName, units);
    }

    /**
     * Performs api request using {@code WeatherApi.client}, takes client response,
     * and returns entity of given target class constructed from client response.
     * Throws {@code ApiResponseException} if client response status was 400 or over it
     * Throws {@code NetworkConnectionException} if client does not get response
     * @param targetClass class for returning entity
     * @param resourceUrl url on which client performs request
     * @param cityName request parameter
     * @param units request parameter
     * @return entity of given class constructed from api response
     */
    private <T> T makeApiRequest(Class<T> targetClass, String resourceUrl, String cityName, TemperatureUnit units) {
        ClientResponse response;
        try {
            response = getClientResponse(resourceUrl, cityName, units);
            int status = response.getStatus();

            if (status > 399) {
                var badResponse = response.getEntity(BadResponse.class);
                throw new ApiResponseException(badResponse.message);
            }
        } catch (ClientHandlerException e) {
            throw new NetworkConnectionException("Check your network connection.");
        }
        return response.getEntity(targetClass);
    }

    private ClientResponse getClientResponse(String resourceUrl, String cityName, TemperatureUnit units) {
        return client.resource(resourceUrl)
                .queryParam(queryParams.getCityParam(), cityName)
                .queryParam(queryParams.getKeyParam(), API_KEY)
                .queryParam(queryParams.getUnitParam(), units.getUnitString())
                .get(ClientResponse.class);
    }

    private static Client getConfiguredClient() {
        ClientConfig configuration = new DefaultClientConfig();
        configuration.getClasses().add(JacksonJaxbJsonProvider.class);
        configuration.getFeatures().put(FEATURE_POJO_MAPPING, true);
        return create(configuration);
    }

}
