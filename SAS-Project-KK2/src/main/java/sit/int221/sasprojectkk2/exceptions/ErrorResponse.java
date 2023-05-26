package sit.int221.sasprojectkk2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private List<DetailError> detail;
    @Getter
    @Setter
    public static class DetailError {
        private String field;
        private String errorMessage;

    }
}
