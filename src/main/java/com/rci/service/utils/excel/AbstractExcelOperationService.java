package com.rci.service.utils.excel;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.rci.annotation.ExcelColumn;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.utils.IExImportService;
import com.rci.tools.DateUtil;

public abstract class AbstractExcelOperationService implements IExImportService {
	protected List<ExcelSheet> customSheets;
	private HSSFWorkbook workbook;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void exportTo(OutputStream out) throws ServiceException {
		// 声明一个工作薄
		if(workbook == null){
			workbook = new HSSFWorkbook();
		}
		if(CollectionUtils.isEmpty(customSheets)){
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, "没有可生成的Sheet");
		}
		for(ExcelSheet customSheet:customSheets){
			customSheet.setWorkbook(workbook);
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(customSheet.getTitle());
			Boolean isHeader = customSheet.getHasHeader();
			Class clazz = customSheet.getClazz();
			Collection dataset = customSheet.getDataset();
			// 设置表格默认列宽度为30个字节
			sheet.setDefaultColumnWidth(30);
			int rowIndex = 0;
			//设置标题行
			if(isHeader){
				//1. 有标题行
				HSSFRow headerRow = sheet.createRow(rowIndex); //创建第一行为标题行
				PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(clazz);
				for (PropertyDescriptor pdr : pdrs) {
					if(pdr.getPropertyType() == Class.class){
						continue;
					}
					Method rMethod = pdr.getReadMethod();
					ExcelColumn excelColumn = rMethod.getAnnotation(ExcelColumn.class);
					if (excelColumn != null) {
						String columnName = excelColumn.value();
						int index = excelColumn.index();
						HSSFCell cell = headerRow.createCell(index);
						cell.setCellStyle(customSheet.getTitleStyle());
						HSSFRichTextString text = new HSSFRichTextString(columnName);
						cell.setCellValue(text);
					}
				}
				rowIndex++;
			}
			// 遍历集合数据
			Iterator<Object> it = dataset.iterator();
			while (it.hasNext()) {
				HSSFRow dataRow = sheet.createRow(rowIndex);
				Object record = it.next();
				PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(clazz);
				for (PropertyDescriptor pdr : pdrs) {
					if(pdr.getPropertyType() == Class.class){
						continue;
					}
					Method rMethod = pdr.getReadMethod();
					ExcelColumn excelColumn = rMethod.getAnnotation(ExcelColumn.class);
					if(excelColumn == null){
						continue;
					}
					int index = excelColumn.index();
					HSSFCell dataCell = dataRow.createCell(index);
					try {
						Object value = rMethod.invoke(record, new Object[] {});
						if(value ==null){
							dataCell.setCellValue("");
							continue;
						}
						Class<?> ptype = rMethod.getReturnType();
						if(ptype == Timestamp.class){
							Timestamp time = (Timestamp)value;
							dataCell.setCellValue(DateUtil.time2Str(time));
						}else if(ptype == BigDecimal.class){
							BigDecimal number = (BigDecimal)value;
							dataCell.setCellValue(number.doubleValue());
						}else if(ptype == Date.class){
							dataCell.setCellValue(DateUtil.date2Str((Date)value));
							dataCell.setCellStyle(customSheet.getDateStyle());
						}
						else{
							dataCell.setCellValue(value.toString());
							dataCell.setCellStyle(customSheet.getTextCellStyle());
						}
					} catch (IllegalArgumentException iage) {
						ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, iage);
					} catch (IllegalAccessException iace) {
						ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, iace);
					} catch (InvocationTargetException ite) {
						ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, ite);
					}
				}
				rowIndex++;
			}
		}
		
		try{
			workbook.write(out);
			out.close();
		}catch(IOException ioe){
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, ioe);
		}
	}

	@Override
	public void importFrom(InputStream in) throws ServiceException {
		try {
			workbook = new HSSFWorkbook(in);
			int sheetNum = workbook.getNumberOfSheets();
			List<HSSFSheet> sheets = new ArrayList<HSSFSheet>();
			for(int i=0;i<sheetNum;i++){
				sheets.add(workbook.getSheetAt(i));
			}
			parseSheet(sheets);
		} catch (IOException ioe) {
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, ioe);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, e);
		} 
	}

	@Override
	public void setCustomSheet(List<ExcelSheet> customSheets)
			throws ServiceException {
		this.customSheets = customSheets;
	}
	
	protected abstract void parseSheet(List<HSSFSheet> sheets) throws Exception;
}
