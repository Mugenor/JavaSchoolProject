package javaschool.entity.converter;

import java.sql.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        return attribute == null ? null : Date.valueOf(attribute.toString());
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : LocalDate.fromDateFields(dbData);
    }
}
