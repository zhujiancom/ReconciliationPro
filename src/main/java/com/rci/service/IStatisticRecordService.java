package com.rci.service;

import java.util.Date;
import java.util.List;

import com.rci.bean.entity.StatisticRecord;
import com.rci.service.base.IBaseService;

public interface IStatisticRecordService extends
		IBaseService<StatisticRecord, Long> {
	StatisticRecord queryRecordByDate(Date date);
	
	List<StatisticRecord> queryRecords(Date sdate,Date edate);
	
	void deleteByDate(Date date);
	
	void doDeleteByDate(Date date);
}
