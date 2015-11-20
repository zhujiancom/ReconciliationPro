package com.rci.dao.repository.inventory;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.inventory.Inventory;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;
@Repository
public class InventoryRepository extends
		DefaultHibernateDAOFacadeImpl<Inventory, Long> {

}
