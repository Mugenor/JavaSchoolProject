package javaschool.controller.validator;

import javaschool.controller.validator.annotation.LocalDateConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateValidator implements ConstraintValidator<LocalDateConstraint, LocalDate> {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.mm.yyyy");
    private boolean isMinDateToday;
    private boolean isMaxDateToday;
    private LocalDate minDate;
    private LocalDate maxDate;

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if(isMinDateToday) {
            minDate = new LocalDate();
        }
        if(isMaxDateToday) {
            maxDate = new LocalDate();
        }
        return localDate.compareTo(minDate) >= 0 && localDate.compareTo(maxDate) <= 0;

    }

    @Override
    public void initialize(LocalDateConstraint constraintAnnotation) {
        if(!constraintAnnotation.minDate().equals("today")) {
            minDate = LocalDate.parse(constraintAnnotation.minDate(), format);
        } else {
            isMinDateToday = true;
        }
        if(!constraintAnnotation.maxDate().equals("today")) {
            maxDate = LocalDate.parse(constraintAnnotation.maxDate(), format);
        } else {
            isMaxDateToday = true;
        }
    }
}
