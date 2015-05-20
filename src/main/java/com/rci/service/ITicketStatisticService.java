package com.rci.service;

import java.util.Date;

import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.Vendor;


public interface ITicketStatisticService {
	/**
	 * 
	 * Describle(描述)：根据日期和平台查找代金券统计数据
	 *
	 * 方法名称：queryTicketStatisticByDate
	 *
	 * 所在类名：ITicketStatisticService
	 *
	 * Create Time:2015年5月20日 下午2:21:05
	 *  
	 * @param date
	 * @param vendor
	 * @return
	 */
	TicketStatistic queryTicketStatisticByDate(Date date,Vendor vendor);
	
	void rwCreateTicketStatistic(TicketStatistic ts);
	
	void rwUpdateTicketStatistic(TicketStatistic ts);
	
	void rwDeleteTicketStatistic(String time);
}
