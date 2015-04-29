package com.rci.dao.repository.base;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.base.DictGroup;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class DictGroupRepository extends
		DefaultHibernateDAOFacadeImpl<DictGroup, Long> {

}
