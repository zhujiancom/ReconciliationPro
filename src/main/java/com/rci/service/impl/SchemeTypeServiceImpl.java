package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.service.ISchemeTypeService;
import com.rci.service.base.BaseServiceImpl;

/**
 * 
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SchemeTypeServiceImpl
 *
 * 包名称：com.rci.service.impl
 *
 * Create Time: 2015年11月11日 下午2:38:26
 *
 */
@Service("SchemeTypeService")
public class SchemeTypeServiceImpl extends BaseServiceImpl<SchemeType, Long> implements
		ISchemeTypeService {

	@Override
	public SchemeType getSchemeTypeByDishno(String dishno) {
		DetachedCriteria sdc = DetachedCriteria.forClass(SchemeType.class);
		sdc.add(Restrictions.eq("dishNo", dishno));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

	@Override
	public SchemeType getSchemeType(BigDecimal amount,ActivityType activity) {
		DetachedCriteria sdc = DetachedCriteria.forClass(SchemeType.class);
		sdc.add(Restrictions.and(Restrictions.le("floorAmount", amount),Restrictions.gt("ceilAmount", amount)))
			.add(Restrictions.eq("activity", activity));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

	@Override
	public SchemeType getSchemeTypeByNo(String typeno) {
		DetachedCriteria sdc = DetachedCriteria.forClass(SchemeType.class);
		sdc.add(Restrictions.eq("typeNo", typeno));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

	@Override
	public List<SchemeType> getSchemeTypes(ActivityStatus status) {
		DetachedCriteria sdc = DetachedCriteria.forClass(SchemeType.class);
		sdc.add(Restrictions.eq("status", status));
		return baseDAO.queryListByCriteria(sdc);
	}
}
