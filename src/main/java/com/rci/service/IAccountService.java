/**
 * 
 */
package com.rci.service;

import com.rci.bean.entity.account.Account;
import com.rci.service.base.IBaseService;

/**
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：IAccountService
 *
 * 包名称：com.rci.service
 *
 * Create Time: 2015年4月26日 上午1:01:03
 *
 * remark (备注):
 *
 */
public interface IAccountService extends IBaseService<Account, Long>{
	/**
	 * 
	 * Describle(描述)： 根据账户号获取账户
	 *
	 * 方法名称：getAccByNo
	 *
	 * 所在类名：IAccountService
	 *
	 * Create Time:2015年6月23日 上午11:03:39
	 *  
	 * @param accNo
	 * @return
	 */
	Account getAccByNo(String accNo);
	
	/**
	 * 
	 * Describle(描述)： 根据主键获取账户
	 *
	 * 方法名称：getAccount
	 *
	 * 所在类名：IAccountService
	 *
	 * Create Time:2015年6月23日 上午11:03:56
	 *  
	 * @param id
	 * @return
	 */
	Account getAccount(Long id);
}
