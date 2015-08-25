package com.rci.service.utils;

import java.io.InputStream;
import java.io.OutputStream;

import com.rci.exceptions.ServiceException;

public interface IExImportService {
	/**
	 * 
	 * Describle(描述)：文件导出
	 *
	 * 方法名称：exportTo
	 *
	 * 所在类名：IExImportService
	 *
	 * Create Time:2015年8月25日 下午5:03:16
	 *  
	 * @param out
	 * @throws ServiceException
	 */
	public void exportTo(OutputStream out) throws ServiceException;
	
	/**
	 * Describle(描述)：文件导入
	 *
	 * 方法名称：importFrom
	 *
	 * 所在类名：IExImportService
	 *
	 * Create Time:2015年8月25日 上午11:02:39
	 *  
	 * @param in
	 * @throws ServiceException 文件导入异常
	 */
	public void importFrom(InputStream in)throws ServiceException;
	
	
}
