package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidationUtilsImpl implements ValidationUtils {

    private final Validator validator;

    public ValidationUtilsImpl() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();


    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
