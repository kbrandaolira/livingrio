package br.com.livingrio.utils;

import java.util.Calendar;

import br.com.livingrio.web.params.FilterPeriod;

public class CalendarUtils {

	/**
	 * @param period (ALL, YEAR, MONTH and WEEK
	 * @return Date ONE YEAR BEFORE or MONTH BEFORE or WEEK before
	*/
	public static Calendar getDateAgoBy(FilterPeriod period) {
		Calendar calendar = Calendar.getInstance();
		if( period.equals(FilterPeriod.YEAR) ){
			calendar.set( Calendar.YEAR, calendar.get(Calendar.YEAR)-1);
		}else if( period.equals(FilterPeriod.MONTH) ){
			calendar.set( Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		}else if( period.equals(FilterPeriod.WEEK) ){
			calendar.set( Calendar.DATE, calendar.get(Calendar.DATE)-7);
		}else{
			return null;
		}
		
		return calendar;
	}
	
	public static long secondsDifferenceBetweenTwoDates( Calendar cBefore, Calendar cAfter ){
		return ((cAfter.getTimeInMillis()-cBefore.getTimeInMillis()) / 1000);
		
	}
	
}
