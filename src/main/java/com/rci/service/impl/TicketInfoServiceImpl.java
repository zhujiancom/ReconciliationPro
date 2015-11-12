package com.rci.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.TicketInfo;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ITicketInfoService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;

@Service("TicketInfoService")
public class TicketInfoServiceImpl extends BaseServiceImpl<TicketInfo, Long>
		implements ITicketInfoService {
	@Override
	public List<TicketInfo> queryTicketStatisticByDate(Date date, Vendor vendor) {
		DetachedCriteria dc = DetachedCriteria.forClass(TicketInfo.class);
		dc.add(Restrictions.eq("date", date)).add(Restrictions.eq("vendor", vendor));
		return baseDAO.queryListByCriteria(dc);
	}

	@Override
	public TicketInfo queryTicketStatisticByDateAndType(SchemeType stype,
			Date date, Vendor vendor) {
		DetachedCriteria dc = DetachedCriteria.forClass(TicketInfo.class);
		dc.createAlias("schemeType", "schemeType");
		dc.add(Restrictions.eq("date", date)).add(Restrictions.eq("vendor", vendor)).add(Restrictions.eq("schemeType.typeNo", stype.getTypeNo()));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void deleteTicketStatistic(String time) {
		try {
			Date date = DateUtil.parseDate(time,"yyyyMMdd");
			DetachedCriteria dc = DetachedCriteria.forClass(TicketInfo.class);
			dc.add(Restrictions.eq("date", date));
			List<TicketInfo> tss = baseDAO.queryListByCriteria(dc);
			((ITicketInfoService)AopContext.currentProxy()).rwDelete(tss.toArray(new TicketInfo[0]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
