package com.rci.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.StockOpLog;
import com.rci.service.IStockOpLogService;
import com.rci.service.base.BaseServiceImpl;

@Service("StockOpLogService")
public class StockOpLogServiceImpl extends BaseServiceImpl<StockOpLog, Long>
		implements IStockOpLogService {

	@Override
	public void clearStockLogByDay(String day) {
		DetachedCriteria dc = DetachedCriteria.forClass(StockOpLog.class);
		dc.add(Restrictions.eq("day", day));
		List<StockOpLog> stockLogs = baseDAO.queryListByCriteria(dc);
		((IStockOpLogService)AopContext.currentProxy()).rwDelete(stockLogs.toArray(new StockOpLog[0]));
	}
}
