package sit.int221.sasprojectkk2.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponse {
    private String field;
    private String errorMessage;

    public ErrorResponse(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


