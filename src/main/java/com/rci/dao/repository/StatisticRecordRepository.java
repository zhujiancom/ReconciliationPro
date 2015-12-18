package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.StatisticRecord;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class StatisticRecordRepository extends
		DefaultHibernateDAOFacadeImpl<StatisticRecord, Long> {

}
