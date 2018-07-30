package javaschool.controller.validator;

import javaschool.controller.validator.annotation.LocalDateConstraint;
import javaschool.controller.validator.annotation.LocalDateTimeConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateTimeValidator implements ConstraintValidator<LocalDateTimeConstraint, LocalDateTime> {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm:ss");
    public static final String NOW = "now";
    private boolean isMinDateTimeNow;
    private boolean isMaxDateTimeNow;
    private LocalDateTime minDateTime;
    private LocalDateTime maxDateTime;

    @Override
    public boolean isValid(LocalDateTime localDate, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(localDate);
        if(isMinDateTimeNow) {
            minDateTime = new LocalDateTime();
        }
        if(isMaxDateTimeNow) {
            maxDateTime = new LocalDateTime();
        }
        return localDate.compareTo(minDateTime) >= 0 && localDate.compareTo(maxDateTime) <= 0;

    }

    @Override
    public void initialize(LocalDateTimeConstraint constraintAnnotation) {
        if(!constraintAnnotation.minDateTime().equals(NOW)) {
            minDateTime = LocalDateTime.parse(constraintAnnotation.minDateTime(), format);
        } else {
            isMinDateTimeNow = true;
        }
        if(!constraintAnnotation.maxDateTime().equals(NOW)) {
            maxDateTime = LocalDateTime.parse(constraintAnnotation.maxDateTime(), format);
        } else {
            isMaxDateTimeNow = true;
        }
    }
}
