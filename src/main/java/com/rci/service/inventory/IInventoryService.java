package com.rci.service.inventory;

import java.util.List;

import com.rci.bean.entity.inventory.Inventory;
import com.rci.service.base.IBaseService;

public interface IInventoryService extends IBaseService<Inventory, Long> {
	List<Inventory> queryValidInventories();
	
	Inventory queryInventoryByNo(String ino);
	
	List<Inventory> queryInventoryByName(String iname);
}
