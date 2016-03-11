package com.rci.service;

import java.io.InputStream;
import java.util.Date;

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
}
