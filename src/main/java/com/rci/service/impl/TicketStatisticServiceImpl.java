package com.rci.service.impl;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ITicketStatisticService;
import com.rci.service.base.BaseService;
import com.rci.tools.DateUtil;

@Service("TicketStatisticService")
public class TicketStatisticServiceImpl extends BaseService<TicketStatistic, Long> implements ITicketStatisticService{

	@Override
	public TicketStatistic queryTicketStatisticByDate(Date date, Vendor vendor) {
		DetachedCriteria dc = DetachedCriteria.forClass(TicketStatistic.class);
		dc.add(Restrictions.eq("date", date)).add(Restrictions.eq("vendor", vendor));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void rwCreateTicketStatistic(TicketStatistic ts) {
		super.rwCreate(ts);
	}

	@Override
	public void rwUpdateTicketStatistic(TicketStatistic ts) {
		super.rwUpdate(ts);
	}

	@Override
	public void rwDeleteTicketStatistic(String time) {
		try {
			String date = DateUtil.date2Str(DateUtil.parseDate(time,"yyyyMMdd"));
			String hql = "delete TicketStatistic ts where ts.date='"+date+"'";
			baseDAO.executeHQL(hql);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
