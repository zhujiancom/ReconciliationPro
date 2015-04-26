/**
 * 
 */
package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.account.Account;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

/**
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AccountRepository
 *
 * 包名称：com.rci.dao.repository
 *
 * Create Time: 2015年4月26日 上午1:01:23
 *
 * remark (备注):
 *
 */
@Repository
public class AccountRepository  extends DefaultHibernateDAOFacadeImpl<Account, Long>{

}
