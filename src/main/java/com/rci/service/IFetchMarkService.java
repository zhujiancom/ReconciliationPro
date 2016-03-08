package com.rci.service;

import com.rci.bean.entity.DataFetchMark;
import com.rci.service.base.IBaseService;

/**
 * 
 * remark (备注):系统各项操作标记服务
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：IFetchMarkService
 *
 * 包名称：com.rci.service
 *
 * Create Time: 2015年6月23日 上午11:18:21
 *
 */
public interface IFetchMarkService extends IBaseService<DataFetchMark, Long>{
	public DataFetchMark getMarkRecordByDay(String day);
	
	public boolean isSystemInit();
	
	/**
	 * 
	 * Describle(描述)：系统基础数据初始化
	 *
	 * 方法名称：rwInitSystem
	 *
	 * 所在类名：IFetchMarkService
	 *
	 * Create Time:2015年7月13日 下午3:35:01
	 *
	 */
	public void rwInitSystem();
	
	/**
	 * 
	 * Describle(描述)： 库存数据初始化
	 *
	 * 方法名称：rwInitStock
	 *
	 * 所在类名：IFetchMarkService
	 *
	 * Create Time:2015年7月13日 下午3:35:36
	 *
	 */
	public void rwInitStock();
	
	public void markOrder(String day);
	
	public void deleteMark(String day);
	
	public void doDeleteMark(String day);
	
}
