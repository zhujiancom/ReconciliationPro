package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.account.AccFlow;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class AccFlowRepository extends
		DefaultHibernateDAOFacadeImpl<AccFlow, Long> {

}
