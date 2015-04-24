package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class OrderAccountRefRepository extends
		DefaultHibernateDAOFacadeImpl<OrderAccountRef, Long> {

}
