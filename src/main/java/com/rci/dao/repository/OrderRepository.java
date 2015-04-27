/**
 * 
 */
package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.Order;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

/**
 * @author zj
 *	
 * 项目名称：BillSystem
 *
 * 类名称：OrderRepository
 *
 * 包名称：com.bill.sys.dao
 *
 * Create Time: 2015年4月22日 下午11:24:48
 *
 * remark (备注):
 *
 */
@Repository
public class OrderRepository extends DefaultHibernateDAOFacadeImpl<Order, Long> {

}
