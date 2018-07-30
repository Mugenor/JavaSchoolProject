package javaschool.controller.validator;

import javaschool.controller.validator.annotation.IsEmail;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {
    private EmailValidator emailValidator = new EmailValidator();
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return emailValidator.isValid(s, constraintValidatorContext);
    }

    @Override
    public void initialize(IsEmail constraintAnnotation) {

    }
}
