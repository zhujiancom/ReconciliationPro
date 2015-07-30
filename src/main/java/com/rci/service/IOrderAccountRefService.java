package com.rci.service;

import java.util.Date;
import java.util.List;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.base.IBaseService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;

public interface IOrderAccountRefService extends IBaseService<OrderAccountRef, Long>{
	List<OrderAccountRef> getOARef(String billno);
	
	void deleteOar(Date date);
	
	List<AccountSumResult> querySumAmount(Date postTime);
	
	Long getValidOrderCount(Date postTime,Vendor vendor);
}
