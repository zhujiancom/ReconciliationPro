package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Stock;
import com.rci.bean.entity.StockOpLog;
import com.rci.enums.BusinessEnums.StockOpType;
import com.rci.service.IDishService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IStockOpLogService;
import com.rci.service.IStockService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;

@Service("StockService")
public class StockServiceImpl extends BaseServiceImpl<Stock, Long> implements
		IStockService {
	private Log logger = LogFactory.getLog(StockServiceImpl.class);
	@Resource(name="StockOpLogService")
	private IStockOpLogService oplogService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Override
	public void insertStockOpLog(StockOpLog sol) {
		oplogService.rwCreate(sol);
	}

	@Override
	public Stock getStockByDishNo(String dishNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Stock.class);
		dc.add(Restrictions.eq("dishNo", dishNo));
		Stock stock = baseDAO.queryUniqueByCriteria(dc);
		if(stock != null && stock.getPsid() != 0){
			stock = get(stock.getPsid());
		}
		return stock;
	}
	
	

	@Override
	public void rwInitStock(String dishNo, BigDecimal gross, BigDecimal balance) {
		Stock stock = new Stock(dishNo,gross,balance);
		BigDecimal consumeAmount = gross.subtract(balance);
		stock.setConsumeAmount(consumeAmount);
		Dish dish = dishService.findDishByNo(dishNo);
		stock.setDishName(dish.getDishName());
		super.rwCreate(stock);
	}

	@Override
	public void rwRestock(String sno,BigDecimal amount) {
		if(amount.equals(BigDecimal.ZERO)){
			return;
		}
		Stock stock = getStockBySno(sno);
		BigDecimal gross = stock.getGross().add(amount);
		stock.setGross(gross);
		BigDecimal balanceAmount = stock.getBalanceAmount().add(amount);
		stock.setBalanceAmount(balanceAmount);
		rwUpdate(stock);
		StockOpLog sol = new StockOpLog(sno);
		sol.setType(StockOpType.GROSS_INCREASEMENT);
		sol.setConsumeTime(DateUtil.getCurrentDate());
		sol.setDay(DateUtil.time2Str(DateUtil.getCurrentDate(), "yyyyMMdd"));
		sol.setRestockAmount(amount);
		oplogService.rwCreate(sol);
	}

	@Override
	public Stock getStockBySno(String sno) {
		DetachedCriteria dc = DetachedCriteria.forClass(Stock.class);
		dc.add(Restrictions.eq("sno", sno));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void clearStockByDay(String day) {
		final Map<String,BigDecimal> stockMap = new HashMap<String,BigDecimal>();
		DetachedCriteria dc = DetachedCriteria.forClass(StockOpLog.class);
		dc.setProjection(Projections.projectionList()
					.add(Projections.property("sno"))
					.add(Projections.sum("consumeAmount"))
					.add(Projections.groupProperty("sno")));
		dc.add(Restrictions.eq("day", day)).setResultTransformer(new ResultTransformer() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -4259136921987833057L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				String sno = (String) tuple[0];
				BigDecimal amount = (BigDecimal) tuple[1];
				if(amount != null){
					stockMap.put(sno, amount);
				}
				return null;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		//获取stocklog
		baseDAO.queryListByCriteria(dc);
		Stock[] stocks = new Stock[stockMap.keySet().size()];
		int index = 0;
		for(Iterator<Entry<String,BigDecimal>> it=stockMap.entrySet().iterator();it.hasNext();index++){
			Entry<String,BigDecimal> entry = it.next();
			String sno = entry.getKey();
			BigDecimal amount = entry.getValue();
			Stock stock = getStockBySno(sno);
			if(stock == null){
				logger.error("sno = "+sno+" - stock is null");
			}
			stock.setBalanceAmount(stock.getBalanceAmount().add(amount));
			stock.setConsumeAmount(stock.getConsumeAmount().subtract(amount));
			stocks[index] = stock;
		}
		//更新stocks
		((IStockService)AopContext.currentProxy()).rwUpdate(stocks);
		oplogService.clearStockLogByDay(day);
	}
	
	
	
}
