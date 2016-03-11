/**
 * 
 */
package com.rci.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.StatisticRecord;
import com.rci.bean.entity.inventory.Inventory;
import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.bean.entity.inventory.InventorySellLog;
import com.rci.bean.entity.inventory.SellOffWarning;
import com.rci.enums.BusinessEnums.State;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IAccFlowService;
import com.rci.service.IAccountService;
import com.rci.service.IDataLoaderService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IStatisticRecordService;
import com.rci.service.ITableInfoService;
import com.rci.service.calculatecenter.DefaultOrderParameterValue;
import com.rci.service.calculatecenter.filter.PaymodeFilterChain;
import com.rci.service.inventory.IInventoryDishRefService;
import com.rci.service.inventory.IInventorySellLogService;
import com.rci.service.inventory.IInventoryService;
import com.rci.service.inventory.ISellOffWarningService;
import com.rci.tools.DateUtil;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：BaseDataLoaderService
 *
 * 包名称：com.rci.service.impl
 *
 * Create Time: 2015年8月26日 下午8:24:23
 *
 */
public abstract class BaseDataLoaderService implements IDataLoaderService {
	@Resource(name="defaultFilterChain")
	private PaymodeFilterChain filterChain;
	
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oaService;
	
	@Resource(name="AccountService")
	private IAccountService accService;
	
	@Resource(name="AccFlowService")
	private IAccFlowService accFlowService;
	
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Resource(name="InventoryDishRefService")
	private IInventoryDishRefService idrservice;
	
	@Resource(name="InventoryService")
	private IInventoryService inventoryService;
	
	@Resource(name="InventorySellLogService")
	private IInventorySellLogService sellLogService;
	
	@Resource(name="StatisticRecordService")
	private IStatisticRecordService srService;
	
	@Resource(name="TableInfoService")
	private ITableInfoService tableService;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	
	@Resource(name="SellOffWarningService")
	private ISellOffWarningService inventoryWarningService;
	
	protected void updateRelativeInfo(List<Order> orders){
		if(CollectionUtils.isEmpty(orders)){
			return ;
		}
		// 解析订单各种账户收入的金额，判断订单使用的方案
		for (Order order : orders) {
			filterChain.doFilter(new DefaultOrderParameterValue(order));
			addInventoryConsumeLog(order);	//更新库存消耗
			updateCostConsumeLog(order);	//更新今日成本
			updateExpressRecord(order);    //更新外送率
		}
	}

	private void updateExpressRecord(Order order) {
		try{
			Date queryDate = DateUtil.parseDate(order.getDay(), "yyyyMMdd");
			StatisticRecord record = srService.queryRecordByDate(queryDate);
			if(record == null){
				record = new StatisticRecord();
				record.setDate(queryDate);
				record.setTotalOrders(1);
				record.setSavepoint(DateUtil.getCurrentDate());
				if(tableService.isExpressTable(order.getTableNo())){
					record.setExpressOrders(1);
					record.setExpressRate(new BigDecimal(100));
				}else{
					record.setExpressRate(BigDecimal.ZERO);
				}
				srService.rwCreate(record);
			}else{
				record.setSavepoint(DateUtil.getCurrentDate());
				record.setTotalOrders(record.getTotalOrders()+1);
				if(tableService.isExpressTable(order.getTableNo())){
					record.setExpressOrders(record.getExpressOrders()+1);
				}
				BigDecimal rate = new BigDecimal(record.getExpressOrders()).divide(new BigDecimal(record.getTotalOrders()), 4, BigDecimal.ROUND_HALF_EVEN).movePointRight(2);
				record.setExpressRate(rate);
				srService.rwUpdate(record);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void updateCostConsumeLog(Order order) {
		try {
			List<OrderItem> items = order.getItems();
			BigDecimal costAmount = BigDecimal.ZERO;
			for(OrderItem item:items){
				String dishNo = item.getDishNo();
				Dish dish = dishService.findDishByNo(dishNo);
				if(dish == null){
					dish = transformService.transformDishInfo(dishNo);
				}
				if(dish != null){
					costAmount = costAmount.add(dish.getCost());
				}
			}
			BigDecimal postAmount = oaService.getPostAmountForOrder(order.getOrderNo());
			Date queryDate = DateUtil.parseDate(order.getDay(), "yyyyMMdd");
			StatisticRecord record = srService.queryRecordByDate(queryDate);
			if(record == null){
				record = new StatisticRecord();
				record.setDate(queryDate);
				record.setCostAmount(costAmount);
				record.setTurnoverAmount(postAmount);
				record.setSavepoint(DateUtil.getCurrentDate());
				BigDecimal rate = costAmount.divide(postAmount, 4, BigDecimal.ROUND_HALF_EVEN).movePointRight(2);
				record.setCostRate(rate);
				srService.rwCreate(record);
			}else{
				costAmount = record.getCostAmount().add(costAmount);
				BigDecimal turnoverAmount = record.getTurnoverAmount().add(postAmount);
				record.setCostAmount(costAmount);
				record.setTurnoverAmount(turnoverAmount);
				record.setSavepoint(DateUtil.getCurrentDate());
				BigDecimal rate = costAmount.divide(turnoverAmount, 4, BigDecimal.ROUND_HALF_EVEN).movePointRight(2);
				record.setCostRate(rate);
				srService.rwUpdate(record);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Describle(描述)： 记录库存消费
	 *
	 * 方法名称：addInventoryConsumeLog
	 *
	 * 所在类名：BaseDataLoaderService
	 *
	 * Create Time:2015年12月18日 上午11:11:16
	 *  
	 * @param order
	 */
	private void addInventoryConsumeLog(Order order){
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo = item.getDishNo();
			List<InventoryDishRef> idrs = idrservice.queryByDishNo(dishNo);
			if(CollectionUtils.isEmpty(idrs)){
				continue;
			}
			BigDecimal count = item.getCount().subtract(item.getCountback());
			if(count.compareTo(BigDecimal.ZERO) == 0){
				continue;
			}
			for(InventoryDishRef idr:idrs){
				BigDecimal standard = idr.getStandard(); //产品规格
				BigDecimal consumeAmount = count.multiply(standard);
				Inventory inventory = inventoryService.consume(idr.getIno(), consumeAmount);
				InventorySellLog sellLog = new InventorySellLog();
				sellLog.setDay(order.getDay());
				sellLog.setCheckoutTime(item.getConsumeTime());
				sellLog.setDishno(dishNo);
				sellLog.setConsumeAmount(consumeAmount);
				sellLog.setIname(inventory.getIname());
				sellLog.setPayno(order.getPayNo());
				sellLog.setIno(inventory.getIno());
				sellLogService.rwCreate(sellLog);
				//检查库存是否有货
				checkInventory(order.getDay(),inventory);
			}
		}
	}

	private void checkInventory(String day,Inventory inventory) {
		try{
			SellOffWarning warningInfo = inventoryWarningService.queryValidWarningInfo(inventory.getIno());
			BigDecimal warningLine = inventory.getWarningLine();
			if(warningLine == null){
				warningLine = BigDecimal.ZERO;
			}
			if(inventory.getBalanceAmount().compareTo(warningLine) <= 0){
				if(warningInfo == null){
					warningInfo = new SellOffWarning();
					warningInfo.setIno(inventory.getIno());
					warningInfo.setIname(inventory.getIname());
					warningInfo.setSoDate(DateUtil.parseDate(day, "yyyyMMdd"));
					warningInfo.setState(State.VALID);
					warningInfo.setBalanceAmount(inventory.getBalanceAmount());
					inventoryWarningService.rwCreate(warningInfo);
				}else{
					warningInfo.setBalanceAmount(inventory.getBalanceAmount());
					inventoryWarningService.rwUpdate(warningInfo);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
