package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidTypeValidate implements ConstraintValidator<ValidTitle,String> {

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (title == null || title.isEmpty() || title.trim().length() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must not be blank")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
