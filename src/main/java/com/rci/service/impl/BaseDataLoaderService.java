/**
 * 
 */
package com.rci.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.StatisticRecord;
import com.rci.bean.entity.account.AccFlow;
import com.rci.bean.entity.account.Account;
import com.rci.bean.entity.inventory.Inventory;
import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.bean.entity.inventory.InventorySellLog;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.enums.BusinessEnums.FlowType;
import com.rci.service.IAccFlowService;
import com.rci.service.IAccountService;
import com.rci.service.IDataLoaderService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IStatisticRecordService;
import com.rci.service.ITableInfoService;
import com.rci.service.filter.CalculateFilter;
import com.rci.service.filter.FilterChain;
import com.rci.service.filter.FreeFilter;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.service.inventory.IInventoryDishRefService;
import com.rci.service.inventory.IInventorySellLogService;
import com.rci.service.inventory.IInventoryService;
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
	@Autowired
	private List<CalculateFilter> filters;
	
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
	
	protected void updateRelativeInfo(List<Order> orders){
		if(CollectionUtils.isEmpty(orders)){
			return ;
		}
		// 解析订单各种账户收入的金额，判断订单使用的方案
		for (Order order : orders) {
			parseOrder(order);
			addInventoryConsumeLog(order);
			updateCostConsumeLog(order);
			updateExpressRecord(order);
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
				costAmount = costAmount.add(dish.getCost());
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

	/* 
	 * @see com.rci.service.IDataLoaderService#parseOrder(com.rci.bean.entity.Order)
	 */
	@Override
	public void parseOrder(Order order) {
		FilterChain chain = new FilterChain();
		Collections.sort(filters, new Comparator<CalculateFilter>() {

			@Override
			public int compare(CalculateFilter f1, CalculateFilter f2) {
				if(f1 instanceof FreeFilter || f2 instanceof FreeFilter){
					return 0;
				}
				return -1;
			}
		});
		chain.addFilters(filters);
		chain.doFilter(order, chain);
	}

	/* 
	 * @see com.rci.service.IDataLoaderService#generateAccountFlow(java.util.Date)
	 */
	@Override
	public void generateAccountFlow(Date date){
		List<AccountSumResult> sumRes = oaService.querySumAmount(date);
		for(AccountSumResult res:sumRes){
			Long accId = res.getAccId();
			BigDecimal amount = res.getSumAmount();
			//1. 根据时间和账户id，数据来源 查找流水
			AccFlow flow = accFlowService.queryUniqueAccFlow(accId, DataGenerateType.AUTO, date);
			if(flow == null){
				//2. 创建流水信息
				flow = new AccFlow(accId);
				flow.setAmount(amount);
				flow.setClassify("销售收入");
				flow.setProject("营业收入");
				flow.setTime(date);
				flow.setFlowType(FlowType.IN);
				flow.setGenerateType(DataGenerateType.AUTO);
				accFlowService.rwCreate(flow);
				//2. 更新账户信息
				updateAccountInfo(accId, amount);
			}else if(flow.getAmount().compareTo(amount) != 0){
				flow.setAmount(amount);
				accFlowService.rwUpdate(flow);
				//2. 更新账户信息
				BigDecimal difference = amount.subtract(flow.getAmount()); //差额
				updateAccountInfo(accId, difference);
			}
		}
	}

	@Deprecated
	@Override
	public void addStockOpLog(Order order, Map<String, BigDecimal> stockMap) {
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo = item.getDishNo();
			Dish dish = dishService.findDishByNo(dishNo);
			if(dish != null){
//				if(YOrN.isY(dish.getStockFlag())){
//					BigDecimal count = item.getCount().subtract(item.getCountback());
//					List<Stock> stocks = stockService.getStocksByDish(dishNo);
//					for(Stock stock:stocks){
//						BigDecimal totalAmount = stock.getAmount().multiply(count);
//						StockOpLog sol = new StockOpLog(dishNo,totalAmount);
//						sol.setConsumeTime(item.getConsumeTime());
//						sol.setDay(order.getDay());
//						sol.setType(StockOpType.CONSUME);
//						sol.setDishName(stock.getDishName());
//						sol.setSno(stock.getSno());
//						BigDecimal storeAmount = stockMap.get(stock.getSno());
//						if(storeAmount != null){
//							storeAmount = storeAmount.add(totalAmount);
//						}else{
//							storeAmount = totalAmount;
//						}
//						stockMap.put(stock.getSno(), storeAmount);
//						stockService.insertStockOpLog(sol);
//					}
//				}
			}
		}
	}
	
	/**
	 * 
	 *
	 * Describle(描述)：更新账户流入金额和余额
	 *
	 * 方法名称：updateAccountInfo
	 *
	 * 所在类名：BaseDataLoaderService
	 *
	 * Create Time:2015年8月26日 下午9:05:49
	 *  
	 * @param accId
	 * @param amount
	 */
	private void updateAccountInfo(Long accId,BigDecimal amount){
		Account account = accService.getAccount(accId);
		BigDecimal earning = BigDecimal.ZERO;
		if(account.getEarningAmount() == null){
			earning = amount;
		}else{
			earning = account.getEarningAmount().add(amount);
		}
		BigDecimal balance = BigDecimal.ZERO;
		if(account.getBalance() == null){
			balance = amount;
		}else{
			balance = account.getBalance().add(amount);
		}
		
		account.setEarningAmount(earning);
		account.setBalance(balance);
		accService.rwUpdate(account);
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
			}
		}
	}
}
