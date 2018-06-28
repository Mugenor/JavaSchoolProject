package javaschool.controller.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javaschool.controller.validator.LocalDateValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.joda.time.LocalDate;

@Documented
@Constraint(validatedBy = LocalDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateConstraint {
    String message() default "Date must be between {minDate} and {maxDate}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String minDate() default "01.01.1950";
    String maxDate() default "01.01.2100";
}
