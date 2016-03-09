package com.rci.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.service.base.IBaseService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;

public interface IOrderAccountRefService extends IBaseService<OrderAccountRef, Long>{
	List<OrderAccountRef> getOARef(String billno);
	
	/**
	 * 
	 * Describle(描述)：计算订单实际入账金额，去除优惠后所得
	 *
	 * 方法名称：getPostAmountForOrder
	 *
	 * 所在类名：IOrderAccountRefService
	 *
	 * Create Time:2015年12月18日 下午2:07:02
	 *  
	 * @param billno
	 * @return
	 */
	BigDecimal getPostAmountForOrder(String billno);
	
	void deleteOar(Date date);
	
	void doDeleteOar(Date date);
	
	List<AccountSumResult> querySumAmount(Date postTime);
	
	List<AccountSumResult> queryDateRangeSumAmount(Date sdate,Date edate);
	
	Long getValidOrderCount(Date postTime,String account);
	
	BigDecimal querySumAmount(String account,Date postTime,OrderFramework framework);
	
	/**
	 * 
	 * Describle(描述)：查询每日各账户入账金额
	 *
	 * 方法名称：querySumAmountfromView
	 *
	 * 所在类名：IOrderAccountRefService
	 *
	 * Create Time:2016年3月8日 下午4:20:40
	 *  
	 * @param postTime
	 * @return
	 */
	List<AccountSumResult> querySumAmountfromView(Date postTime);
	
	void doRollbackAccount(Date postTime);
}
