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
	@Deprecated
	public void deleteTicketStatistic(String time);
	
	public void deleteTicketInfo(String time);
	
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
	 * Describle(描述)：删除一整天所有相关信息
	 * 1.删除所有订单信息
	 * 2.删除所有标记信息
	 * 3.删除所有流水信息
	 * 4.删除所有代金券统计信息
	 * 5.删除饿了么刷单统计信息
	 * 6.删除当日库存消费信息
	 *
	 * 方法名称：cleanAllOfOneDay
	 *
	 * 所在类名：DataCleanFacade
	 *
	 * Create Time:2015年7月30日 上午8:49:51
	 *  
	 * @param time
	 */
	public void cleanAllOfOneDay(String time);
	
	public void deleteInventoryInfo(String time);
}
