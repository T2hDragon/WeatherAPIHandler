package ee.icd0004_2020f_weatherproject.exceptions;

public class NetworkConnectionException extends RuntimeException {

    public NetworkConnectionException(String message) {
        super(message);
    }
}
