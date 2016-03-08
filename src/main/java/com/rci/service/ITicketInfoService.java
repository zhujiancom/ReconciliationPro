package com.rci.service;

import java.util.Date;
import java.util.List;

import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.TicketInfo;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.base.IBaseService;

public interface ITicketInfoService extends IBaseService<TicketInfo, Long> {
	/**
	 * 
	 * Describle(描述)：根据日期和平台查询代金券使用情况
	 *
	 * 方法名称：queryTicketStatisticByDate
	 *
	 * 所在类名：ITicketInfoService
	 *
	 * Create Time:2015年11月12日 上午11:27:57
	 *  
	 * @param date
	 * @param vendor
	 * @return
	 */
	List<TicketInfo> queryTicketStatisticByDate(Date date,Vendor vendor);
	
	/**
	 * 
	 * Describle(描述)： 根据代金券类型，平台，日期查询代金券使用情况
	 *
	 * 方法名称：queryTicketStatisticByDateAndType
	 *
	 * 所在类名：ITicketInfoService
	 *
	 * Create Time:2015年11月12日 上午11:28:20
	 *  
	 * @param stype
	 * @param date
	 * @param vendor
	 * @return
	 */
	TicketInfo queryTicketStatisticByDateAndType(SchemeType stype, Date date,Vendor vendor);
	
	/**
	 * 
	 * Describle(描述)： 清除指定日期的代金券使用记录
	 *
	 * 方法名称：deleteTicketStatistic
	 *
	 * 所在类名：ITicketInfoService
	 *
	 * Create Time:2015年11月12日 上午11:29:02
	 *  
	 * @param time
	 */
	void deleteTicketStatistic(String time);
	
	void doDeleteTicketStatistic(String time);
}
