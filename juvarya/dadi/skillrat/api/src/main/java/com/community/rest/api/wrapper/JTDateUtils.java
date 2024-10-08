package com.community.rest.api.wrapper;

import java.util.Calendar;
import java.util.Date;

public class JTDateUtils {
	public static Date addDaysToDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
}
