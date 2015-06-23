package com.rci.service.impl;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Dish;
import com.rci.service.IDishService;
import com.rci.service.IDishTypeService;
import com.rci.service.base.BaseServiceImpl;

@Service("DishService")
public class DishServiceImpl extends BaseServiceImpl<Dish, Long> implements
		IDishService {
	@Resource(name="DishTypeService")
	private IDishTypeService dishTypeService;
	
	@Autowired
	private Mapper beanMapper;
	
	@Override
	public Dish findDishByNo(String no) {
		DetachedCriteria dc = DetachedCriteria.forClass(Dish.class);
		dc.add(Restrictions.eq("dishNo", no));
		Dish dish = baseDAO.queryUniqueByCriteria(dc);
		return dish;
	}
}
