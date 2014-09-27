package org.openntf.evex;

import java.util.Calendar;
import java.util.Date;

public class Helper {

	/**
	 * some date functions
	 */
	public static Calendar getTodayStart() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		return todayStart;
	}
	
	public static Calendar getTodayStartEnd() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 23);
		todayStart.set(Calendar.MINUTE, 59);
		todayStart.set(Calendar.SECOND, 59);
		return todayStart;
	}

	public static Calendar getTodayEnd() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		return todayEnd;
	}
	
	public static Calendar getTodayEndBegin() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 0);
		todayEnd.set(Calendar.MINUTE, 0);
		todayEnd.set(Calendar.SECOND, 0);
		return todayEnd;
	}

	public static Date getTodayStartDate() {
		return getTodayStart().getTime();
	}

	public static Date getTodayEndDate() {
		return getTodayEnd().getTime();
	}
	
	public static Date getDateFromTimeStamp(final String s){
		return new Date(Long.valueOf(s).longValue());
	}
}
