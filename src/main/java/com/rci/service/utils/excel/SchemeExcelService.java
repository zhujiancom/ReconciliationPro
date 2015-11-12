package com.rci.service.utils.excel;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.annotation.ExcelColumn;
import com.rci.bean.dto.SchemeDTO;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.ISchemeService;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

@Service("SchemeExcelService")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SchemeExcelService extends AbstractExcelOperationService {
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	
	@Autowired
	private Mapper beanMapper;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Collection getDataSet() throws ServiceException {
		return null;
	}

	@Override
	protected void parseSheet(List<HSSFSheet> sheets) throws Exception {
		if(CollectionUtils.isEmpty(sheets)){
			ExceptionManage.throwServiceException("活动数据不存在！");
		}
		List<Scheme> oldSchemes = schemeService.getAll();
		schemeService.rwDelete(oldSchemes.toArray(new Scheme[0])); //删除现有的数据
		HSSFSheet sheet = sheets.get(0);
		int rowNum = sheet.getLastRowNum();
		for(int j=1;j<=rowNum;j++){
			HSSFRow row = sheet.getRow(j);
			SchemeDTO schemeDTO = new SchemeDTO();
			PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(SchemeDTO.class);
			int columnIndex = 0;
			try{
				for (PropertyDescriptor pdr : pdrs) {
					if(pdr.getPropertyType() == Class.class){
						continue;
					}
					Method rMethod = pdr.getReadMethod();
					ExcelColumn excelColumn = rMethod.getAnnotation(ExcelColumn.class);
					if (excelColumn != null) {
						int index = excelColumn.index();
						columnIndex = index;
						HSSFCell cell = row.getCell(index);
						Object value = null;
						Method wMethod = pdr.getWriteMethod();
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							if(wMethod.getParameterTypes()[0] == BigDecimal.class){
								value = new BigDecimal(cell.getNumericCellValue());
							}else if(wMethod.getParameterTypes()[0] == Timestamp.class){
								Date date = DateUtil.parseTime(String.valueOf(cell.getNumericCellValue()));
								value = new Timestamp(date.getTime()) ;
							}else if (wMethod.getParameterTypes()[0].isAssignableFrom(Date.class)){
								value = DateUtil.parseDate(String.valueOf(cell.getNumericCellValue()));
							}else{
								BigDecimal b = new BigDecimal(cell.getNumericCellValue());
								value = b.toString();
							}
						}else{
							if(wMethod.getParameterTypes()[0] == BigDecimal.class){
								if(StringUtils.hasText(cell.getStringCellValue().trim())){
									value = new BigDecimal(cell.getStringCellValue());
								}
							}else if(wMethod.getParameterTypes()[0] == Timestamp.class){
								Date date = DateUtil.parseTime(cell.getStringCellValue());
								if(date != null){
									value = new Timestamp(date.getTime()) ;
								}
							}else if (Date.class.isAssignableFrom(wMethod.getParameterTypes()[0])){
								value = DateUtil.parseDate(cell.getStringCellValue());
							}else if(wMethod.getParameterTypes()[0] == ActivityStatus.class){
								value = ActivityStatus.valueOf(cell.getStringCellValue());
							}else if(wMethod.getParameterTypes()[0] == Vendor.class){
								value = Vendor.valueOf(cell.getStringCellValue());
							}else{
								value = cell.getStringCellValue();
							}
						}
						wMethod.invoke(schemeDTO, new Object[]{value});
					}
				}
				Scheme scheme = beanMapper.map(schemeDTO, Scheme.class);
				schemeService.rwCreate(scheme);
			}catch(Exception e){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "sheet:"+sheet.getSheetName()+",rowIndex = "+j+",columnIndex="+columnIndex, e);
			}
		}
	}

}
