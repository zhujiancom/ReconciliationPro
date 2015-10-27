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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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

public abstract class AbstractExcelOperationService<T> implements IExImportService {
	protected List<IExcelSheet> customSheets;
	private HSSFWorkbook workbook;

	@Override
	public void exportTo(OutputStream out) throws ServiceException {
		// 声明一个工作薄
		if(workbook == null){
			workbook = new HSSFWorkbook();
		}
		if(CollectionUtils.isEmpty(customSheets)){
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, "没有可生成的Sheet");
		}
		for(IExcelSheet customSheet:customSheets){
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(customSheet.getTitle());
			Boolean isHeader = customSheet.isHeader();
			Class<T> clazz = customSheet.getClazz();
			Collection<T> dataset = customSheet.getDataset();
			// 设置表格默认列宽度为30个字节
			sheet.setDefaultColumnWidth(30);
			//默认样式
			HSSFCellStyle defaultContentStyle = customSheet.getDefaultContentCellStyle(workbook);
			HSSFCellStyle defaultTitleStyle = customSheet.getDefaultTitleCellStyle(workbook);
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
						cell.setCellStyle(defaultTitleStyle);
						HSSFRichTextString text = new HSSFRichTextString(columnName);
						cell.setCellValue(text);
					}
				}
				rowIndex++;
			}
			// 遍历集合数据
			Iterator<T> it = dataset.iterator();
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
					int index = excelColumn.index();
					HSSFCell dataCell = dataRow.createCell(index);
					dataCell.setCellStyle(defaultContentStyle);
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
		// TODO Auto-generated method stub

	}

	public List<IExcelSheet> getCustomSheets() {
		return customSheets;
	}

	@Override
	public void setCustomSheet(List<IExcelSheet> customSheets)
			throws ServiceException {
		this.customSheets = customSheets;
	}

}
