package com.rci.service.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.IOrderService;
import com.rci.service.ITicketStatisticService;

@Service("StatisticCenterFacade")
public class StatisticCenterFacadeImpl implements StatisticCenterFacade {
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService ticketService;
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Override
	public String getTicketStatistic(Date date, Vendor vendor) {
		TicketStatistic ts = ticketService.queryTicketStatisticByDate(date, vendor);
		if(ts != null){
			return ts.getName();
		}
		return "";
	}

	@Override
	public BigDecimal getExpressRate(String time) {
		BigDecimal allCount = orderService.getOrderCountByDay(time);
		BigDecimal expressCount = orderService.getExpressOrderCountByDay(time);
		BigDecimal rate = expressCount.divide(allCount, 4, BigDecimal.ROUND_HALF_EVEN).movePointRight(2);
		return rate;
	}
}
