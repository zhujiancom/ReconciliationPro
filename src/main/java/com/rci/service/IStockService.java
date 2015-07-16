package com.rci.service;

import java.math.BigDecimal;

import com.rci.bean.entity.Stock;
import com.rci.bean.entity.StockOpLog;
import com.rci.service.base.IBaseService;

public interface IStockService extends IBaseService<Stock, Long> {
	/**
	 * 
	 * Describle(描述)：记录库存变更log
	 *
	 * 方法名称：insertStockOpLog
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月13日 下午2:46:21
	 *  
	 * @param sol
	 */
	void insertStockOpLog(StockOpLog sol);
	
	/**
	 * 
	 * Describle(描述)：根据菜品编号获取该菜品的库存信息
	 *
	 * 方法名称：getStockByDishNo
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月13日 下午2:46:53
	 *  
	 * @param dishNo
	 * @return
	 */
	Stock getStockByDishNo(String dishNo);
	
	/**
	 * 
	 * Describle(描述)： 根据库存编号查找
	 *
	 * 方法名称：getStockBySno
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月14日 下午3:06:25
	 *  
	 * @param sno
	 * @return
	 */
	Stock getStockBySno(String sno);
	
	
	/**
	 * 
	 * Describle(描述)： 库存数据初始化
	 *
	 * 方法名称：rwInitStock
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月13日 下午3:57:30
	 *  
	 * @param dishNo
	 * @param gross
	 * @param balance
	 */
	void rwInitStock(String dishNo,BigDecimal gross,BigDecimal balance);
	
	/**
	 * 
	 * Describle(描述)：进货
	 *
	 * 方法名称：rwRestock
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月14日 下午3:04:31
	 *  
	 * @param sno
	 * @param amount
	 */
	void rwRestock(String sno,BigDecimal amount);
	
	/**
	 * 
	 * Describle(描述)：清空指定日期的库存信息
	 *
	 * 方法名称：clearStockByDay
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月16日 下午2:33:56
	 *  
	 * @param day
	 */
	void clearStockByDay(String day);
	
}
