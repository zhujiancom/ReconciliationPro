package com.rci.service.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rci.enums.BusinessEnums.Vendor;
import com.rci.ui.swing.vos.DishStatisticVO;
import com.rci.ui.swing.vos.ExpressRateVO;
import com.rci.ui.swing.vos.TurnoverStatisticVO;
import com.rci.ui.swing.vos.TurnoverVO;

public interface StatisticCenterFacade {
	/**
	 * 
	 * Describle(描述)： 统计不同平台当日代金券使用量
	 *
	 * 方法名称：getTicketStatistic
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年6月19日 下午4:50:20
	 *  
	 * @param date
	 * @param vendor
	 * @return
	 */
	String getTicketStatistic(Date date,Vendor vendor);
	
	/**
	 * 
	 * Describle(描述)：统计当日外送单比率
	 *
	 * 方法名称：getExpressRate
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年6月19日 下午4:57:02
	 *  
	 * @param time
	 * @return
	 */
	BigDecimal getExpressRate(String time);
	
	/**
	 * 
	 * Describle(描述)：获取刷单获取的补贴金额
	 *
	 * 方法名称：getSDAllowanceAmount
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年7月9日 下午3:00:49
	 *  
	 * @param date
	 * @return
	 */
	BigDecimal getSDAllowanceAmount(Date date);
	
	/**
	 * 
	 * Describle(描述)：搜索当月每天的外送率数据
	 *
	 * 方法名称：getExpressRateList
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年7月31日 下午1:53:17
	 *  
	 * @return
	 */
	List<ExpressRateVO> getExpressRateList();
	
	/**
	 * 
	 * Describle(描述)：搜索指定日期范围的每天的外送率数据
	 *
	 * 方法名称：getExpressRateList
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年7月31日 下午1:54:38
	 *  
	 * @param sdate
	 * @param edate
	 * @return
	 */
	List<ExpressRateVO> getExpressRateList(Date sdate,Date edate);
	
	/**
	 * 
	 *
	 * Describle(描述)：统计指定日期范围的每日营业数据
	 *
	 * 方法名称：getTurnoverList
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年8月1日 上午1:13:10
	 *  
	 * @param sdate
	 * @param edate
	 * @return
	 */
	List<TurnoverVO> getTurnoverList(Date sdate,Date edate);
	
	/**
	 * 
	 * Describle(描述)：统计各账户的收入的总额
	 *
	 * 方法名称：getTrunoverSum
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年8月3日 上午10:47:45
	 *  
	 * @param itmes
	 * @return
	 */
//	TurnoverVO getTrunoverSum(List<TurnoverVO> itmes);
	
	List<DishStatisticVO> getDishSaleStatisticInfo(Date sdate,Date edate);
	
	/**
	 * 
	 * Describle(描述)：获取菜品总销售量
	 *
	 * 方法名称：getSaleroom
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年12月8日 下午4:57:49
	 *  
	 * @param sdate
	 * @param edate
	 * @return
	 */
	BigDecimal getSaleroom(Date sdate,Date edate);
	
	/**
	 * 
	 * Describle(描述)： 营业额统计
	 *
	 * 方法名称：getTurnoverStatisticInfo
	 *
	 * 所在类名：StatisticCenterFacade
	 *
	 * Create Time:2015年12月10日 上午9:26:31
	 *  
	 * @param sdate
	 * @param edate
	 * @return
	 */
	public List<TurnoverStatisticVO> getTurnoverStatisticInfo(Date sdate,Date edate);
}
