package com.rci.service.filter.algorithm;

import com.rci.bean.entity.Order;
import com.rci.service.filter.FilterChain;

public class DefaultParameterCreator implements ParameterCreator {
	private Order order;
	
	private FilterChain chain;

	public DefaultParameterCreator(Order order, FilterChain chain) {
		super();
		this.order = order;
		this.chain = chain;
	}

	@Override
	public ParameterValue doCreateParamterFromOrder() {
		DefaultParameterValue parameter = new DefaultParameterValue(order);
		parameter.setChain(chain);
		return parameter;
	}
}
