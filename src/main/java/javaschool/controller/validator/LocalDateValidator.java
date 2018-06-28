package javaschool.controller.validator;

import javaschool.controller.validator.annotation.LocalDateConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateValidator implements ConstraintValidator<LocalDateConstraint, LocalDate> {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.mm.yyyy");
    private LocalDate minDate;
    private LocalDate maxDate;

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.compareTo(minDate) >= 0 && localDate.compareTo(maxDate) <= 0;
    }

    @Override
    public void initialize(LocalDateConstraint constraintAnnotation) {
        minDate = LocalDate.parse(constraintAnnotation.minDate(), format);
        maxDate = LocalDate.parse(constraintAnnotation.maxDate(), format);
    }
}
