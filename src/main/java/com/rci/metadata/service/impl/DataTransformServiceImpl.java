package com.rci.metadata.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishSeries;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.Paymode;
import com.rci.bean.entity.TableInfo;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.dto.DishDTO;
import com.rci.metadata.dto.DishSeriesDTO;
import com.rci.metadata.dto.DishTypeDTO;
import com.rci.metadata.dto.HangupTableDTO;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.metadata.dto.PaymodeDTO;
import com.rci.metadata.dto.TableDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDishSeriesService;
import com.rci.service.IDishService;
import com.rci.service.IDishTypeService;
import com.rci.service.IOrderService;
import com.rci.service.IPayModeService;
import com.rci.service.ITableInfoService;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;
import com.rci.ui.swing.vos.HangupTabelInfoVO;
import com.rci.ui.swing.vos.OrderItemVO;

@Service("DataTransformService")
public class DataTransformServiceImpl implements IDataTransformService {
	private static final Log logger = LogFactory.getLog(DataTransformServiceImpl.class);
	@Resource(name="DataFetchService")
	private IDataFetchService fetchService;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DishService")
	private IDishService dishService;
	@Resource(name="DishTypeService")
	private IDishTypeService dishTypeService;
	@Resource(name="DishSeriesService")
	private IDishSeriesService dishSeriesService;
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Resource(name="TableInfoService")
	private ITableInfoService tableService;
	@Override
	public List<Order> transformOrderInfo(Date sdate) {
		Date edate = DateUtil.getEndTimeOfDay(sdate);
		//1. 根据日期获取当前时间之前的符合日期订单
		List<OrderDTO> orderDTOs = fetchService.fetchOrders(sdate, edate);
		List<Order> orders = new ArrayList<Order>();
		Map<String,Order> container = mergerOrder(orderDTOs);
		//2. 迭代order,获取对应的item信息
		for(Iterator<Entry<String,Order>> it = container.entrySet().iterator();it.hasNext();){
			Entry<String,Order> entry = it.next();
			String key = entry.getKey();
			Order value = entry.getValue();
			value.setDay(DateUtil.date2Str(sdate, "yyyyMMdd"));
			List<OrderItemDTO> itemDTOs = fetchService.fetchOrderItems(key);
			//设置关联的order Item
			List<OrderItem> items = new LinkedList<OrderItem>();
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

	@Override
	public void transformDishInfo() {
		List<DishSeriesDTO> seriesDTOs =fetchService.fetchAllDishSeries();
		Map<String,DishSeries> seriesMap = new HashMap<String,DishSeries>();
		List<Dish> dishes = new ArrayList<Dish>();
		for(DishSeriesDTO seriesDTO:seriesDTOs){
			DishSeries series = beanMapper.map(seriesDTO, DishSeries.class);
			seriesMap.put(seriesDTO.getSeriesno(),series);
		}
		List<DishTypeDTO> typeDTOs = fetchService.fetchAllDishTypes();
		Map<String,DishType> typesMap = new HashMap<String,DishType>();
		for(DishTypeDTO typeDTO:typeDTOs){
			if(typeDTO.getTypename().equals("0")){
				continue;
			}
			DishType type = beanMapper.map(typeDTO, DishType.class);
			DishSeries series = seriesMap.get(typeDTO.getSeriesno());
			type.setDishSeries(series);
			dishTypeService.rwCreate(type);
			typesMap.put(typeDTO.getTypeno(), type);
		}
		List<DishDTO> dishDTOs = fetchService.fetchAllDish();
		for(DishDTO dishDTO:dishDTOs){
			Dish dish = beanMapper.map(dishDTO, Dish.class);
			if("0".equals(dishDTO.getDishName())){
				continue;
			}
			DishType type = typesMap.get(dishDTO.getDishType());
			DishSeries series = seriesMap.get(dishDTO.getSeriesno());
			if(type == null || series == null){
				continue;
			}
			dish.setDishType(type);
			dish.setDishSeries(series);
			dish.setStatisticFlag(YOrN.Y);
			dishes.add(dish);
//			dishService.rwCreate(dish);
		}
		dishService.rwCreate(dishes.toArray(new Dish[0]));
	}
	
	public Dish transformDishInfo(String dishno){
		DishDTO dishDTO = fetchService.fetchDishByNo(dishno);
		DishType dType= dishTypeService.queryDishTypeByNo(dishDTO.getDishType());
		if(dType == null){
			DishTypeDTO dTypeDTO = fetchService.fetchDishTypeByNo(dishDTO.getDishType());
			dType = beanMapper.map(dTypeDTO, DishType.class);
			DishSeries series = dishSeriesService.querySeriesByNo(dType.getSeriesno());
			if(series == null){
				DishSeriesDTO seriesDTO = fetchService.fetchDishSeriesByno(dType.getSeriesno());
				series = beanMapper.map(seriesDTO, DishSeries.class);
			}
			dType.setDishSeries(series);
			dishTypeService.rwCreate(dType);
		}
		Dish dish = beanMapper.map(dishDTO, Dish.class);
		dish.setDishType(dType);
		dish.setDishSeries(dType.getDishSeries());
		dish.setStatisticFlag(YOrN.Y);
		dishService.rwCreate(dish);
		return dish;
	}

	@Override
	public void transformPaymodeInfo() {
		List<PaymodeDTO> paymodeDTOs = fetchService.fetchPaymodes();
		List<Paymode> paymodes = new ArrayList<Paymode>();
		for(PaymodeDTO modeDTO:paymodeDTOs){
			Paymode mode = beanMapper.map(modeDTO, Paymode.class);
			paymodes.add(mode);
		}
		paymodeService.rwCreate(paymodes.toArray(new Paymode[0]));
	}
	
	/**
	 * 
	 *
	 * Describle(描述)： 由于订单有多种支付方式，该方法主要是合并订单
	 *
	 * 方法名称：mergerOrder
	 *
	 * 所在类名：DataTransformServiceImpl
	 *
	 * Create Time:2015年4月23日 下午10:47:00
	 *  
	 * @param orderDTOs
	 * @return
	 */
	private Map<String,Order> mergerOrder(List<OrderDTO> orderDTOs){
		Map<String,Order> container = new HashMap<String,Order>();
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				Order order = null;
				String orderNo = orderDTO.getOrderNo();
				String paymode = orderDTO.getPaymode();
				BigDecimal realAmount = orderDTO.getRealAmount();
				logger.debug("orderno: "+ orderNo +" -> paymode "+paymode);
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

	
	@Override
	public void transformTableInfo() {
		List<TableDTO> tableDTOs = fetchService.fetchTables();
		List<TableInfo> tables = new ArrayList<TableInfo>();
		for(TableDTO tdto:tableDTOs){
			tables.add(beanMapper.map(tdto, TableInfo.class));
		}
		tableService.rwCreate(tables.toArray(new TableInfo[0]));
	}

	@Override
	public List<HangupTabelInfoVO> transformHangupTableInfo() {
		List<HangupTabelInfoVO> results = new ArrayList<HangupTabelInfoVO>();
		List<HangupTableDTO> hangupTables = fetchService.fetchHangupTables();
		if(!CollectionUtils.isEmpty(hangupTables)){
			for(HangupTableDTO table:hangupTables){
				HangupTabelInfoVO vo = beanMapper.map(table, HangupTabelInfoVO.class);
				List<OrderItemDTO> items = fetchService.fetchOrderItems(table.getBillno());
				BigDecimal nodiscountAmount = BigDecimal.ZERO;
				if(!CollectionUtils.isEmpty(items)){
					List<OrderItemVO> itemvos = new ArrayList<OrderItemVO>();
					for(OrderItemDTO item:items){
						OrderItemVO itemvo = beanMapper.map(item, OrderItemVO.class);
						String dishno = item.getDishNo();
						BigDecimal count = item.getCount();
						BigDecimal countBack = item.getCountback();
						BigDecimal singlePrice = item.getPrice();
						BigDecimal singleRate = item.getDiscountRate();
						BigDecimal rate = DigitUtil.precentDown(singleRate);
						BigDecimal itemTotalAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countBack)),rate);
						Dish dish = dishService.findDishByNo(dishno);
						if(dish ==null){
							dish = transformDishInfo(dishno);
						}
						itemvo.setDishName(dish.getDishName());
						if(isDishDiscountable(dish)){
							nodiscountAmount = nodiscountAmount.add(itemTotalAmount);
						}
						itemvos.add(itemvo);
					}
					vo.setOrderItems(itemvos);
				}
				BigDecimal discountAmount = table.getConsumAmount().subtract(nodiscountAmount);
				vo.setDiscountAmount(discountAmount);
				vo.setNodiscountAmount(nodiscountAmount);
				results.add(vo);
			}
		}
		return results;
	}

	private boolean isDishDiscountable(Dish dish){
		if(!YOrN.isY(dish.getDiscountFlag())){
			return true;
		}
		return false;
	}
}
