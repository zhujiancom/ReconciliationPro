package com.rci.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DishType;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.service.IDishTypeService;
import com.rci.service.base.BaseServiceImpl;

@Service("DishTypeService")
public class DishTypeServiceImpl extends BaseServiceImpl<DishType, Long> implements IDishTypeService {
	@Override
	public DishType queryDishTypeByNo(String no) {
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(DishType.class);
			dc.add(Restrictions.eq("dtNo", no));
			return baseDAO.queryUniqueByCriteria(dc);
		}catch(Exception e){
			logger.debug("query dish error: dishTypeno = "+no);
			ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, e);
		}
		return null;
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

	@Override
	public List<DishType> queryDishTypesBySeriesno(String seriesno) {
		DetachedCriteria dc = DetachedCriteria.forClass(DishType.class);
		dc.add(Restrictions.eq("seriesno", seriesno));
		return baseDAO.queryListByCriteria(dc);
	}
}
