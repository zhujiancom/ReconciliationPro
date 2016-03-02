/**
 * 
 */
package com.rci.service.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.account.Account;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.IAccountService;
import com.rci.service.base.BaseServiceImpl;

/**
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AccountService
 *
 * 包名称：com.rci.service.impl
 *
 * Create Time: 2015年4月26日 上午1:02:49
 *
 * remark (备注):
 *
 */
@Service("AccountService")
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements IAccountService{

	@Override
	public Account getAccount(Long id) {
		return baseDAO.get(id);
	}

	@Override
	public Account getAccount(PaymodeCode paymode) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Account.class);
		sdc.add(SafeRestrictions.eq("paymodeNo", paymode.getPaymodeno()));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

	@Override
	public Account getAccount(String accNo) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Account.class);
		sdc.add(SafeRestrictions.eq("accNo", accNo));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

}
