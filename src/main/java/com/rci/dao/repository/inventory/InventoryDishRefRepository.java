package com.rci.dao.repository.inventory;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class InventoryDishRefRepository extends
		DefaultHibernateDAOFacadeImpl<InventoryDishRef, Long> {

}
