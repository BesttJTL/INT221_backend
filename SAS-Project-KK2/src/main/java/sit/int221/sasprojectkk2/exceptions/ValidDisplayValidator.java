package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.NotBlank;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.data.domain.Page;

public class ValidDisplayValidator implements ConstraintValidator<ValidDisplay, String> {

    @Override
    public boolean isValid(String display, ConstraintValidatorContext context) {
        System.out.println("Results "+ display);

//        if(String.valueOf(character).isEmpty()){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("must be either 'Y' or 'N' ")
//                    .addConstraintViolation();
//            return false;
//        }
//        if(display.isEmpty()){
//            return false;
//        }
        if (display == null ) {
            return true;
        }
        else if(display.equals("N") || display.equals("Y")) {
            return true;
        }
        else if(!display.equals("N") && !display.equals("Y")) {
            return false;
        }else{
            return false;
        }

        // Requirement 3: If announcementDisplay is neither 'Y' nor 'N', return false
//        context.disableDefaultConstraintViolation();
//        HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
//        hibernateContext.disableDefaultConstraintViolation();
//        hibernateContext.addMessageParameter("display", String.valueOf(character))
//                .buildConstraintViolationWithTemplate("must be either 'Y' or 'N'")
//                .addConstraintViolation();
    }
}





