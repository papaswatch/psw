package com.papaswatch.psw.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DateTimeUtils {

    public static String YEAR_TO_DATE_SLASH = "yyyy/MM/dd";

    public static String currentYearToDateSlash() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(YEAR_TO_DATE_SLASH));
    }
}
