package uk.co.comment.relational.domain.converter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.persistence.AttributeConverter;

public class DateTimeConverter implements AttributeConverter<DateTime, String> {
    
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    @Override
    public DateTime convertToEntityAttribute(String dbData) {
        return DateTime.parse(dbData, DateTimeFormat.forPattern(DATE_TIME_FORMAT));
    }
    
    @Override
    public String convertToDatabaseColumn(DateTime attribute) {
        return attribute.toString(DATE_TIME_FORMAT);
    }
    
}
