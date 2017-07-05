package com.aspect.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ruaidhri on 04/07/2017.
 */
public class DateTimeUtil {

    /**
     * Time Formatter for Seconds Only format
     */
    public static SimpleDateFormat simpleDateFormatSecs = new SimpleDateFormat("ss");

    /** Time Formatter for Mins, Secs Only format */
    public static SimpleDateFormat simpleDateFormatMinsSecs = new SimpleDateFormat("mm:ss");

    /** Time Formatter for Mins, Secs Only format */
    public static SimpleDateFormat simpleDateFormatHrsMinsSecs = new SimpleDateFormat("hh:mm:ss");

    /** Time Formatter for Days Mins, Secs Only format */
    public static SimpleDateFormat simpleDateFormatDaysHrsMinsSecs = new SimpleDateFormat("DD hh:mm:ss");

    /** Time Formatter for Days Mins, Secs Only format */
    public static SimpleDateFormat simpleDateFormatDaysMthsHrsMinsSecs = new SimpleDateFormat("DD-MM hh:mm:ss");

    /** Time Formatter for Days Mins, Secs Only format */
    public static SimpleDateFormat simpleDateFormatDaysMthsYrsHrsMinsSecs = new SimpleDateFormat("DD-MM-YYYY hh:mm:ss");


    /**
     * Convert a Date object to a String
     *
     * @param date date to be parsed into a String
     * @return String representation of Date
     */
    public static String parseDate(Date date) {

        try {
            return simpleDateFormatDaysMthsYrsHrsMinsSecs.format(date);
        } catch (Exception e) {
            //TODO Handle this better
            e.printStackTrace();
        }

        return null;
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
            //TODO handle this better
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
