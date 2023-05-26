package sit.int221.sasprojectkk2.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//@RestControllerAdvice
//public class HandlerMethodException {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<List<ErrorResponse.DetailError>> handleValidationException(MethodArgumentNotValidException ex) {
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        List<ErrorResponse.DetailError> errorResponses = new ArrayList<>();
//        for (FieldError fieldError : fieldErrors) {
//            ErrorResponse.DetailError errorResponse = new ErrorResponse.DetailError();
//            errorResponse.setField(fieldError.getField());
//            errorResponse.setErrorMessage(fieldError.getDefaultMessage());
//            errorResponses.add(errorResponse);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
//    }
//
//
//}

