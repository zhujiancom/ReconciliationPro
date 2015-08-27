package com.rci.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.exceptions.ServiceException;

public interface IDataLoaderService {
	/**
	 * 
	 * Describle(描述)：从sqlserver中加载订单相关的metadata到本地数据库
	 *
	 * 方法名称：load
	 *
	 * 所在类名：IDataLoaderService
	 *
	 * Create Time:2015年4月24日 下午3:52:37
	 *  
	 * @param date
	 */
	void load(Date date);
	
	void load(InputStream in,Date date) throws ServiceException;
	/**
	 * 
	 * Describle(描述)：解析订单各种账户收入的金额，判断订单使用的方案
	 *
	 * 方法名称：parseOrder
	 *
	 * 所在类名：IDataLoaderService
	 *
	 * Create Time:2015年4月24日 下午3:52:31
	 *  
	 * @param order
	 */
	void parseOrder(Order order);
	
	/**
	 * 
	 * Describle(描述)：生成账户流水
	 *
	 * 方法名称：generateAccountFlow
	 *
	 * 所在类名：IDataLoaderService
	 *
	 * Create Time:2015年4月28日 上午10:05:15
	 *  
	 * @param orderNo
	 */
	void generateAccountFlow(Date date) throws Exception;
	
	void addStockOpLog(Order order,Map<String,BigDecimal> stockMap);
}
