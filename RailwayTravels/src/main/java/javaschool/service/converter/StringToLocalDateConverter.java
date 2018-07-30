package javaschool.service.converter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class StringToLocalDateConverter implements Converter<String, LocalDate>, ClassConverter<String, LocalDate> {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.mm.yyyy");
    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s, format);
    }

    @Override
    public String convertFrom(LocalDate localDate) {
        return localDate.toString(format);
    }

    @Override
    public LocalDate convertTo(String s) {
        return convert(s);
    }
}
