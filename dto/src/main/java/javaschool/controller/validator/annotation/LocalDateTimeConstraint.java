package javaschool.controller.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javaschool.controller.validator.LocalDateTimeValidator;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = LocalDateTimeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateTimeConstraint {
    String message() default "Date must be between {minDateTime} and {maxDateTime}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String minDateTime() default "01.01.1950, 00:00:00";
    String maxDateTime() default "01.01.2100, 00:00:00";
}
