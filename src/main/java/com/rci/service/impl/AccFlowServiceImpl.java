package com.rci.service.impl;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.account.AccFlow;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.service.IAccFlowService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;

@Service("AccFlowService")
public class AccFlowServiceImpl extends BaseServiceImpl<AccFlow, Long> implements
		IAccFlowService {
	@Override
	public void rwDeleteFlowInfo(String time,DataGenerateType generateType) {
		try {
			String formatTime = DateUtil.date2Str(DateUtil.parseDate(time,"yyyyMMdd"));
			String hql = "delete AccFlow flow where flow.time='"+formatTime+"',generateType="+generateType;
			baseDAO.executeHQL(hql);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public AccFlow queryUniqueAccFlow(Long accId,
			DataGenerateType generateType, Date date) {
		DetachedCriteria dc = DetachedCriteria.forClass(AccFlow.class);
		dc.add(Restrictions.eq("accId", accId)).add(Restrictions.eq("generateType", generateType)).add(Restrictions.eq("time", date));
		return baseDAO.queryUniqueByCriteria(dc);
	}

}
