package com.rci.service.core;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.enums.BusinessEnums.Vendor;

public interface StatisticCenterFacade {
	/**
	 * 
	 * Describle(描述)： 统计不同平台当日代金券使用量
	 *
	 * 方法名称：getTicketStatistic
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年6月19日 下午4:50:20
	 *  
	 * @param date
	 * @param vendor
	 * @return
	 */
	String getTicketStatistic(Date date,Vendor vendor);
	
	/**
	 * 
	 * Describle(描述)：统计当日外送单比率
	 *
	 * 方法名称：getExpressRate
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年6月19日 下午4:57:02
	 *  
	 * @param time
	 * @return
	 */
	BigDecimal getExpressRate(String time);
	
	/**
	 * 
	 * Describle(描述)：获取刷单获取的补贴金额
	 *
	 * 方法名称：getSDAllowanceAmount
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年7月9日 下午3:00:49
	 *  
	 * @param date
	 * @return
	 */
	BigDecimal getSDAllowanceAmount(Date date);
}
