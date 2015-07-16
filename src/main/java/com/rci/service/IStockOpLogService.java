package com.rci.service;

import com.rci.bean.entity.StockOpLog;
import com.rci.service.base.IBaseService;

public interface IStockOpLogService extends IBaseService<StockOpLog, Long> {
	/**
	 * 
	 * Describle(描述)： 清除指定日期的库存信息
	 *
	 * 方法名称：clearStockLogByDay
	 *
	 * 所在类名：IStockOpLogService
	 *
	 * Create Time:2015年7月16日 下午3:04:08
	 *  
	 * @param day
	 */
	void clearStockLogByDay(String day);
}
