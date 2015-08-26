package com.rci.service.utils.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelSheet{
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	private String title;
	
	private Boolean hasHeader;
	
	@SuppressWarnings("rawtypes")
	private List dataset;
	
	private HSSFCellStyle titleStyle; //标题样式
	
	private HSSFCellStyle contentStyle; //正文内容样式

	public ExcelSheet(String title){
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HSSFCellStyle getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(HSSFCellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	public HSSFCellStyle getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(HSSFCellStyle contentStyle) {
		this.contentStyle = contentStyle;
	}
	
	public HSSFCellStyle getDefaultTitleCellStyle(HSSFWorkbook workbook){
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
	
	public HSSFCellStyle getDefaultContentCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle defaultStyle = workbook.createCellStyle();
		defaultStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		return defaultStyle;
	}

	public Boolean getHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(Boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	@SuppressWarnings("rawtypes")
	public Class getClazz() {
		return clazz;
	}

	@SuppressWarnings("rawtypes")
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("rawtypes")
	public List getDataset() {
		return dataset;
	}

	@SuppressWarnings("rawtypes")
	public void setDataset(List dataset) {
		this.dataset = dataset;
	}


}
