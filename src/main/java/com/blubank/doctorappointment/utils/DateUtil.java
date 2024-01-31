package com.blubank.doctorappointment.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final String ISO8601_DATETIME_FORMAT = "yyyyMMdd'T'HHmmssSSSSSSXXX";
    private static DateFormat iso8601dateFormat = new SimpleDateFormat(ISO8601_DATETIME_FORMAT);

    public static String dateToIso8601Basic(Date theDate, String timeZone) {
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        iso8601dateFormat.setTimeZone(tz);
        return iso8601dateFormat.format(theDate);
    }

    public static Date iso8601BasicToDate(String isoDateAsString) throws ParseException {
        return iso8601dateFormat.parse(isoDateAsString);
    }

    public static Date toDate(LocalTime time) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault()).with(time);
        return Date.from(dateTime.toInstant());
    }

    public static Date toDate(LocalDate date, LocalTime time) {
        LocalDateTime localDateTime = date.atTime(time);
        return java.util.Date
                .from(localDateTime.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static LocalTime toLocalTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.toLocalTime();
    }

    public static LocalDate toLocalDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    public static LocalTime toLocalTime(String localTimeAsString, String delimiter) {
        String[] chunks = localTimeAsString.split(delimiter);
        return LocalTime.of(Integer.parseInt(chunks[0]), Integer.parseInt(chunks[1]));
    }
}
