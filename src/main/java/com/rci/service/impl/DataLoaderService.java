package com.rci.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DataFetchMark;
import com.rci.bean.entity.Order;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDataLoaderService;
import com.rci.service.IFetchMarkService;
import com.rci.service.filter.CalculateFilter;
import com.rci.service.filter.FilterChain;
import com.rci.tools.DateUtil;

@Service("DataLoaderService")
public class DataLoaderService implements IDataLoaderService {
	@Autowired
	private List<CalculateFilter> filters;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	
	@Override
	public void load(Date date) {
		String day = DateUtil.date2Str(date, "yyyyMMdd");
		DataFetchMark mark = markService.getMarkRecordByDay(day);
		Date queryEndofDate = DateUtil.getEndTimeOfDay(date);
		List<Order> orders = new ArrayList<Order>();
		if(mark == null){ 
			//1. 如果今天还没有加载过数据，则加载
			orders = transformService.transformOrderInfo(date);
			markService.rwOrderMark(day);
		}else{
			//2. 如果加载过数据，则判断当前时间是否在保存点之后，如果在并且在当天24点之前，则做增量查询
			Date savepoint = mark.getSavepoint();
			if(savepoint != null && savepoint.before(queryEndofDate)){
				orders = transformService.transformOrderInfo(date);
				markService.rwUpdateMark(mark);
			}
		}
		//解析订单各种账户收入的金额，判断订单使用的方案
		for(Order order:orders){
			parseOrder(order);
		}
	}

	@Override
	public void parseOrder(Order order) {
		FilterChain chain = new FilterChain();
		chain.addFilters(filters);
		chain.doFilter(order, chain);
	}
	

}
