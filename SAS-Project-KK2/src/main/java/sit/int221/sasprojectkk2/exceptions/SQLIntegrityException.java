package sit.int221.sasprojectkk2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SQLIntegrityException extends SQLException {
    public SQLIntegrityException(String message) {
        super(message);
    }
}
