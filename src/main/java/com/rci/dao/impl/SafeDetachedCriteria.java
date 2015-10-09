/**
 * 
 */
package com.rci.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SafeDetachedCriteria
 *
 * 包名称：com.rci.dao.impl
 *
 * Create Time: 2015年10月4日 下午5:16:38
 *
 */
public class SafeDetachedCriteria extends DetachedCriteria{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6414583547482798733L;

	protected SafeDetachedCriteria(String entityName) {
		super(entityName);
	}
	
	public static SafeDetachedCriteria forClass(Class clazz) {
		return new SafeDetachedCriteria(clazz.getName());
	}
	
	public SafeDetachedCriteria add(Criterion criterion) {
		if(criterion == null){
			return this;
		}
		return (SafeDetachedCriteria) add(criterion);
	}
}
