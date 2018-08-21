package javaschool.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.LocalDateTime;

/**
 * The type Local date time attribute converter.
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Long> {
    @Override
    public Long convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute == null ? null : attribute.toDateTime().getMillis();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new LocalDateTime(dbData);
    }
}
