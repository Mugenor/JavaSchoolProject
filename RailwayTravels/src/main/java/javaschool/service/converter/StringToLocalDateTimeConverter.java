package javaschool.service.converter;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

/**
 * The type String to local date time converter.
 */
@Service
public class StringToLocalDateTimeConverter implements ClassConverter<String, LocalDateTime> {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");

    @Override
    public LocalDateTime convertTo(String s) {
        return format.parseLocalDateTime(s);
    }

    @Override
    public String convertFrom(LocalDateTime localDateTime) {
        return localDateTime.toString(format);
    }
}
