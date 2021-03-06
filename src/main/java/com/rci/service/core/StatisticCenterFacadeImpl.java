package com.rci.service.core;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.bean.entity.StatisticRecord;
import com.rci.bean.entity.TicketInfo;
import com.rci.bean.entity.account.Account;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.metadata.NativeSQLBuilder;
import com.rci.service.IAccountService;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.IStatisticRecordService;
import com.rci.service.ITicketInfoService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.CostStatisticVO;
import com.rci.ui.swing.vos.DishStatisticVO;
import com.rci.ui.swing.vos.ExpressRateVO;
import com.rci.ui.swing.vos.TurnoverStatisticVO;
import com.rci.ui.swing.vos.TurnoverVO;

@Service("StatisticCenterFacade")
public class StatisticCenterFacadeImpl implements StatisticCenterFacade {
	@Resource(name="TicketInfoService")
	private ITicketInfoService ticketService;
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Resource(name="ELESDStatisticService")
	private IELESDStatisticService elesdService;
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oarService;
	@Resource(name="StatisticRecordService")
	private IStatisticRecordService srService;
	@Resource(name="AccountService")
	private IAccountService accService;
	
	@Autowired
	private Mapper beanMapper;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
//	@Resource(name="jdbcTemplateForDerby")
//	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getTicketStatistic(Date date, Vendor vendor) {
//		List<TicketStatistic> ts = ticketService.queryTicketStatisticByDate(date, vendor);
//		if(ts != null){
//			return ts.getName();
//		}
		StringBuffer sb = new StringBuffer();
		List<TicketInfo> ticketInfos = ticketService.queryTicketStatisticByDate(date, vendor);
		if(!CollectionUtils.isEmpty(ticketInfos)){
			for(TicketInfo info:ticketInfos){
				sb.append(",").append(info.getName()).append(":").append(info.getValifyCount()).append(" 张");
			}
			return sb.substring(1);
		}
		return sb.toString();
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
//		TurnoverVO sum = new TurnoverVO("总计");
		if(edate == null){
			edate = DateUtil.getCurrentDate();
		}
		Date position = edate;
		while(position.after(sdate)){
			List<AccountSumResult> results = oarService.querySumAmount(position);
			TurnoverVO vo = buildTurnoverVO(position,results);
			if(vo != null){
				resultList.add(vo);
//				buildTurnoverSum(sum,vo);
			}
			position = DateUtil.addDays(position, -1);
		}
		
		if(DateUtil.isSameDay(position, sdate)){
			List<AccountSumResult> results = oarService.querySumAmount(position);
			TurnoverVO vo = buildTurnoverVO(position,results);
			if(vo != null){
				resultList.add(vo);
//				buildTurnoverSum(sum,vo);
			}
		}
//		resultList.add(sum);
		return resultList;
	}
	
	@Override
	public List<TurnoverStatisticVO> getTurnoverStatisticInfo(Date sdate,Date edate){
		List<TurnoverStatisticVO> results = jdbcTemplate.query(NativeSQLBuilder.TURNOVER_STATISTIC,new Object[]{sdate,edate},new RowMapper<TurnoverStatisticVO>(){

			@Override
			public TurnoverStatisticVO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				TurnoverStatisticVO result = new TurnoverStatisticVO();
				result.setAccno(rs.getString("accno"));
				result.setParentAccountno(rs.getString("main_account"));
//				String symbol = rs.getString("symbol");
				result.setAmount(rs.getBigDecimal("amount"));
//				if("N".equals(symbol)){
//					result.setAmount(rs.getBigDecimal("amount").negate());					
//				}
//				if(rs.getString("framework") != null){
//					result.setFramework(OrderFramework.valueOf(rs.getString("framework")));
//				}
				return result;
			}
			
		});
		return mergeTurnoverStatisticList(results);
	}
	
	private List<TurnoverStatisticVO> mergeTurnoverStatisticList(List<TurnoverStatisticVO> list){
		Map<String,TurnoverStatisticVO> datastore = new HashMap<String,TurnoverStatisticVO>();
		List<TurnoverStatisticVO> results = new ArrayList<TurnoverStatisticVO>();
		for(TurnoverStatisticVO turnover:list){
			String key = "";
			if(turnover.getAmount().compareTo(BigDecimal.ZERO) < 0){
				key = turnover.getAccno();
			}else{
				key = turnover.getParentAccountno();
			}
			if(datastore.containsKey(key)){
				TurnoverStatisticVO oldObj = datastore.get(key);
				oldObj.addAmount(turnover.getAmount());
			}else{
				Account account = accService.getAccount(key);
				turnover.setAccountName(account.getAccName());
				datastore.put(key, turnover);
			}
		}
		for(Iterator<Entry<String,TurnoverStatisticVO>> it = datastore.entrySet().iterator();it.hasNext();){
			Entry<String,TurnoverStatisticVO> entry = it.next();
			results.add(entry.getValue());
		}
		return results;
	}
	
	private TurnoverVO buildTurnoverVO(Date position,List<AccountSumResult> results){
		if(!CollectionUtils.isEmpty(results)){
			TurnoverVO vo = new TurnoverVO();
			if(position != null){
				vo.setDisplayTitle(DateUtil.date2Str(position));
			}
			BigDecimal totalAmount = BigDecimal.ZERO;
			for(AccountSumResult sumResult:results){
				String accountNo = sumResult.getAccNo();
				BigDecimal amount = sumResult.getSumAmount();
				totalAmount = totalAmount.add(amount);
				
				//现金入账
				if(BusinessConstant.AccountCode_CASH_MACHINE.equals(accountNo)){
					vo.setCashMachineAmount(amount);
				}
				//POS机入账
				if(BusinessConstant.AccountCode_POS.equals(accountNo)){
					vo.setPosAmount(amount);
				}
				//大众点评团购券入账
				if(BusinessConstant.AccountCode_DPTG.equals(accountNo)){
					vo.setDptgAmount(amount);
				}
				//大众点评闪惠入账
				if(BusinessConstant.AccountCode_DPSH.equals(accountNo)){
					vo.setDpshAmount(amount);
				}
				//饿了么入账
				if(BusinessConstant.AccountCode_ELE.equals(accountNo)){
					vo.setEleAmount(amount);
				}
				//支付宝入账
				if(BusinessConstant.AccountCode_ALIPAY.equals(accountNo)){
					vo.setAliPayAmount(amount);
				}
				//美团团购券入账
				if(BusinessConstant.AccountCode_MT.equals(accountNo)){
					vo.setMtAmount(amount);
				}
				//美团超券券入账
				if(BusinessConstant.AccountCode_MT_SUPER.equals(accountNo)){
					vo.setMtSuperAmount(amount);
				}
				//美团外卖入账
				if(BusinessConstant.AccountCode_MTWM.equals(accountNo)){
					vo.setMtwmAmount(amount);
				}
				//百度外卖入账
				if(BusinessConstant.AccountCode_BDWM.equals(accountNo)){
					vo.setBdwmAmount(amount);
				}
				//百度糯米团购券入账
				if(BusinessConstant.AccountCode_BDNM.equals(accountNo)){
					vo.setBdnmAmount(amount);
				}
				//百度糯米到店付入账
				if(BusinessConstant.AccountCode_BDNM_DDF.equals(accountNo)){
					vo.setBdnmddfAmount(amount);
				}
				//在线优惠（免单）金额
				if(BusinessConstant.AccountCode_FREE_ONLINE.equals(accountNo)){
					vo.setOnlineFreeAmount(amount);
					totalAmount = totalAmount.subtract(amount);
				}
				//线下优惠金额
				if(BusinessConstant.AccountCode_FREE_OFFLINE.equals(accountNo)){
					vo.setTsFreeAmount(amount);
					totalAmount = totalAmount.subtract(amount);
				}
			}
			/**
			 * 获取饿了么刷单补贴金额
			 */
			BigDecimal elesdAmount = getSDAllowanceAmount(position);
			if(elesdAmount != null){
				vo.setElesdAmount(elesdAmount);
				totalAmount = totalAmount.add(elesdAmount);
			}
			/**
			 * 获取饿了么餐厅补贴金额
			 */
			BigDecimal eleOnlineFreeAmount = oarService.querySumAmount(BusinessConstant.AccountCode_FREE_ONLINE, position, OrderFramework.ELE);
			vo.setEleOnlineFreeAmount(eleOnlineFreeAmount);
			
			/**
			 * 获取美团外卖补贴金额
			 */
			BigDecimal mtwmOnlineFreeAmount = oarService.querySumAmount(BusinessConstant.AccountCode_FREE_ONLINE, position, OrderFramework.MTWM);
			vo.setMtwmOnlineFreeAmount(mtwmOnlineFreeAmount);
			
			vo.setTotalAmount(totalAmount);
			return vo;
		}
		return null;
	}

	@Override
	public List<DishStatisticVO> getDishSaleStatisticInfo(Date sdate, Date edate) {
		return jdbcTemplate.query(NativeSQLBuilder.DISHSALE_STATISTIC, new Object[]{sdate,edate}, new RowMapper<DishStatisticVO>(){

			@Override
			public DishStatisticVO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DishStatisticVO vo = new DishStatisticVO();
				vo.setDishNo(rs.getString("dishno"));
				vo.setDishName(rs.getString("dishname"));
				vo.setDishPrice(rs.getBigDecimal("dishprice"));
				vo.setSaleAmount(rs.getBigDecimal("amount"));
				return vo;
			}
			
		});
	}

	@Override
	public List<CostStatisticVO> getCostStatisticList(Date sdate, Date edate) {
		List<StatisticRecord> records = srService.queryRecords(sdate, edate);
		List<CostStatisticVO> results = new ArrayList<CostStatisticVO>();
		if(!CollectionUtils.isEmpty(records)){
			for(StatisticRecord record:records){
				CostStatisticVO costvo = beanMapper.map(record, CostStatisticVO.class);
				results.add(costvo);
			}
		}
		return results;
	}
	
	
}
