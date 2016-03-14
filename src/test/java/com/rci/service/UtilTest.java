package com.rci.service;

import java.util.Calendar;

import org.junit.Test;

import com.rci.bean.entity.Order;
import com.rci.contants.BusinessConstant;
import com.rci.service.calculatecenter.DefaultOrderParameterValue;
import com.rci.tools.DateUtil;
import com.rci.tools.PropertyUtils;
import com.rci.tools.StringUtils;

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
	
	@Test
	public void testStringUtilsJoin(){
		String[] name = new String[]{"a","b","c"};
//		System.out.println(StringUtils.join(name, BusinessConstant.COMMA,"d"));
		DefaultOrderParameterValue value = new DefaultOrderParameterValue(new Order());
		System.out.println(value.joinSchemeName());
	}
	
	@Test
	public void testPropertiesUtils(){
		System.out.println(PropertyUtils.getIntegerValue("notdiscount.dishno"));
		System.out.println(PropertyUtils.getBigDecimalValue("notdiscount.dishno"));
		System.out.println(PropertyUtils.getStringValue("application.name"));
		System.out.println(PropertyUtils.getStringValue("test.message"));
	}

}
