package com.jobtrackingtool.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatDateWithOrdinal(LocalDate date) {
        int day = date.getDayOfMonth();
        String daySuffix = getDaySuffix(day);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d'" + daySuffix + "'"));
        return formattedDate;
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }
}
