package com.automationstarter.utils;

import com.google.common.collect.Ordering;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.automationstarter.utils.WebDriverUtil.getCurrentUrl;
import static java.util.Calendar.DATE;

public class GeneralUtil {
    
    private static final String datePattern = "yyyy-MM-dd";
    private static final DateFormat dateFormat = new SimpleDateFormat(datePattern);
    
    /**
     * check if the url belongs to rccl or not. If the url is rccl then returns true, false otherwise
     */
    public static boolean isRoyalCaribbeanUrl() {
        return getCurrentUrl().matches("(.*)rccl(.*)");
    }
    
    /**
     * check if the url contains 'category'
     */
    public static boolean isCategoryPresentInUrl() {
        return getCurrentUrl().matches("(.*)category(.*)");
    }
    
    /**
     * check if the url si Admin page
     */
    public static boolean isAdminPage() {
        return getCurrentUrl().matches("(.*)wp-admin(.*)");
    }
    
    /**
     * Get the index of any month name
     */
    public static int getMonthIndex(final String monthName) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(monthName);
        return accessor.get(ChronoField.MONTH_OF_YEAR);
    }
    
    /**
     * Check if the list is sorted
     */
    public static boolean isListSortedInDecOrder(final List<Integer> list) {
        return Ordering.natural().reverse().isOrdered(list);
    }
    
    /**
     * Get the today's date dynamically
     */
    public static String getCurrentDate() {
        return dateFormat.format(new Date());
    }
    
    /**
     * Get an arbitrary date in the future
     */
    public static String getCurrentDatePlusFutureDate(String currentDate, int days) throws ParseException {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new SimpleDateFormat(datePattern).parse(currentDate));
        calender.add(DATE, days);
        return dateFormat.format(calender.getTime());
    }
    
    
}
