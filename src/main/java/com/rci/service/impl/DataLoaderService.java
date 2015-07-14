package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rci.bean.entity.DataFetchMark;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.Stock;
import com.rci.bean.entity.StockOpLog;
import com.rci.bean.entity.account.AccFlow;
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.enums.BusinessEnums.FlowType;
import com.rci.enums.BusinessEnums.StockOpType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ServiceException;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IAccFlowService;
import com.rci.service.IAccountService;
import com.rci.service.IDataLoaderService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IStockService;
import com.rci.service.core.IMetadataService;
import com.rci.service.filter.CalculateFilter;
import com.rci.service.filter.FilterChain;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;

@Service("DataLoaderService")
public class DataLoaderService implements IDataLoaderService {
	@Autowired
	private List<CalculateFilter> filters;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oaService;
	
	@Resource(name="AccountService")
	private IAccountService accService;
	
	@Resource(name="AccFlowService")
	private IAccFlowService accFlowService;
	
	@Resource(name="StockService")
	private IStockService stockService;
	
	@Resource(name="MetadataService")
	private IMetadataService metadataService;
	
	@Override
	public void load(Date date) {
		String day = DateUtil.date2Str(date, "yyyyMMdd");
		DataFetchMark mark = markService.getMarkRecordByDay(day);
		Date queryEndofDate = DateUtil.getEndTimeOfDay(date);
		List<Order> orders = new ArrayList<Order>();
		if(mark == null){ 
			//1. 如果今天还没有加载过数据，则加载
			orders = transformService.transformOrderInfo(date);
			markService.markOrder(day);
		}else{
			//2. 如果加载过数据，则判断当前时间是否在保存点之后，如果在并且在当天24点之前，则做增量查询
			Date savepoint = mark.getSavepoint();
			if(savepoint != null && savepoint.before(queryEndofDate)){
				orders = transformService.transformOrderInfo(savepoint);
				mark.setSavepoint(DateUtil.getCurrentDate());
				markService.rwUpdate(mark);
			}
		}
		//解析订单各种账户收入的金额，判断订单使用的方案
		Map<String,BigDecimal> stockMap = new HashMap<String,BigDecimal>();
		for(Order order:orders){
			parseOrder(order);
			//插入库存变更记录
			addStockOpLog(order,stockMap);
		}
		//更新库存表
		for(Iterator<Entry<String,BigDecimal>> it=stockMap.entrySet().iterator();it.hasNext();){
			Entry<String,BigDecimal> entry = it.next();
			String sno = entry.getKey();
			BigDecimal amount = entry.getValue();
//			Stock stock = stockService.getStockByDishNo(dishNo);
			Stock stock = stockService.getStockBySno(sno);
			if(stock == null){
				throw new ServiceException(SERVICE.DATA_ERROR, sno+" - 该菜品不在库存控制范围内！");
			}else{
				BigDecimal balanceAmount = stock.getBalanceAmount().subtract(amount);
				BigDecimal consumeAmount = stock.getConsumeAmount().add(amount);
				stock.setBalanceAmount(balanceAmount);
				stock.setConsumeAmount(consumeAmount);
				stockService.rwUpdate(stock);
			}
		}
		
		//生成账单流水
		try {
			generateAccountFlow(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parseOrder(Order order) {
		FilterChain chain = new FilterChain();
		chain.addFilters(filters);
		chain.doFilter(order, chain);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
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
	
	/**
	 * 
	 * Describle(描述)：更新账户流入金额和余额
	 *
	 * 方法名称：updateAccountInfo
	 *
	 * 所在类名：DataLoaderService
	 *
	 * Create Time:2015年4月28日 下午2:10:57
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

	@Override
	public void addStockOpLog(Order order,Map<String,BigDecimal> stockMap) {
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo = item.getDishNo();
			Dish dish = metadataService.getDishByNo(dishNo);
			if(dish != null){
				if(YOrN.isY(dish.getStockFlag())){
					BigDecimal amount = item.getCount().subtract(item.getCountback());
					BigDecimal storeAmount = stockMap.get(dishNo);
					if(storeAmount != null){
						storeAmount = storeAmount.add(amount);
					}else{
						storeAmount = amount;
					}
					
					StockOpLog sol = new StockOpLog(dishNo,amount);
					sol.setConsumeTime(item.getConsumeTime());
					sol.setDay(order.getDay());
					sol.setType(StockOpType.CONSUME);
					sol.setDishName(dish.getDishName());
					Stock stock = stockService.getStockByDishNo(dishNo);
					if(stock != null){
						sol.setSno(stock.getSno());
						stockMap.put(stock.getSno(), storeAmount);
					}
					stockService.insertStockOpLog(sol);
				}
			}
		}
	}
}
