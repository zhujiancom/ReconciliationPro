package com.rci.service.utils.excel;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.annotation.ExcelColumn;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.utils.IExImportService;
import com.rci.tools.DateUtil;

@Service("ExcelExImportService")
public class ExcelExImportService implements IExImportService {
	private List<ExcelSheet> customSheets;
	private HSSFWorkbook workbook;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void exportTo(OutputStream out) throws ServiceException {
		// 声明一个工作薄
		if(workbook == null){
			workbook = new HSSFWorkbook();
		}
		if(CollectionUtils.isEmpty(customSheets)){
			ExceptionManage.throwServiceException(SERVICE.EXCEL_GENERATION, "没有可生成的Sheet");
		}
		for(ExcelSheet customSheet:customSheets){
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
				int index = 0;
				for (PropertyDescriptor pdr : pdrs) {
					if(pdr.getPropertyType() == Class.class){
						continue;
					}
					Method rMethod = pdr.getReadMethod();
					ExcelColumn excelColumn = rMethod.getAnnotation(ExcelColumn.class);
					if (excelColumn != null) {
						String columnName = excelColumn.value();
						HSSFCell cell = headerRow.createCell(index);
						if(customSheet.getTitleStyle() == null){
							cell.setCellStyle(customSheet.getDefaultTitleCellStyle(workbook));
						}else{
							cell.setCellStyle(customSheet.getTitleStyle());
						}
						HSSFRichTextString text = new HSSFRichTextString(columnName);
						cell.setCellValue(text);
						index++;
					}
				}
				rowIndex++;
			}
			// 遍历集合数据
			Iterator it = dataset.iterator();
			while (it.hasNext()) {
				HSSFRow dataRow = sheet.createRow(rowIndex);
				Object record = it.next();
				PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(clazz);
				int index = 0;
				for (PropertyDescriptor pdr : pdrs) {
					if(pdr.getPropertyType() == Class.class){
						continue;
					}
					Method rMethod = pdr.getReadMethod();
					HSSFCell dataCell = dataRow.createCell(index);
					if(customSheet.getContentStyle() == null){
						dataCell.setCellStyle(customSheet.getDefaultContentCellStyle(workbook));
					}else{
						dataCell.setCellStyle(customSheet.getContentStyle());
					}
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
						}else{
							dataCell.setCellValue(value.toString());
						}
					} catch (IllegalArgumentException iage) {
						ExceptionManage.throwServiceException(SERVICE.EXCEL_GENERATION, iage);
					} catch (IllegalAccessException iace) {
						ExceptionManage.throwServiceException(SERVICE.EXCEL_GENERATION, iace);
					} catch (InvocationTargetException ite) {
						ExceptionManage.throwServiceException(SERVICE.EXCEL_GENERATION, ite);
					}
				}
				index++;
			}
		}
		
		try{
			workbook.write(out);
			out.close();
		}catch(IOException ioe){
			ExceptionManage.throwServiceException(SERVICE.EXCEL_GENERATION, ioe);
		}
	}

	@Override
	public void importFrom(InputStream in) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public List<ExcelSheet> getCustomSheets() {
		return customSheets;
	}

	public void setCustomSheets(List<ExcelSheet> customSheets) {
		this.customSheets = customSheets;
	}

}
