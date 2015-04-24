package com.rci.dao.repository.base;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.base.DictItem;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class DictItemRepository extends DefaultHibernateDAOFacadeImpl<DictItem, Long> {

}
