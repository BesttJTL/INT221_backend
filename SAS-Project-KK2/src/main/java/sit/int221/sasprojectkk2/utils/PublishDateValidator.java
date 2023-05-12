package sit.int221.sasprojectkk2.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sit.int221.sasprojectkk2.dtos.ValidPublishDate;

import java.time.ZonedDateTime;

public class PublishDateValidator implements ConstraintValidator<ValidPublishDate, ZonedDateTime> {
    @Override
    public boolean isValid(ZonedDateTime value, ConstraintValidatorContext context){
        if(value == null){
            return true;
        }
        ZonedDateTime current = ZonedDateTime.now();
        return value.isEqual(current) || value.isAfter(current);
    }
}
