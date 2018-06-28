package javaschool.controller.converter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.mm.yyyy");
    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s, format);
    }
}
