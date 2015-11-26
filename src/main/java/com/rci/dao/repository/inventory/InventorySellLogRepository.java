package com.rci.dao.repository.inventory;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.inventory.InventorySellLog;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class InventorySellLogRepository extends
		DefaultHibernateDAOFacadeImpl<InventorySellLog, Long> {

}
