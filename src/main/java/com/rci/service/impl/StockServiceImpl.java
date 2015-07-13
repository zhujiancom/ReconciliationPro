package com.rci.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Stock;
import com.rci.bean.entity.StockOpLog;
import com.rci.service.IDishService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IStockOpLogService;
import com.rci.service.IStockService;
import com.rci.service.base.BaseServiceImpl;

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
		if(stock.getPsid() != 0){
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
}
