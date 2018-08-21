package javaschool.service.converter;

import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * The type Long to local date time converter.
 */
@Service
public class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(Long aLong) {
        return new LocalDateTime(aLong);
    }
}
