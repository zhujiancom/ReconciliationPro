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
	
	void deleteOar(Date date);
	
	List<AccountSumResult> querySumAmount(Date postTime);
	
	List<AccountSumResult> queryDateRangeSumAmount(Date sdate,Date edate);
	
	Long getValidOrderCount(Date postTime,AccountCode account);
	
	BigDecimal querySumAmount(AccountCode account,Date postTime,OrderFramework framework);
}
