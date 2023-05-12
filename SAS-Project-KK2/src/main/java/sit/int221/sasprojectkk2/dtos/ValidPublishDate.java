package sit.int221.sasprojectkk2.dtos;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int221.sasprojectkk2.utils.PublishDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PublishDateValidator.class)
public @interface ValidPublishDate {
    String message() default "Publish date cannot be in the past.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
