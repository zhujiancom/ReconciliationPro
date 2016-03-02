package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;

/**
 * 
 * remark (备注): 代金券
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：VoucherFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年3月2日 下午1:14:38
 *
 */
public class VoucherFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.VOUCHER);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		BigDecimal voucherAmount = value.getAmount(PaymodeCode.VOUCHER);
		value.joinSchemeName("使用代金券"+voucherAmount+"元");
		value.addPayInfo(PaymodeCode.ONLINE_FREE, voucherAmount);
		
	}

}
