package com.rci.service.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import com.rci.exceptions.ServiceException;
import com.rci.service.utils.excel.IExcelSheet;

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
	public void importFrom(InputStream in) throws ServiceException;
	
	/**
	 * 
	 * Describle(描述)：返回原始数据集
	 *
	 * 方法名称：getDataSet
	 *
	 * 所在类名：IExImportService
	 *
	 * Create Time:2015年10月27日 上午11:16:31
	 *  
	 * @return
	 * @throws ServiceException
	 */
	public <T> Collection<T> getDataSet() throws ServiceException;
	
	/**
	 * 
	 * Describle(描述)： 设置sheet数量
	 *
	 * 方法名称：setCustomSheet
	 *
	 * 所在类名：IExImportService
	 *
	 * Create Time:2015年10月27日 下午4:49:15
	 *  
	 * @param customSheets
	 * @throws ServiceException
	 */
	public void setCustomSheet(List<IExcelSheet> customSheets) throws ServiceException;
	
	
}
