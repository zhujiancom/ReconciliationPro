package com.rci.service.utils.excel;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelSheet{
	private HSSFWorkbook workbook;
	
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	private String title;
	
	private Boolean hasHeader = true;
	
	@SuppressWarnings("rawtypes")
	private Collection dataset;
	
	private HSSFCellStyle titleStyle; //标题样式
	
	private HSSFCellStyle contentStyle; //正文内容样式
	
	private HSSFCellStyle textStyle; //文本格式
	
	private HSSFCellStyle dateStyle;

	public ExcelSheet(String title){
		this.title = title;
	}
	
	@SuppressWarnings("rawtypes")
	public ExcelSheet(Class clazz,String title){
		this(title);
		this.clazz = clazz; 
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HSSFCellStyle getTitleStyle() {
		if(titleStyle == null){
			titleStyle = getDefaultTitleCellStyle();
		}
		return titleStyle;
	}

	public void setTitleStyle(HSSFCellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	public HSSFCellStyle getContentStyle() {
		if(contentStyle == null){
			contentStyle = getDefaultContentCellStyle();
		}
		return contentStyle;
	}

	public void setContentStyle(HSSFCellStyle contentStyle) {
		this.contentStyle = contentStyle;
	}
	
	public HSSFCellStyle getDefaultTitleCellStyle(){
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
	
	public HSSFCellStyle getDefaultContentCellStyle(){
		HSSFCellStyle defaultStyle = workbook.createCellStyle();
		defaultStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		return defaultStyle;
	}
	
	public HSSFCellStyle getTextCellStyle(){
		if(textStyle == null){
			textStyle = workbook.createCellStyle();
			HSSFDataFormat fmt = workbook.createDataFormat();
			textStyle.setDataFormat(fmt.getFormat("@"));
			textStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}
		return textStyle;
	}
	
	public HSSFCellStyle getDefaultDateCellStyle(String pattern){
		HSSFCellStyle defaultStyle = workbook.createCellStyle();
		HSSFDataFormat fmt = workbook.createDataFormat();
		defaultStyle.setDataFormat(fmt.getFormat(pattern));
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
	public void setDataset(Collection dataset) {
		this.dataset = dataset;
	}

	@SuppressWarnings("rawtypes")
	public Collection getDataset() {
		return dataset;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public HSSFCellStyle getDateStyle() {
		if(dateStyle == null){
			dateStyle = getDefaultDateCellStyle("yyyy-MM-dd");
		}
		return dateStyle;
	}

	public void setDateStyle(HSSFCellStyle dateStyle) {
		this.dateStyle = dateStyle;
	}
}
