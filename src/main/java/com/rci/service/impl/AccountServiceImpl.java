/**
 * 
 */
package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.account.Account;
import com.rci.service.IAccountService;
import com.rci.service.base.BaseService;

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
public class AccountServiceImpl extends BaseService<Account, Long> implements IAccountService{

	/* 
	 * @see com.rci.service.IAccountService#getAccByNo(java.lang.String)
	 */
	@Override
	public Account getAccByNo(String accNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Account.class);
		dc.add(Restrictions.eq("accNo", accNo));
		Account account = baseDAO.queryUniqueByCriteria(dc);
		return account;
	}

	@Override
	public Account getAccount(Long id) {
		return baseDAO.get(id);
	}

	@Override
	public void rwUpdateAccount(Account account) {
		super.rwUpdate(account);
	}

	@Override
	public void rwCreateAccount(Account account) {
		super.rwCreate(account);
	}

	@Override
	public void rwCreateAccount(Account[] accounts) {
		super.rwCreate(accounts);
	}

}
