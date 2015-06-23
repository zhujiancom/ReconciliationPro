package com.rci.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Paymode;
import com.rci.service.IPayModeService;
import com.rci.service.base.BaseServiceImpl;

@Service("PayModeService")
public class PayModeServiceImpl extends BaseServiceImpl<Paymode, Long> implements IPayModeService {
	private transient Log logger = LogFactory.getLog(PayModeServiceImpl.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(PayModeServiceImpl.class);
		} else {
			return logger;
		}
	}

	@Override
	public Paymode getPayModeByNo(String pmno) {
		DetachedCriteria dc = DetachedCriteria.forClass(Paymode.class);
		dc.add(Restrictions.eq("pmNo", pmno));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void deleteAll() {
		List<Paymode> paymodes = super.getAll();
		((IPayModeService)AopContext.currentProxy()).rwDelete(paymodes.toArray(new Paymode[0]));
	}

}
