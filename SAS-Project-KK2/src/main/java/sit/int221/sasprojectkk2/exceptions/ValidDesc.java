package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidDescription.class )
public @interface ValidDesc {
    String message() default "must cannot be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
