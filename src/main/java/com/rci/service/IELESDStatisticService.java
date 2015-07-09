package com.rci.service;

import java.util.Date;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.service.base.IBaseService;

public interface IELESDStatisticService extends IBaseService<EleSDStatistic, Long>{
	/**
	 * 
	 * Describle(描述)：清除指定日期的刷单统计数据
	 *
	 * 方法名称：clearDataByDate
	 *
	 * 所在类名：IELESDStatisticService
	 *
	 * Create Time:2015年7月9日 下午2:22:00
	 *  
	 * @param date
	 */
	void clearDataByDate(Date date);
	
	/**
	 * 
	 * Describle(描述)： 当日统计数据已生成
	 *
	 * 方法名称：isExistData
	 *
	 * 所在类名：IELESDStatisticService
	 *
	 * Create Time:2015年7月9日 下午2:32:54
	 *  
	 * @param date
	 * @return
	 */
	boolean isExistData(Date date);
	
	/**
	 * 
	 * Describle(描述)：保存刷单信息
	 *
	 * 方法名称：saveSDInfo
	 *
	 * 所在类名：IELESDStatisticService
	 *
	 * Create Time:2015年7月9日 下午2:36:37
	 *  
	 * @param elesd
	 */
	void saveSDInfo(EleSDStatistic elesd);
	
	/**
	 * 
	 * Describle(描述)：查询指定日期的刷单信息
	 *
	 * 方法名称：querySDStatisticByDate
	 *
	 * 所在类名：IELESDStatisticService
	 *
	 * Create Time:2015年7月9日 下午2:52:58
	 *  
	 * @param date
	 * @return
	 */
	EleSDStatistic querySDStatisticByDate(Date date);
}
