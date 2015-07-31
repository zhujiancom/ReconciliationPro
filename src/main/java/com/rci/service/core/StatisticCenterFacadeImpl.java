package com.rci.service.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IOrderService;
import com.rci.service.ITicketStatisticService;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.ExpressRateVO;

@Service("StatisticCenterFacade")
public class StatisticCenterFacadeImpl implements StatisticCenterFacade {
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService ticketService;
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Resource(name="ELESDStatisticService")
	private IELESDStatisticService elesdService;
	
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
		if(allCount== null || allCount.equals(BigDecimal.ZERO)){
			return BigDecimal.ZERO;
		}
		BigDecimal expressCount = orderService.getExpressOrderCountByDay(time);
		BigDecimal rate = expressCount.divide(allCount, 4, BigDecimal.ROUND_HALF_EVEN).movePointRight(2);
		return rate;
	}

	@Override
	public BigDecimal getSDAllowanceAmount(Date date) {
		EleSDStatistic elesd = elesdService.querySDStatisticByDate(date);
		if(elesd != null){
			return elesd.getAllowanceAmount();
		}
		return null;
	}

	@Override
	public List<ExpressRateVO> getExpressRateList() {
		List<ExpressRateVO> resultList = new ArrayList<ExpressRateVO>();
		Date today = DateUtil.getCurrentDate();
		Date positionDay = DateUtil.getFirstDayOfMonth(today);
		while(today.after(positionDay)){
			ExpressRateVO ratevo = buildExpressRateVO(today);
			resultList.add(ratevo);
			today = DateUtil.addDays(today, -1);
		}
		if(DateUtil.isSameDay(today, positionDay)){
			ExpressRateVO ratevo = buildExpressRateVO(today);
			resultList.add(ratevo);
		}
		return resultList;
	}
	
	private ExpressRateVO buildExpressRateVO(Date date){
		String time = DateUtil.date2Str(date, "yyyyMMdd");
		BigDecimal orderCount = orderService.getOrderCountByDay(time);
		ExpressRateVO ratevo = new ExpressRateVO();
		if(orderCount== null || orderCount.equals(BigDecimal.ZERO)){
			ratevo = new ExpressRateVO(date,0,0);
			ratevo.setExpressRate(BigDecimal.ZERO);
		}else{
			BigDecimal expressCount = orderService.getExpressOrderCountByDay(time);
			BigDecimal rate = expressCount.divide(orderCount, 4, BigDecimal.ROUND_HALF_EVEN).movePointRight(2);
			ratevo = new ExpressRateVO(date,expressCount.intValue(),orderCount.intValue());
			ratevo.setExpressRate(rate);
		}
		return ratevo;
	}

	@Override
	public List<ExpressRateVO> getExpressRateList(Date sdate, Date edate) {
		List<ExpressRateVO> resultList = new ArrayList<ExpressRateVO>();
		if(edate == null){
			edate = DateUtil.getCurrentDate();
		}
		while(edate.after(sdate)){
			ExpressRateVO ratevo = buildExpressRateVO(edate);
			resultList.add(ratevo);
			edate = DateUtil.addDays(edate, -1);
		}
		if(DateUtil.isSameDay(edate, sdate)){
			ExpressRateVO ratevo = buildExpressRateVO(edate);
			resultList.add(ratevo);
		}
		return resultList;
	}
	
	
}
