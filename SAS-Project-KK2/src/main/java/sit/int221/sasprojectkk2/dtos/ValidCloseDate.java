package sit.int221.sasprojectkk2.dtos;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int221.sasprojectkk2.utils.CloseDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CloseDateValidator.class)
public @interface ValidCloseDate {

    String message() default "Close date cannot be before publish date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}