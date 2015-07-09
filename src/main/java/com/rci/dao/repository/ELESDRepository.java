package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class ELESDRepository extends
		DefaultHibernateDAOFacadeImpl<EleSDStatistic, Long> {

}
