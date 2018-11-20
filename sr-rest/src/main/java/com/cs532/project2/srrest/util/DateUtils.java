package com.cs532.project2.srrest.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	/**
	 * Format
	 * dd - 	days
	 * mm - 	months
	 * yyyy - 	year
	 */
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static LocalDate parseDate(String date) {
		LocalDate dateOut = null;
		try {
			dateOut = LocalDate.parse(date, formatter);
		} catch (Exception e) {
			// NOP
		} finally {}
		return dateOut;
	}

	public static String formatDate(LocalDate date) {
		return date != null ? formatter.format(date) : null;
	}
}
