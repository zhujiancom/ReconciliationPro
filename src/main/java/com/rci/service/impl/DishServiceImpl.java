package com.rci.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Dish;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.IDishService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.ui.swing.vos.DishVO;

@Service("DishService")
public class DishServiceImpl extends BaseServiceImpl<Dish, Long> implements
		IDishService {
	@Autowired
	private Mapper beanMapper;
	
	@Override
	public Dish findDishByNo(String no) {
		DetachedCriteria dc = DetachedCriteria.forClass(Dish.class);
		dc.add(Restrictions.eq("dishNo", no));
		Dish dish = baseDAO.queryUniqueByCriteria(dc);
		return dish;
	}
	
	

	@Override
	public List<DishVO> queryDishes(boolean isStocked) {
		DetachedCriteria dc = DetachedCriteria.forClass(Dish.class);
		dc.add(Restrictions.eq("stopFlag", YOrN.N)).add(Restrictions.eq("stockFlag", YOrN.getYN(isStocked)));
		List<Dish> dishes = baseDAO.queryListByCriteria(dc);
		List<DishVO> dishVos = new ArrayList<DishVO>();
		for(Dish dish:dishes){
			DishVO dishvo = beanMapper.map(dish, DishVO.class);
			dishVos.add(dishvo);
		}
		return dishVos;
	}



	@Override
	public DishVO queryDish(String no) {
		Dish dish = findDishByNo(no);
		return beanMapper.map(dish, DishVO.class);
	}



	@Override
	public void rwupdateDishInfo(Dish dish) {
		baseDAO.update(dish);
	}

}
