package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;

/**
 * 
 * remark (备注): 免单节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：FreeFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:59:42
 *
 */
public class OffLineFreeFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		if(paymodeMapping.containsKey(PaymodeCode.ONLINE_FREE)){//已经经过线上优惠处理
			return false;
		}
		return paymodeMapping.containsKey(PaymodeCode.FREE);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		if(freeAmount.compareTo(BigDecimal.ZERO) > 0){
			value.joinSchemeName("堂食免单"+freeAmount+"元");
		}
		value.addPayInfo(PaymodeCode.OFFLINE_FREE, freeAmount);
	}

}
