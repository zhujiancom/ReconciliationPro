package com.rci.service.utils.excel;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.rci.tools.GenericsUtils;

public class ExcelSheetImpl<T> implements IExcelSheet {
	private String title;
	
	private Collection<T> dataset;
	
	private boolean header = false;
	
	public ExcelSheetImpl(String title){
		this.title = title;
		header = true;
	}
	
	@Override
	public HSSFCellStyle getDefaultTitleCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle defaultStyle = workbook.createCellStyle();
		//前景色
		defaultStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		//边框设置
		defaultStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		defaultStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 	//1.下边框
		defaultStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);		//2.左边框
		defaultStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);		//3.右边框
		defaultStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);		//4.上边框
		//文本对齐方式
		defaultStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//自适应宽度
		defaultStyle.setShrinkToFit(true);
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short)14);
		font.setFontName("微软雅黑");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		defaultStyle.setFont(font);
		return defaultStyle;
	}

	@Override
	public HSSFCellStyle getDefaultContentCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle defaultStyle = workbook.createCellStyle();
		defaultStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		return defaultStyle;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDataset(Collection<T> dataset) {
		this.dataset = dataset;
	}

	@Override
	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Class<T> getClazz() {
		return GenericsUtils.getSuperClassGenericType(getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getDataset() {
		return dataset;
	}

}
