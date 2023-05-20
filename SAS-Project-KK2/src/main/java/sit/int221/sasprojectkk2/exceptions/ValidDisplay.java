package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidDisplayValidator.class )
public @interface ValidDisplay {
    String message() default "must be either 'Y' or 'N'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
