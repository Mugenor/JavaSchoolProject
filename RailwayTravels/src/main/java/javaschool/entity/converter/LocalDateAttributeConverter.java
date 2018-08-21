package javaschool.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.LocalDate;

/**
 * The type Local date attribute converter.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Long> {
    @Override
    public Long convertToDatabaseColumn(LocalDate attribute) {
        return attribute == null ? null : attribute.toDate().getTime();
    }

    @Override
    public LocalDate convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new LocalDate(dbData);
    }
}
