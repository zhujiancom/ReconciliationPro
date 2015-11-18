package com.rci.service;

import java.util.List;

import com.rci.bean.entity.Dish;
import com.rci.service.base.IBaseService;
import com.rci.ui.swing.vos.DishVO;

public interface IDishService extends IBaseService<Dish, Long>{
	public Dish findDishByNo(String no);
	
	public DishVO queryDish(String no);
	
	public List<DishVO> queryDishes(boolean isStocked);
	
	public void rwupdateDishInfo(Dish dish);
	
	public List<Dish> queryDishesBySeries(String seriesName);
	
	public List<Dish> queryDishesByTypeno(String typeno);
}
