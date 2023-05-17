package sit.int221.sasprojectkk2.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import sit.int221.sasprojectkk2.dtos.PostAnnouncementDTO;
import sit.int221.sasprojectkk2.dtos.ValidCloseDate;

import java.time.ZonedDateTime;

public class CloseDateValidator implements ConstraintValidator<ValidCloseDate,ZonedDateTime> {
    @Override
    public boolean isValid(ZonedDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        ZonedDateTime current = ZonedDateTime.now();

        return false;
    }


}
