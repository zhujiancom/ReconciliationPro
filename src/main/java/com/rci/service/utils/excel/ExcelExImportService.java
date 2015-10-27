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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.annotation.ExcelColumn;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.service.IOrderService;
import com.rci.service.utils.IExImportService;
import com.rci.tools.DateUtil;

@Service("ExcelExImportService")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExcelExImportService implements IExImportService {
	private List<ExcelSheet> customSheets;
	private HSSFWorkbook workbook;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	private List<Order> orders;
	
	@SuppressWarnings("rawtypes")
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
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(customSheet.getTitle());
			Boolean isHeader = customSheet.getHasHeader();
			Class clazz = customSheet.getClazz();
			Collection dataset = customSheet.getDataset();
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
						if(customSheet.getTitleStyle() == null){
							cell.setCellStyle(defaultTitleStyle);
						}else{
							cell.setCellStyle(customSheet.getTitleStyle());
						}
						HSSFRichTextString text = new HSSFRichTextString(columnName);
						cell.setCellValue(text);
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
				for (PropertyDescriptor pdr : pdrs) {
					if(pdr.getPropertyType() == Class.class){
						continue;
					}
					Method rMethod = pdr.getReadMethod();
					ExcelColumn excelColumn = rMethod.getAnnotation(ExcelColumn.class);
					int index = excelColumn.index();
					HSSFCell dataCell = dataRow.createCell(index);
					if(customSheet.getContentStyle() == null){
						dataCell.setCellStyle(defaultContentStyle);
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
			HSSFSheet orderSheet = workbook.getSheetAt(0);
			setOrders(readOrderInfo(orderSheet));
		} catch (IOException ioe) {
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, ioe);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION, e);
		} 
	}
	
	/**
	 * 
	 * Describle(描述)： 读取order DTO 
	 *
	 * 方法名称：readOrderInfo
	 *
	 * 所在类名：ExcelExImportService
	 *
	 * Create Time:2015年8月26日 上午10:50:30
	 *  
	 * @param sheet
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private List<Order> readOrderInfo(HSSFSheet sheet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Order> orders = new ArrayList<Order>();
		int rowNum = sheet.getLastRowNum();
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		for(int j=1;j<=rowNum;j++){
			HSSFRow row = sheet.getRow(j);
			OrderDTO orderDTO = new OrderDTO();
			PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(OrderDTO.class);
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
						Object value = new Object();
						Method wMethod = pdr.getWriteMethod();
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							if(wMethod.getParameterTypes()[0] == BigDecimal.class){
								value = new BigDecimal(cell.getNumericCellValue());
							}else if(wMethod.getParameterTypes()[0] == Timestamp.class){
								Date date = DateUtil.parseTime(String.valueOf(cell.getNumericCellValue()));
								value = new Timestamp(date.getTime()) ;
							}else{
								BigDecimal b = new BigDecimal(cell.getNumericCellValue());
								value = b.toString();
							}
						}else{
							if(wMethod.getParameterTypes()[0] == BigDecimal.class){
								value = new BigDecimal(cell.getStringCellValue());
							}else if(wMethod.getParameterTypes()[0] == Timestamp.class){
								Date date = DateUtil.parseTime(cell.getStringCellValue());
								value = new Timestamp(date.getTime()) ;
							}else{
								value = cell.getStringCellValue();
							}
						}
						wMethod.invoke(orderDTO, new Object[]{value});
					}
				}
				orderDTOs.add(orderDTO);
			}catch(Exception e){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "sheet:"+sheet.getSheetName()+"rowIndex = "+j+",columnIndex="+columnIndex, e);
			}
		}
		Map<String,Order> container = mergerOrder(orderDTOs);
		//2. 迭代order,获取对应的item信息
		Map<String,List<OrderItemDTO>> itemsMapping = readOrderItemInfo(workbook.getSheetAt(1));
		for(Iterator<Entry<String,Order>> it = container.entrySet().iterator();it.hasNext();){
			Entry<String,Order> entry = it.next();
			String key = entry.getKey();
			Order value = entry.getValue();
			value.setDay(DateUtil.date2Str(value.getCheckoutTime(), "yyyyMMdd"));
			//设置关联的order Item
			List<OrderItem> items = new LinkedList<OrderItem>();
			List<OrderItemDTO> itemDTOs = itemsMapping.get(key);
			if(CollectionUtils.isEmpty(itemDTOs)){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "orderNo:"+key+" 没有菜品详细信息数据.");
			}
			for(OrderItemDTO itemDTO:itemDTOs){
				OrderItem item = beanMapper.map(itemDTO, OrderItem.class);
				item.setOrder(value);
				items.add(item);
			}
			value.setItems(items);
			orders.add(value);
		}
		orderService.rwCreate(orders.toArray(new Order[0]));
		return orders;
	}
	
	/**
	 * 
	 * Describle(描述)： 读取order Item DTO
	 *
	 * 方法名称：readOrderItemInfo
	 *
	 * 所在类名：ExcelExImportService
	 *
	 * Create Time:2015年8月26日 上午10:51:10
	 *  
	 * @param sheet
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private Map<String,List<OrderItemDTO>> readOrderItemInfo(HSSFSheet sheet) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Map<String,List<OrderItemDTO>> itemsMapping = new HashMap<String,List<OrderItemDTO>>();
		int rowNum = sheet.getLastRowNum();
//		List<OrderItemDTO> orderItemDTOs = new ArrayList<OrderItemDTO>();
		for(int j=1;j<=rowNum;j++){
			HSSFRow row = sheet.getRow(j);
			OrderItemDTO orderItemDTO = new OrderItemDTO();
			PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(OrderItemDTO.class);
			for (PropertyDescriptor pdr : pdrs) {
				if(pdr.getPropertyType() == Class.class){
					continue;
				}
				Method rMethod = pdr.getReadMethod();
				ExcelColumn excelColumn = rMethod.getAnnotation(ExcelColumn.class);
				if (excelColumn != null) {
					int index = excelColumn.index();
					HSSFCell cell = row.getCell(index);
					Object value = new Object();
					Method wMethod = pdr.getWriteMethod();
					if(wMethod.getParameterTypes()[0] == BigDecimal.class){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							value = new BigDecimal(cell.getNumericCellValue());
						}else{
							value = new BigDecimal(cell.getStringCellValue());
						}
					}else if(wMethod.getParameterTypes()[0] == Timestamp.class){
						Date date = DateUtil.parseTime(cell.getStringCellValue());
						value = new Timestamp(date.getTime()) ;
					}else{
						value = cell.getStringCellValue();
					}
					wMethod.invoke(orderItemDTO, new Object[]{value});
				}
			}
			String key = orderItemDTO.getBillNo();
			if(itemsMapping.containsKey(key)){
				List<OrderItemDTO> orderItemDTOs = itemsMapping.get(key);
				orderItemDTOs.add(orderItemDTO);
				itemsMapping.put(key, orderItemDTOs);
			}else{
				List<OrderItemDTO> orderItemDTOs = new ArrayList<OrderItemDTO>();
				orderItemDTOs.add(orderItemDTO);
				itemsMapping.put(key, orderItemDTOs);
			}
			
		}
		return itemsMapping;
	}
	
	private Map<String,Order> mergerOrder(List<OrderDTO> orderDTOs){
		Map<String,Order> container = new HashMap<String,Order>();
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				Order order = null;
				String orderNo = orderDTO.getOrderNo();
				String paymode = orderDTO.getPaymode();
				BigDecimal realAmount = orderDTO.getRealAmount();
				//2.1 如果容器中存在订单号重复，记录当前订单的支付方式合并到第一条订单中
				if(container.containsKey(orderNo)){
					order = container.get(orderNo);
					order.addPayMode(paymode,realAmount);
					if(realAmount.compareTo(BigDecimal.ZERO) > 0){
						order.setRealAmount(order.getRealAmount().add(realAmount));
					}
					continue;
				}
				//2.2 如果容器中不存在，则初始化设置订单信息，将其加入容器
				order = beanMapper.map(orderDTO, Order.class);
				order.setOriginPrice(orderDTO.getOriginAmount());
				order.addPayMode(paymode,realAmount);
				container.put(orderNo, order);
			}
		}
		return container;
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

	/**
	 * @return the orders
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public <E> Collection<E> getDataSet() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCustomSheet(List<IExcelSheet> customSheets)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}


}
