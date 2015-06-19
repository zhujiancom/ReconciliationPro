package com.rci.service.core;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ITicketStatisticService;

@Service("StatisticCenterFacade")
public class StatisticCenterFacadeImpl implements StatisticCenterFacade {
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService ticketService;
	
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
//		SELECT COUNT(1) FROM bus_tb_order o WHERE o.`day`='20150618'
//
//				SELECT COUNT(1) FROM bus_tb_order o LEFT JOIN bus_tb_table t ON o.`table_no`=t.`table_no`
//				WHERE o.`day`='20150618'
//				AND t.`table_type`='03'
		
		return null;
	}

}
