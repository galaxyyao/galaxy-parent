package com.galaxy.common.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.google.common.base.Strings;

public class DateUtil {
	private final static String DATE_PATTERN = "yyyy-MM-dd";
	private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private final static String TIME_PATTERN = "HH:mm";
	private final static String DATE_TIME_PATTERN_COMPACT = "yyyyMMddHHmmssSSS";
	private final static String[] WEEKDAYS = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

	public static LocalDate parseToDate(String dateText) {
		if (Strings.isNullOrEmpty(dateText)) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDate date = LocalDate.parse(dateText, formatter);
		return date;
	}

	public static LocalDateTime parseToDateTime(String dateTimeText) {
		if (Strings.isNullOrEmpty(dateTimeText)) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeText, formatter);
		return dateTime;
	}

	public static String parseFromDate(LocalDate date) {
		if (date == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		String dateText = date.format(formatter);
		return dateText;
	}

	public static String parseFromDateTime(LocalDateTime dateTime) {
		if (dateTime == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		String dateTimeText = dateTime.format(formatter);
		return dateTimeText;
	}

	public static String getCurrentDateTimeText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_COMPACT);
		return LocalDateTime.now().format(formatter);
	}

	public static String parseFromDate(LocalDateTime dateTime) {
		if (dateTime == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		String dateText = dateTime.format(formatter);
		return dateText;
	}

	public static String parseFromTime(LocalDateTime dateTime) {
		if (dateTime == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
		String dateTimeText = dateTime.format(formatter);
		return dateTimeText;
	}

	public static String parseFromUnixMilliTime(String unixStr) {
		if (Strings.isNullOrEmpty(unixStr)) {
			return null;
		}
		Long timestamp = Long.valueOf(unixStr);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
		return parseFromDateTime(localDateTime);
	}

	public static String parseFromUnixOfEpochSecond(String unixStr) {
		if (Strings.isNullOrEmpty(unixStr)) {
			return null;
		}
		Long timestamp = Long.valueOf(unixStr);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),  ZoneOffset.of("+8"));
		return parseFromDateTime(localDateTime);
	}

	public static LocalDateTime parseTimeFromUnixSecondTime(String unixStr) {
		if (Strings.isNullOrEmpty(unixStr)) {
			return null;
		}
		Long timestamp = Long.valueOf(unixStr);
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);
	}
	
	
	public static LocalDateTime parseTimeFromUnixEpochSecond(String unixStr) {
		if (Strings.isNullOrEmpty(unixStr)) {
			return null;
		}
		Long timestamp = Long.valueOf(unixStr);
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.of("+8"));
	}
}
