/**
 * 
 */
package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeDTO;
import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Scheme;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ISchemeService;
import com.rci.service.base.BaseServiceImpl;

/**
 * @author zj
 *	
 * 项目名称：BillSystem
 *
 * 类名称：SchemeServiceImpl
 *
 * 包名称：com.bill.sys.service
 *
 * Create Time: 2015年4月22日 下午11:15:36
 *
 * remark (备注):
 *
 */
@Service("SchemeService")
public class SchemeServiceImpl extends BaseServiceImpl<Scheme, Long> implements
		ISchemeService {
	private static final Log logger = LogFactory.getLog(SchemeServiceImpl.class);
	@Autowired
	private Mapper beanMapper;

	@Override
	public Scheme getScheme(Vendor vendor, BigDecimal freePrice, Date date) {
		DetachedCriteria dc = DetachedCriteria.forClass(Scheme.class);
		Scheme scheme = null;
		try{
			dc.add(Restrictions.eq("vendor", vendor)).add(Restrictions.eq("price", freePrice))
			.add(Restrictions.eq("activityStatus", ActivityStatus.ACTIVE))
			.add(Restrictions.and(Restrictions.ge("endDate", date),Restrictions.le("startDate", date)));
			scheme = baseDAO.queryUniqueByCriteria(dc); 
		}catch(Exception e){
			List<Scheme> schemes = baseDAO.queryListByCriteria(dc); 
			scheme = schemes.get(0);
			logger.warn("--- "+scheme.getName()+" --- 活动重复"+schemes.size()+"条，默认取第一条");
		}
		return scheme; 
	}

	/* 
	 * @see com.rci.service.ISchemeService#getScheme(com.rci.enums.BusinessEnums.SchemeType, com.rci.enums.BusinessEnums.Vendor)
	 */
	@Override
	public Scheme getScheme(SchemeType type, Vendor vendor,Date date) {
		DetachedCriteria sdc = DetachedCriteria.forClass(Scheme.class);
		sdc.add(Restrictions.eq("type", type)).add(Restrictions.eq("vendor", vendor)).add(Restrictions.and(Restrictions.ge("endDate", date), Restrictions.le("startDate", date)));
		Scheme scheme = baseDAO.queryUniqueByCriteria(sdc);
		return scheme;
	}

	/* 
	 * @see com.rci.service.ISchemeService#checkStatus(java.util.Date)
	 */
	@Override
	public void checkStatus(Date deadLine) {
		List<Scheme> schemes = getAll();
		for(Scheme scheme:schemes){
			if(scheme.getEndDate().before(deadLine) && ActivityStatus.ACTIVE.equals(scheme.getActivityStatus())){
				scheme.setActivityStatus(ActivityStatus.INACTIVE);
				((ISchemeService)AopContext.currentProxy()).rwUpdate(scheme);
			}
		}
	}

	/* 
	 * @see com.rci.service.ISchemeService#getSchemes(com.rci.bean.dto.SchemeQueryDTO)
	 */
	@Override
	public List<Scheme> getSchemes(SchemeQueryDTO queryDTO) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Scheme.class);
		sdc.add(SafeRestrictions.eq("vendor", queryDTO.getVendor()))
		.add(SafeRestrictions.eq("activityStatus", queryDTO.getStatus()))
		.add(SafeRestrictions.ands(SafeRestrictions.great("endDate", queryDTO.getEndDate()),SafeRestrictions.less("startDate", queryDTO.getStartDate())))
		.add(SafeRestrictions.great("price", queryDTO.getPrice()));
		sdc.addOrder(Order.asc("vendor"));
		return baseDAO.queryListByCriteria(sdc);
	}

	@Override
	public List<SchemeDTO> getAllSchemes() {
		List<SchemeDTO> schemeDTOs = new ArrayList<SchemeDTO>();
		List<Scheme> schemes = getAll();
		if(!CollectionUtils.isEmpty(schemes)){
			for(Scheme scheme:schemes){
				schemeDTOs.add(beanMapper.map(scheme, SchemeDTO.class));
			}
		}
		return schemeDTOs;
	}

}
