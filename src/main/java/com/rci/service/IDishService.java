package com.rci.service;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;

public interface IDishService {
	public void rwSaveDish(Dish dish);
	
	public Dish findDishByNo(String no);
	
	public void rwSaveDishes(Dish[] dishes);
	
	public DishType findDishTypeByTypeNo(String typeno);
}
