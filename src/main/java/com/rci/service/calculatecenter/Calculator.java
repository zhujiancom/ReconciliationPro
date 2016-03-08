package com.rci.service.calculatecenter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rci.bean.SchemeWrapper;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ServiceException;

public interface Calculator {
	/**
	 * 
	 * Describle(描述)： 得到团购商家到账的金额和商家补贴的金额
	 *
	 * 方法名称：doCalculateAmountForTG
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:13:51
	 *  
	 * @param scheme
	 * @param count
	 * @return
	 */
	BigDecimal[] doCalculateAmountForTG(Scheme scheme,Integer count);
	
	/**
	 * 
	 * Describle(描述)：计算团购使用的代金券， 适用平台 大众点评团购，美团团购，糯米团购
	 *
	 * 方法名称：getSchemesForTG
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:26:10
	 *  
	 * @param amount
	 * @param vendor
	 * @param suitFlag
	 * @param queryDate
	 * @return
	 */
	public Map<SchemeType,SchemeWrapper> getSchemesForTG(BigDecimal amount,Vendor vendor, Map<SchemeType, Integer> suitMap,Date queryDate);
	
	/**
	 * 
	 * Describle(描述)： 获取合适的方案
	 *
	 * 方法名称：getAppropriteSchemes
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:37:18
	 *  
	 * @param date
	 * @param vendor
	 * @return
	 */
	public List<Scheme> getAppropriteSchemes(Date date,Vendor vendor);
	
	/**
	 * 
	 * Describle(描述)：给定免单金额查找具体的方案信息
	 *
	 * 方法名称：getAppropriteScheme
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:44:16
	 *  
	 * @param date
	 * @param amount
	 * @param vendor
	 * @return
	 */
	public Scheme getAppropriteScheme(Date date,BigDecimal amount,Vendor vendor);
	
	/**
	 * 
	 * Describle(描述)：针对美团超券，闪惠，到店付满100减10的活动使用的算法
	 *
	 * 方法名称：doCalculateAmountForOnlinePay
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:34:19
	 *  
	 * @param payAmount
	 * @param queryDate
	 * @param vendor
	 * @return
	 * @throws ServiceException
	 */
	public BigDecimal[] doCalculateAmountForOnlinePay(BigDecimal payAmount,Date queryDate,Vendor vendor) throws ServiceException;
	
	/**
	 * 
	 * Describle(描述)：获取新用户享受的方案信息
	 *
	 * 方法名称：getSchemeForNewCustomer
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:37:53
	 *  
	 * @param freeAmount
	 * @param schemes
	 * @return
	 */
	public Scheme getSchemeForNewCustomer(BigDecimal freeAmount,List<Scheme> schemes);
	
	/**
	 * 
	 * Describle(描述)：记录代金券的使用情况
	 *
	 * 方法名称：createTicketRecord
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月1日 下午8:40:22
	 *  
	 * @param queryDate
	 * @param vendor
	 * @param schemes
	 */
	public void createTicketRecord(Date queryDate,Vendor vendor,Map<SchemeType,SchemeWrapper> schemes);
	
	/**
	 * 
	 * Describle(描述)：账户入账
	 *
	 * 方法名称：doEarningPostAmount
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月8日 下午5:38:54
	 *  
	 * @param account
	 * @param amount
	 */
	public void doEarningPostAmount(Account account,BigDecimal amount);
	
	/**
	 * 
	 * Describle(描述)：账户出账，回滚
	 *
	 * 方法名称：doExpensePostAmount
	 *
	 * 所在类名：Calculator
	 *
	 * Create Time:2016年3月8日 下午5:39:52
	 *  
	 * @param account
	 * @param amount
	 */
	public void doExpensePostAmount(Account account,BigDecimal amount);
}
