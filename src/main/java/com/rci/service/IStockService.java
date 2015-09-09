package com.rci.service;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.Stock;
import com.rci.bean.entity.StockOpLog;
import com.rci.service.base.IBaseService;
import com.rci.ui.swing.vos.StockVO;

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
	
	List<Stock> getStocksByDish(String dishNo);
	
	public StockVO getStock(String dishNo);
	
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
	 * Describle(描述)： 添加菜品到库存管理
	 *
	 * 方法名称：rwInitStock
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年7月13日 下午3:57:30
	 *  
	 * @param dishNo
	 */
	void rwAddStock(String dishNo);
	
	/**
	 * 
	 * Describle(描述)： 从库存管理中移除菜品
	 *
	 * 方法名称：rwRemoveStock
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年8月21日 下午3:03:35
	 *  
	 * @param stockId
	 */
	void rwRemoveStock(Long stockId);
	
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
	
	/**
	 * 
	 * Describle(描述)：获取所有被库存管理的菜品编号
	 *
	 * 方法名称：getStockDishNumbers
	 *
	 * 所在类名：IStockService
	 *
	 * Create Time:2015年9月9日 上午11:19:04
	 *  
	 * @return
	 */
	List<String> getStockDishNumbers();
	
}
