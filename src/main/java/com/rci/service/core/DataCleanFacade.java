package com.rci.service.core;

import com.rci.enums.BusinessEnums.DataGenerateType;

public interface DataCleanFacade {
	/**
	 * 
	 * Describle(描述)： 删除指定日期的订单
	 *
	 * 方法名称：deleteOrders
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月29日 下午5:12:10
	 *  
	 * @param time
	 */
	public void deleteOrders(String time);
	
	/**
	 * 
	 * Describle(描述)：删除标记
	 *
	 * 方法名称：deleteMark
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月29日 下午5:12:41
	 *  
	 * @param time
	 */
	public void deleteMark(String time);
	
	/**
	 * 
	 * Describle(描述)： 删除流水信息
	 *
	 * 方法名称：deleteFlowInfo
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月29日 下午5:13:32
	 *  
	 * @param time
	 * @param type
	 */
	public void deleteFlowInfo(String time,DataGenerateType type);
	
	/**
	 * 
	 * Describle(描述)： 删除代金券使用信息
	 *
	 * 方法名称：deleteTicketStatistic
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月29日 下午5:14:13
	 *  
	 * @param time
	 */
	public void deleteTicketStatistic(String time);
	
	/**
	 * 
	 * Describle(描述)：删除饿了么刷单信息
	 *
	 * 方法名称：deleteELESDInfo
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月29日 下午5:15:11
	 *  
	 * @param time
	 */
	public void deleteELESDInfo(String time);
	
	/**
	 * 
	 * Describle(描述)：删除库存信息
	 *
	 * 方法名称：deleteStockInfo
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月29日 下午5:15:49
	 *  
	 * @param time
	 */
	public void deleteStockInfo(String time);
}
