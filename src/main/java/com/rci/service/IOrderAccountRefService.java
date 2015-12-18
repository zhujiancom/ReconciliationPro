package com.rci.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.enums.BusinessEnums.AccountCode;
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
	
	List<AccountSumResult> querySumAmount(Date postTime);
	
	List<AccountSumResult> queryDateRangeSumAmount(Date sdate,Date edate);
	
	Long getValidOrderCount(Date postTime,AccountCode account);
	
	BigDecimal querySumAmount(AccountCode account,Date postTime,OrderFramework framework);
}
