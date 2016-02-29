package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.service.calculatecenter.filter.PaymodeFilterChain;

/**
 * 
 * remark (备注): 百度糯米节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：BDNMFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:54:00
 *
 */
public class BDNMFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.BDNM);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		// TODO Auto-generated method stub
		
	}

}
