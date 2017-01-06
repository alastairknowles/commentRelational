package uk.co.comment.relational.service.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import uk.co.comment.relational.domain.converter.DateTimeConverter;

public class DateTimeConverterTest {
    
    private static final String DATE_STRING = "2001-01-12 02:23:43";
    
    @Test
    public void shouldConvertDateTimeToString() {
        DateTime datetime = new DateTime().withYear(2001).withMonthOfYear(1).withDayOfMonth(12).withHourOfDay(2).withMinuteOfHour(23).withSecondOfMinute(43);
        DateTimeConverter dateTimeConverter = new DateTimeConverter();
        Assert.assertEquals(DATE_STRING, dateTimeConverter.convertToDatabaseColumn(datetime));
    }
    
    @Test
    public void shouldCovertStringToDateTime() {
        DateTimeConverter dateTimeConverter = new DateTimeConverter();
        Assert.assertEquals(new DateTime().withYear(2001).withMonthOfYear(1).withDayOfMonth(12).withHourOfDay(2).withMinuteOfHour(23).withSecondOfMinute(43).withMillisOfSecond(0),
                dateTimeConverter.convertToEntityAttribute(DATE_STRING));
    }
    
}
