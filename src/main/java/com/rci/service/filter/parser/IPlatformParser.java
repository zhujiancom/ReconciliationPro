/**
 * 
 */
package com.rci.service.filter.parser;

import com.rci.bean.entity.Order;

/**
 * remark (备注):	对于不同销售平台使用的不同算法策略
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：IPlatformParser
 *
 * 包名称：com.rci.service.filter
 *
 * Create Time: 2016年1月22日 上午10:03:56
 *
 */
public interface IPlatformParser {
	void doParse(Order order);
}
