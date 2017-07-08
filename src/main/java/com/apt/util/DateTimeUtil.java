package com.apt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Untility class to handle Date and Time formatting
 */
public class DateTimeUtil {

    /**
     * Time Formatter for Days Mins, Secs Only format
     */
    private static SimpleDateFormat simpleDateFormatDaysMthsYrsHrsMinsSecs = new SimpleDateFormat("DD-MM-YYYY hh:mm:ss");


    /**
     * Convert a Date object to a String
     *
     * @param date date to be parsed into a String
     * @return String representation of Date
     */
    public static String parseDate(Date date) {
        return simpleDateFormatDaysMthsYrsHrsMinsSecs.format(date);
    }

    /**
     * Converts a String to a Date object
     *
     * @param date String of date to be composed into Date object
     * @return Date object representation of String
     */
    public static Date composeDate(String date) throws ParseException {
        Date startDate;
        try {
            startDate = simpleDateFormatDaysMthsYrsHrsMinsSecs.parse(date);
            return startDate;
        } catch (ParseException e) {
            System.out.println("Error parsing String for Date");
            throw e;
        }

    }

    /**
     * Gets the current time in miliseconds
     *
     * @return the current time in miliseconds
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
