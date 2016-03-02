package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.StringUtils;

/**
 * 
 * remark (备注): 记录未知支付方式的节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：UnknowFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午5:54:11
 *
 */
public class UnknowFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.UNKNOW);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		String[] paymodes = StringUtils.split(order.getPaymodes(),",");
		for(String paymode:paymodes){
			if(PaymodeCode.UNKNOW.equals(PaymodeCode.paymodeCode(paymode))){
				value.joinSchemeName("未知支付方式编码："+paymode);
				order.setUnusual(YOrN.Y);
			}
		}
	}

}
