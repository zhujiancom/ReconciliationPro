package com.rci.service;

import com.rci.bean.entity.Dish;
import com.rci.service.base.IBaseService;

public interface IDishService extends IBaseService<Dish, Long>{
	public Dish findDishByNo(String no);
}
