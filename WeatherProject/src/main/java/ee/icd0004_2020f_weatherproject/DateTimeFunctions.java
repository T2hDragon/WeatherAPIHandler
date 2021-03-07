package ee.icd0004_2020f_weatherproject;

import java.util.Calendar;
import java.util.Date;

public class DateTimeFunctions {

    /**
     * get a part of the date (like hour/day/..) using Calendar and its inner int constants values.
     * @param date given date
     * @param calendarFlag Calendar constant value
     * @return part of given date
     */
    private static int getPartOfDate(Date date, int calendarFlag) {
        return new Calendar
                .Builder()
                .setInstant(date)
                .build()
                .get(calendarFlag);
    }

    public static int getHourOfDay(Date date) {
        return getPartOfDate(date, Calendar.HOUR_OF_DAY);
    }

    public static int getDayOfYear(Date date) {
        return getPartOfDate(date, Calendar.DAY_OF_YEAR);
    }

    private static int getYear(Date date) {
        return getPartOfDate(date, Calendar.YEAR);
    }

    public static boolean areSameDays(Date d1, Date d2) {
        return getDayOfYear(d1) == getDayOfYear(d2) &&
                getYear(d1) == getYear(d2);
    }

}
