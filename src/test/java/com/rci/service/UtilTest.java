package com.rci.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.rci.tools.DateUtil;

public class UtilTest {

	@Test
	public void testTruncate() {
		System.out.println(DateUtil.truncate(DateUtil.getCurrentDate(), Calendar.PM));
	}
	
	@Test
	public void testGetLastDayOfMonth(){
		System.out.println(DateUtil.addDays(DateUtil.ceiling(DateUtil.getCurrentDate(), Calendar.SECOND),-1));
	}
	
	@Test
	public void testGetFirstDayOfMonth(){
		System.out.println(DateUtil.getFirstDayOfMonth(DateUtil.getCurrentDate()));
	}
	
	@Test
	public void testGetTimeStamp(){
//		System.out.println(DateUtil.ceiling(DateUtil.getCurrentDate(),Calendar.HOUR));
		System.out.println(DateUtil.getFragmentInHours(DateUtil.getCurrentDate(),Calendar.DATE));
		System.out.println(DateUtil.getFragmentInMinutes(DateUtil.getCurrentDate(),Calendar.HOUR_OF_DAY));
		System.out.println(DateUtil.getFragmentInSeconds(DateUtil.getCurrentDate(),Calendar.MINUTE));
	}

}
