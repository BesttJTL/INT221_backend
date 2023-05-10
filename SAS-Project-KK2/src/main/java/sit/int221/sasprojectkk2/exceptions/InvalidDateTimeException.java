package sit.int221.sasprojectkk2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateTimeException extends RuntimeException{
    private String message;

    public InvalidDateTimeException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}