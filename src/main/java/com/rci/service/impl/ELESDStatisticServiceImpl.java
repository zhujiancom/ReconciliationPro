package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

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
		if(elesd.getPayAmount().equals(BigDecimal.ZERO) || elesd.getSdCount().equals(BigDecimal.ZERO) || isExistData(elesd.getSdDate())){
			return;
		}
		BigDecimal perAllowanceAmount = BigDecimal.ZERO;
		Scheme scheme = schemeService.getScheme(Vendor.ELE, elesd.getPerAllowanceAmount(), elesd.getSdDate());
		if(scheme != null){
			perAllowanceAmount = scheme.getPostPrice();
		}
//		BigDecimal perAllowanceAmount= PropertyUtils.getBigDecimalValue("ele.free.allowance") == null?BigDecimal.ZERO:PropertyUtils.getBigDecimalValue("ele.free.allowance");
		BigDecimal allowanceAmount = elesd.getSdCount().multiply(perAllowanceAmount); 
		elesd.setAllowanceAmount(allowanceAmount);
		elesd.setPerAllowanceAmount(perAllowanceAmount);
		((IELESDStatisticService)AopContext.currentProxy()).rwCreate(elesd);
	}



	@Override
	public EleSDStatistic querySDStatisticByDate(Date date) {
		DetachedCriteria dc = DetachedCriteria.forClass(EleSDStatistic.class);
		dc.add(Restrictions.eq("sdDate", date));
		return baseDAO.queryUniqueByCriteria(dc);
	}

}
