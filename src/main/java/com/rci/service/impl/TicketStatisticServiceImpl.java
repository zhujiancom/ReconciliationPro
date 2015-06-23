package com.rci.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ITicketStatisticService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;

@Service("TicketStatisticService")
public class TicketStatisticServiceImpl extends BaseServiceImpl<TicketStatistic, Long> implements ITicketStatisticService{

	@Override
	public TicketStatistic queryTicketStatisticByDate(Date date, Vendor vendor) {
		DetachedCriteria dc = DetachedCriteria.forClass(TicketStatistic.class);
		dc.add(Restrictions.eq("date", date)).add(Restrictions.eq("vendor", vendor));
		return baseDAO.queryUniqueByCriteria(dc);
	}


	@Override
	public void deleteTicketStatistic(String time) {
		try {
			Date date = DateUtil.parseDate(time,"yyyyMMdd");
			DetachedCriteria dc = DetachedCriteria.forClass(TicketStatistic.class);
			dc.add(Restrictions.eq("date", date));
			List<TicketStatistic> tss = baseDAO.queryListByCriteria(dc);
			((ITicketStatisticService)AopContext.currentProxy()).rwDelete(tss.toArray(new TicketStatistic[0]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
