package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.IELESDStatisticService;
import com.rci.service.ISchemeService;
import com.rci.service.base.BaseServiceImpl;

@Service("ELESDStatisticService")
public class ELESDStatisticServiceImpl extends BaseServiceImpl<EleSDStatistic,Long> implements IELESDStatisticService {
	
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	
	@Override
	public void clearDataByDate(Date date) {
		EleSDStatistic elesd = querySDStatisticByDate(date);
		if(elesd != null){
			((IELESDStatisticService)AopContext.currentProxy()).rwDelete(elesd.getEid());
		}
	}
	
	@Override
	public boolean isExistData(Date date) {
		EleSDStatistic elesd = querySDStatisticByDate(date);
		if(elesd != null){
			return true;
		}
		return false;
	}

	@Override
	public void saveSDInfo(EleSDStatistic elesd) {
		List<Scheme> schemes = schemeService.getSchemes(Vendor.ELE, elesd.getSdDate());
		BigDecimal perAllowanceAmount = BigDecimal.ZERO;
		if(!CollectionUtils.isEmpty(schemes)){
			Scheme scheme = schemes.get(0);
			perAllowanceAmount= scheme.getPostPrice();
		}
//		BigDecimal perAllowanceAmount= PropertyUtils.getBigDecimalValue("ele.free.allowance") == null?BigDecimal.ZERO:PropertyUtils.getBigDecimalValue("ele.free.allowance");
		elesd.setPerAllowanceAmount(perAllowanceAmount);
		BigDecimal allowanceAmount = elesd.getSdCount().multiply(perAllowanceAmount); 
		elesd.setAllowanceAmount(allowanceAmount);
		((IELESDStatisticService)AopContext.currentProxy()).rwCreate(elesd);
	}



	@Override
	public EleSDStatistic querySDStatisticByDate(Date date) {
		DetachedCriteria dc = DetachedCriteria.forClass(EleSDStatistic.class);
		dc.add(Restrictions.eq("sdDate", date));
		return baseDAO.queryUniqueByCriteria(dc);
	}

}
