package com.rci.dao.repository.inventory;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.inventory.SellOffWarning;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class SellOffWarningRepository extends
		DefaultHibernateDAOFacadeImpl<SellOffWarning, Long> {

}
