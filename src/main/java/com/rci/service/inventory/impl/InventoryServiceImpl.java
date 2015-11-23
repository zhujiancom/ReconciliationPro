package com.rci.service.inventory.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.inventory.Inventory;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IInventoryService;

@Service("InventoryService")
public class InventoryServiceImpl extends BaseServiceImpl<Inventory, Long>
		implements IInventoryService {

	@Override
	public List<Inventory> queryValidInventories() {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Inventory.class);
		sdc.add(SafeRestrictions.eq("status", YOrN.Y));
		return baseDAO.queryListByCriteria(sdc);
	}
}
