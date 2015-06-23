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
	
	public void rwInitSystem();
	
	public void markOrder(String day);
	
	public void deleteMark(String day);
}
