package sit.int221.sasprojectkk2.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int221.sasprojectkk2.repositories.CategoryRepository;


public class ValidCategoryValidator implements ConstraintValidator<ValidCategory, Integer> {

    @Autowired
    private CategoryRepository repo;

    @Override
    public boolean isValid(Integer categoryId, ConstraintValidatorContext context) {
        if (categoryId == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must not be null")
                    .addConstraintViolation();
            return false;
        }

        if (!repo.existsById(categoryId)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("does not exists")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
