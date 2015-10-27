package com.rci.service.utils.excel;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IExcelSheet {
	/**
	 * 
	 * Describle(描述)： 数据类型
	 *
	 * 方法名称：getClazz
	 *
	 * 所在类名：IExcelSheet
	 *
	 * Create Time:2015年10月27日 下午3:37:13
	 * @param <T>
	 *  
	 * @return
	 */
	<T> Class<T> getClazz();
	
	/**
	 * 
	 * Describle(描述)： 数据集
	 *
	 * 方法名称：getDataset
	 *
	 * 所在类名：IExcelSheet
	 *
	 * Create Time:2015年10月27日 下午3:37:01
	 *  
	 * @return
	 */
	<T> Collection<T> getDataset();
	
	/**
	 * 
	 * Describle(描述)： 默认标题头样式
	 *
	 * 方法名称：getDefaultTitleCellStyle
	 *
	 * 所在类名：IExcelSheet
	 *
	 * Create Time:2015年10月27日 下午3:36:48
	 *  
	 * @param workbook
	 * @return
	 */
	HSSFCellStyle getDefaultTitleCellStyle(HSSFWorkbook workbook);
	
	/**
	 * 
	 * Describle(描述)： 默认内容区样式
	 *
	 * 方法名称：getDefaultContentCellStyle
	 *
	 * 所在类名：IExcelSheet
	 *
	 * Create Time:2015年10月27日 下午3:36:24
	 *  
	 * @param workbook
	 * @return
	 */
	HSSFCellStyle getDefaultContentCellStyle(HSSFWorkbook workbook);
	
	/**
	 * 
	 * Describle(描述)： sheet 名称
	 *
	 * 方法名称：getTitle
	 *
	 * 所在类名：IExcelSheet
	 *
	 * Create Time:2015年10月27日 下午3:36:12
	 *  
	 * @return
	 */
	String getTitle();
	
	/**
	 * 
	 * Describle(描述)：是否有标题头
	 *
	 * 方法名称：isHeader
	 *
	 * 所在类名：IExcelSheet
	 *
	 * Create Time:2015年10月27日 下午3:39:26
	 *  
	 * @return
	 */
	boolean isHeader();
}
