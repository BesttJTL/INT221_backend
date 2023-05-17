package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDescription implements ConstraintValidator<ValidDesc,String> {
    @Override
    public boolean isValid(String desc, ConstraintValidatorContext context) {
        if (desc == null || desc.isEmpty() || desc.trim().length() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must not be blank")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
