package com.rci.service;

import com.rci.bean.entity.DishType;

public interface IDishTypeService {
	void rwUpdateDishType(DishType type);
	
	DishType queryDishTypeByNo(String no);
}
