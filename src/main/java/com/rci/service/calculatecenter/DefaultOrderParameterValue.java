package com.rci.service.calculatecenter;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.PaymodeCode;

public class DefaultOrderParameterValue implements ParameterValue {
	private Order order;
	
	private Map<PaymodeCode,BigDecimal> paymodeMapping;
	
	public DefaultOrderParameterValue(Order order){
		this.order = order;
	}

	@Override
	public Object getSourceData() {
		return order;
	}

	@Override
	public String getPayNo() {
		return order.getPayNo();
	}

	@Override
	public Map<PaymodeCode, BigDecimal> getPayInfo() {
		paymodeMapping = order.getPaymodeMapping();
		return paymodeMapping;
	}

	@Override
	public void addPayInfo(PaymodeCode code, BigDecimal amount) {
		if(paymodeMapping == null){
			paymodeMapping = order.getPaymodeMapping();
		}
		paymodeMapping.put(code, amount);
	}

	@Override
	public BigDecimal getAmount(PaymodeCode code) {
		return paymodeMapping.get(code);
	}
}
