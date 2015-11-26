package com.rci.service.inventory;

import java.util.List;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.service.base.IBaseService;

public interface IInventoryDishRefService extends
		IBaseService<InventoryDishRef, Long> {
	List<InventoryDishRef> queryByInventoryNo(String ino);
	List<Dish> queryRelatedDishes(String ino);
	String queryRelatedDishNames(String ino);
	
	void deleteRelatedInfo(String ino);
	
	List<InventoryDishRef> queryByDishNo(String dishno);
}
