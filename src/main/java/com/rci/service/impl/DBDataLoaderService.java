package com.rci.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.DataFetchMark;
import com.rci.bean.entity.Order;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IFetchMarkService;
import com.rci.tools.DateUtil;

/**
 * 
 * remark (备注): 从sqlserver 数据库中加载数据
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：DataLoaderService
 *
 * 包名称：com.rci.service.impl
 *
 * Create Time: 2015年8月26日 下午9:08:44
 *
 */
@Service("DBDataLoaderService")
public class DBDataLoaderService extends BaseDataLoaderService {
	
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
		// 更新账单使用方案和库存数据
		updateRelativeInfo(orders);
		// 生成账单流水
		generateAccountFlow(date);
	}

	/* 
	 * @see com.rci.service.IDataLoaderService#load(java.io.InputStream)
	 */
	@Override
	public void load(InputStream in,Date date) {
		throw new UnsupportedOperationException("不支持从Inputstream流中加载数据...");
	}
}
