package it.vige.school;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.getInstance;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static Calendar getCalendarByDate(Date date) {
		Calendar day = getInstance();
		day.setTime(date);
		return day;
	}

	public static Date today() {
		Calendar day = getInstance();
		day.setTime(new Date());
		day.set(HOUR_OF_DAY, 0);
		day.set(MINUTE, 0);
		day.set(SECOND, 0);
		day.set(MILLISECOND, 0);
		return day.getTime();
	}
}
