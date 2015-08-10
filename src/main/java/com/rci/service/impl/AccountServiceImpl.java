/**
 * 
 */
package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.AccountCode;
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

	/* 
	 * @see com.rci.service.IAccountService#getAccByNo(java.lang.String)
	 */
	@Override
	public Account getAccByNo(AccountCode accNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Account.class);
		dc.add(Restrictions.eq("accNo", accNo.name()));
		Account account = baseDAO.queryUniqueByCriteria(dc);
		return account;
	}

	@Override
	public Account getAccount(Long id) {
		return baseDAO.get(id);
	}

}
