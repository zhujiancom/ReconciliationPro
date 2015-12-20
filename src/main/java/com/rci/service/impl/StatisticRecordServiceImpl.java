package com.rci.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.StatisticRecord;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.service.IStatisticRecordService;
import com.rci.service.base.BaseServiceImpl;

@Service("StatisticRecordService")
public class StatisticRecordServiceImpl extends
		BaseServiceImpl<StatisticRecord, Long> implements
		IStatisticRecordService {

	@Override
	public StatisticRecord queryRecordByDate(Date date) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(StatisticRecord.class);
		sdc.add(SafeRestrictions.eq("date", date));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

	@Override
	public List<StatisticRecord> queryRecords(Date sdate, Date edate) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(StatisticRecord.class);
		sdc.add(SafeRestrictions.and(SafeRestrictions.ge("date", sdate), SafeRestrictions.le("date", edate)))
		.addOrder(Order.asc("date"));
		
		return baseDAO.queryListByCriteria(sdc);
	}

	/* 
	 * @see com.rci.service.IStatisticRecordService#deleteByDate(java.util.Date)
	 */
	@Override
	public void deleteByDate(Date date) {
		StatisticRecord record = queryRecordByDate(date);
		if(record != null){
			((IStatisticRecordService)AopContext.currentProxy()).rwDelete(record);
		}
	}

}
