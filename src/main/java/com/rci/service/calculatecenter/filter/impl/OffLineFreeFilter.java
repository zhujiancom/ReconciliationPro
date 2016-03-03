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
		return paymodeMapping.containsKey(PaymodeCode.FREE);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);		//收银机的免单金额
		BigDecimal onlineFreeAmount = value.getAmount(PaymodeCode.ONLINE_FREE);  //经过前面处理器处理后查看有没有在线免单的金额
		if(onlineFreeAmount == null){ // 没有在线免单
			value.addPayInfo(PaymodeCode.OFFLINE_FREE, freeAmount);
			value.joinSchemeName("堂食免单"+freeAmount+"元");
		}else{
			BigDecimal offlineFreeAmount = freeAmount.subtract(onlineFreeAmount);
			if(offlineFreeAmount.compareTo(BigDecimal.ZERO) > 0){
				value.addPayInfo(PaymodeCode.OFFLINE_FREE, offlineFreeAmount);
				value.joinSchemeName("堂食免单"+offlineFreeAmount+"元");
			}
		}
	}

}
