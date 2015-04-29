package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DishType;
import com.rci.service.IDishTypeService;
import com.rci.service.base.BaseService;

@Service("DishTypeService")
public class DishTypeServiceImpl extends BaseService<DishType, Long> implements
		IDishTypeService {

	@Override
	public void rwUpdateDishType(DishType type) {
		super.rwUpdate(type);
	}

	@Override
	public DishType queryDishTypeByNo(String no) {
		DetachedCriteria dc = DetachedCriteria.forClass(DishType.class);
		dc.add(Restrictions.eq("dtNo", no));
		return baseDAO.queryUniqueByCriteria(dc);
	}

}
