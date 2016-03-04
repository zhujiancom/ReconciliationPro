package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
/**
 * 
 * remark (备注): 现金支付节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：CashFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:55:53
 *
 */
public class CashFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.CASH_MACHINE);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		BigDecimal cashAmount = value.getAmount(PaymodeCode.CASH_MACHINE);
		value.joinSchemeName("现金支付"+cashAmount);
		value.addPostAccountAmount(AccountCode.CASH_MACHINE, cashAmount);
	}
}
