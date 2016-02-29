package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;

/**
 * 
 * remark (备注): 最后将数据保存到数据库节点， 该节点是链条上的最后一个节点，一定要执行，所以support永远返回true;
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：PreserveFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午5:55:10
 *
 */
public class PreserveFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return true;
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		//保存order 以及相关信息， 关联数据从ParameterValue中获取
	}

}
