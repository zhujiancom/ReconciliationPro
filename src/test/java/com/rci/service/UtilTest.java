package com.rci.service;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.rci.tools.DateUtil;

public class UtilTest {

	@Test
	public void testTruncate() {
		System.out.println(DateUtil.truncate(DateUtil.getCurrentDate(), Calendar.DATE));
	}
	
	@Test
	public void testGetLastDayOfMonth(){
		System.out.println(DateUtil.addDays(DateUtil.ceiling(DateUtil.getCurrentDate(), Calendar.SECOND),-1));
	}
	
	@Test
	public void testGetFirstDayOfMonth(){
		System.out.println(DateUtil.getFirstDayOfMonth(DateUtil.getCurrentDate()));
	}

}
