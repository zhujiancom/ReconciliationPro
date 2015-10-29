package com.rci.service.utils.excel;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.service.IOrderService;
import com.rci.tools.DateUtil;

@Service("OrderExcelService")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderExcelService extends AbstractExcelOperationService {
	private List<Order> orders = Collections.emptyList();
	
	@Autowired
	private Mapper beanMapper;
	
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Override
	public Collection<Order> getDataSet() throws ServiceException {
		return orders;
	}

	@Override
	protected void parseSheet(List<HSSFSheet> sheets) throws Exception {
		if(CollectionUtils.isEmpty(sheets)){
			ExceptionManage.throwServiceException("订单数据不存在！");
		}
		if(sheets.size() < 2){
			ExceptionManage.throwServiceException("数据不完整,订单菜品销售列表不存在！");
		}
		Map<String,Order> orderContainer = parseOrder(sheets.get(0));
		Map<String,List<OrderItemDTO>> itemsMapping = parseOrderItem(sheets.get(1));
		
		orders = new ArrayList<Order>();
		for(Iterator<Entry<String,Order>> it = orderContainer.entrySet().iterator();it.hasNext();){
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
	}
	
	private Map<String,Order> parseOrder(HSSFSheet orderSheet){
		int rowNum = orderSheet.getLastRowNum();
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		for(int j=1;j<=rowNum;j++){
			HSSFRow row = orderSheet.getRow(j);
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
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "sheet:"+orderSheet.getSheetName()+"rowIndex = "+j+",columnIndex="+columnIndex, e);
			}
		}
		return mergerOrder(orderDTOs);
	}
	
	private Map<String,List<OrderItemDTO>> parseOrderItem(HSSFSheet sheet){
		Map<String,List<OrderItemDTO>> itemsMapping = new HashMap<String,List<OrderItemDTO>>();
		int rowNum = sheet.getLastRowNum();
		try{
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
		}catch(Exception e){
			ExceptionManage.throwServiceException(SERVICE.EXCEL_OPERATION,"解析菜品销售列表出错！",e);
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

}
