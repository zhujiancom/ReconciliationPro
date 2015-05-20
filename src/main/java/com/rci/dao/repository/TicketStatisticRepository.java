package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.TicketStatistic;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class TicketStatisticRepository extends DefaultHibernateDAOFacadeImpl<TicketStatistic, Long>{

}
