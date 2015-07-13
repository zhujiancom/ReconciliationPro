package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.Stock;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class StockRepository extends DefaultHibernateDAOFacadeImpl<Stock, Long> {

}
