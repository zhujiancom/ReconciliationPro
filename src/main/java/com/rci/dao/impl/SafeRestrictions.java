/**
 * 
 */
package com.rci.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.rci.tools.StringUtils;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SafeRestrictions
 *
 * 包名称：com.rci.dao.impl
 *
 * Create Time: 2015年10月4日 下午5:17:21
 *
 */
public class SafeRestrictions extends Restrictions {
	public static SimpleExpression eq(String propertyName, Object value) {
		if(value == null){
			return null;
		}
		if(value.getClass().isAssignableFrom(String.class) && !StringUtils.hasText(value.toString())){
			return null;
		}
		return Restrictions.eq(propertyName, value);
	}
	
	public static SimpleExpression great(String propertyName,Object value){
		if(value == null){
			return null;
		}
		return Restrictions.ge(propertyName, value);
	}
	
	public static SimpleExpression less(String propertyName,Object value){
		if(value == null){
			return null;
		}
		return Restrictions.le(propertyName, value);
	}
	
	public static Criterion ands(Criterion lhs, Criterion rhs) {
		if(lhs == null && rhs == null){
			return null;
		}
		if(lhs == null){
			return rhs;
		}
		if(rhs == null){
			return lhs;
		}
		return Restrictions.and(lhs, rhs); //new LogicalExpression(lhs, rhs, "and");
	}
	
	public static SimpleExpression likeAnyWhere(String propertyName,String value){
		if(!StringUtils.hasText(value)){
			return null;
		}
		return like(propertyName,value,MatchMode.ANYWHERE);
	}
	
	public static SimpleExpression likeStart(String propertyName,String value){
		if(!StringUtils.hasText(value)){
			return null;
		}
		return like(propertyName,value,MatchMode.START);
	}
	
	public static SimpleExpression likeEnd(String propertyName,String value){
		if(!StringUtils.hasText(value)){
			return null;
		}
		return like(propertyName,value,MatchMode.END);
	}
}
