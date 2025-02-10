package com.jhoncontreras.usermanagement.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: Jhon Contreras
 * @Date:   15 nov. 2023
 * Format a LocalDateTime to an custom format
 */

public class DateTimeUtils {
	public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_TIME);
        return dateTime.format(formatter);
    }
	
	public static Date convertToLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
