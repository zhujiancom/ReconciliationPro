package com.rci.service.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.bean.entity.TicketStatistic;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.ITicketStatisticService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.ExpressRateVO;
import com.rci.ui.swing.vos.TurnoverVO;

@Service("StatisticCenterFacade")
public class StatisticCenterFacadeImpl implements StatisticCenterFacade {
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService ticketService;
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Resource(name="ELESDStatisticService")
	private IELESDStatisticService elesdService;
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oarService;
	
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

	/* 
	 * @see com.rci.service.core.StatisticCenterFacade#getTurnoverList(java.util.Date, java.util.Date)
	 */
	@Override
	public List<TurnoverVO> getTurnoverList(Date sdate, Date edate) {
		List<TurnoverVO> resultList = new ArrayList<TurnoverVO>();
		TurnoverVO sum = new TurnoverVO("总计");
		if(edate == null){
			edate = DateUtil.getCurrentDate();
		}
		Date position = edate;
		while(position.after(sdate)){
			TurnoverVO vo = buildTurnoverVO(position);
			if(vo != null){
				resultList.add(vo);
				buildTurnoverSum(sum,vo);
			}
			position = DateUtil.addDays(position, -1);
		}
		
		if(DateUtil.isSameDay(position, sdate)){
			TurnoverVO vo = buildTurnoverVO(position);
			if(vo != null){
				resultList.add(vo);
				buildTurnoverSum(sum,vo);
			}
		}
		resultList.add(sum);
		return resultList;
	}
	
	private TurnoverVO buildTurnoverSum(TurnoverVO sum,TurnoverVO item){
		if(sum == null){
			sum = new TurnoverVO("总计");
		}
		sum.setCashMachineAmount(sum.getCashMachineAmount().add(item.getCashMachineAmount()));
		sum.setDpshAmount(sum.getDpshAmount().add(item.getDpshAmount()));
		sum.setDptgAmount(sum.getDptgAmount().add(item.getDptgAmount()));
		sum.setEleAmount(sum.getEleAmount().add(item.getEleAmount()));
		sum.setElebtAmount(sum.getElebtAmount().add(item.getElebtAmount()));
		sum.setElesdAmount(sum.getElesdAmount().add(item.getElesdAmount()));
		sum.setMtAmount(sum.getMtAmount().add(item.getMtAmount()));
		sum.setMtSuperAmount(sum.getMtSuperAmount().add(item.getMtSuperAmount()));
		sum.setOnlineFreeAmount(sum.getOnlineFreeAmount().add(item.getOnlineFreeAmount()));
		sum.setPosAmount(sum.getPosAmount().add(item.getPosAmount()));
		sum.setAliPayAmount(sum.getAliPayAmount().add(item.getAliPayAmount()));
		sum.setTsFreeAmount(sum.getTsFreeAmount().add(item.getTsFreeAmount()));
		sum.setTotalAmount(sum.getTotalAmount().add(item.getTotalAmount()));
		return sum;
	}
	
	private TurnoverVO buildTurnoverVO(Date position){
		List<AccountSumResult> results = oarService.querySumAmount(position);
		if(!CollectionUtils.isEmpty(results)){
			TurnoverVO vo = new TurnoverVO(DateUtil.date2Str(position));
			BigDecimal totalAmount = BigDecimal.ZERO;
			for(AccountSumResult sumResult:results){
				AccountCode accountNo = sumResult.getAccNo();
				BigDecimal amount = sumResult.getSumAmount();
				totalAmount = totalAmount.add(amount);
				switch(accountNo){
				case CASH_MACHINE:vo.setCashMachineAmount(amount);break;
				case POS:vo.setPosAmount(amount);break;
				case MT:vo.setMtAmount(amount);break;
				case DPTG:vo.setDptgAmount(amount);break;
				case DPSH:vo.setDpshAmount(amount);break;
				case ELE:vo.setEleAmount(amount);break;
				case FREE_ELE:vo.setElebtAmount(amount);break;
				case ALIPAY:vo.setAliPayAmount(amount);break;
				case MT_SUPER:vo.setMtSuperAmount(amount);break;
				case FREE:
					vo.setTsFreeAmount(amount);
					totalAmount = totalAmount.subtract(amount);
					break;
				case FREE_ONLINE:
					vo.setOnlineFreeAmount(amount);
					totalAmount = totalAmount.subtract(amount);
					break;
				default:
						break;
				}
			}
			BigDecimal elesdAmount = getSDAllowanceAmount(position);
			if(elesdAmount != null){
				vo.setElesdAmount(elesdAmount);
				totalAmount = totalAmount.add(elesdAmount);
			}
			vo.setTotalAmount(totalAmount);
			return vo;
		}
		return null;
	}
	
	
}
