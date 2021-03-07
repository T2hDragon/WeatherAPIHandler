package ee.icd0004_2020f_weatherproject.exceptions;

public class ApiResponseException extends IllegalArgumentException {

    public ApiResponseException(String message) {
        super(message);
    }
}
