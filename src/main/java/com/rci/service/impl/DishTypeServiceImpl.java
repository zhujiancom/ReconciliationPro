package com.rci.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DishType;
import com.rci.service.IDishTypeService;
import com.rci.service.base.BaseServiceImpl;

@Service("DishTypeService")
public class DishTypeServiceImpl extends BaseServiceImpl<DishType, Long> implements IDishTypeService {
	@Override
	public DishType queryDishTypeByNo(String no) {
		DetachedCriteria dc = DetachedCriteria.forClass(DishType.class);
		dc.add(Restrictions.eq("dtNo", no));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void deleteAll() {
		List<DishType> dishTypes = getAll();
		for(DishType type:dishTypes){
			((IDishTypeService)AopContext.currentProxy()).rwDelete(type.getDtid());
		}
	}
	
	@Override
	public void delete(DishType type){
		((IDishTypeService)AopContext.currentProxy()).rwDelete(type.getDtid());
	}
}
