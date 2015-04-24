package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.Scheme;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class SchemeRepository extends DefaultHibernateDAOFacadeImpl<Scheme, Long>{

}
