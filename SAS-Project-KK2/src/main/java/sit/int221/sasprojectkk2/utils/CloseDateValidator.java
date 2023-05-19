package sit.int221.sasprojectkk2.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sit.int221.sasprojectkk2.exceptions.ValidCloseDate;

import java.time.ZonedDateTime;

public class CloseDateValidator implements ConstraintValidator<ValidCloseDate,ZonedDateTime> {
    @Override
    public boolean isValid(ZonedDateTime cDate, ConstraintValidatorContext context) {
            ZonedDateTime currentTime = ZonedDateTime.now();
            if (cDate != null && cDate.isBefore(currentTime)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("must be a future date")
                        .addConstraintViolation();
                return false;
            }
            if(cDate == null){
                return true;
            }
            return true;

    }
}
