package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.SchemeType;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class SchemeTypeRepository extends
		DefaultHibernateDAOFacadeImpl<SchemeType, Long> {

}
