package com.rci.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
	
	
}
