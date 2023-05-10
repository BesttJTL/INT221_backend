package sit.int221.sasprojectkk2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoAnnException extends RuntimeException{
    private String message;

    public NoAnnException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
