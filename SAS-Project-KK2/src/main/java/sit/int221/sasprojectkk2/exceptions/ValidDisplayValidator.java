package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.NotBlank;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.data.domain.Page;

public class ValidDisplayValidator implements ConstraintValidator<ValidDisplay, Character> {

    @Override
    public boolean isValid(Character character, ConstraintValidatorContext context) {
        System.out.println("Results "+character);

//        if(String.valueOf(character).isEmpty()){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("must be either 'Y' or 'N' ")
//                    .addConstraintViolation();
//            return false;
//        }

        if (character == null) {
            return false;
        }

        if(character.equals('N') || character.equals('Y')) {
            return true;
        }

        // Requirement 3: If announcementDisplay is neither 'Y' nor 'N', return false
//        context.disableDefaultConstraintViolation();
//        HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
//        hibernateContext.disableDefaultConstraintViolation();
//        hibernateContext.addMessageParameter("display", String.valueOf(character))
//                .buildConstraintViolationWithTemplate("must be either 'Y' or 'N'")
//                .addConstraintViolation();
        return false;
    }
}





