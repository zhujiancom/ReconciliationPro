package com.rci.service.filter.algorithm;

import java.math.BigDecimal;

import com.rci.bean.entity.Order;

public abstract class AbstractAlgorithmCallback implements AlgorithmCallback {
	@Override
	public void dowork(ParameterValue parameter) {
		Order order = parameter.getOrder();
		BigDecimal onlineAmount = parameter.getOnlinePayAmount();
		setSchemeDescription(order,onlineAmount);
		doInternalwork(parameter);
	}
	
	protected abstract void setSchemeDescription(Order order,BigDecimal onlineAmount);
	
	protected abstract void doInternalwork(ParameterValue parameter);
}
