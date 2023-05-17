package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int221.sasprojectkk2.repositories.CategoryRepository;

public class ValidCategoryValidator implements ConstraintValidator<ValidCategory,Integer> {

    @Autowired
    private CategoryRepository repo;

    @Override
    public boolean isValid(Integer categoryId, ConstraintValidatorContext context) {
        try{
            if(!(repo.existsById(categoryId))){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("does not exists")
                        .addConstraintViolation();
                return false;
            }
            if (repo.findById(categoryId).isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("does not exists")
                        .addConstraintViolation();
                return false;
            }
            return true;

        }catch (IllegalArgumentException e){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("does not exists")
                    .addConstraintViolation();
            return false;
        }

    }
}
