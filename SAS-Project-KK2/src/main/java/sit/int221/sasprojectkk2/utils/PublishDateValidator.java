package sit.int221.sasprojectkk2.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sit.int221.sasprojectkk2.exceptions.ValidPublishDate;

import java.time.ZonedDateTime;

public class PublishDateValidator implements ConstraintValidator<ValidPublishDate, ZonedDateTime> {

    private ZonedDateTime closeDate;

    public void PublishDateValidator(ZonedDateTime closeDate) {
        this.closeDate = closeDate;
    }

    @Override
    public boolean isValid(ZonedDateTime pDate, ConstraintValidatorContext context) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        if (pDate != null && pDate.isBefore(currentTime)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be a date in the present or in the future")
                    .addConstraintViolation();
            return false;
        }
        if(pDate != null && closeDate != null && pDate.isEqual(closeDate)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be later than publish date")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

