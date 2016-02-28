package com.rci.service.filter.algorithm;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.filter.FilterChain;

public class DefaultParameterValue implements ParameterValue {
	private Order order;
	
	private FilterChain chain;
	
	private PaymodeCode paymode;
	
	private Vendor vendor;
	
	private List<Scheme> schemes = Collections.emptyList();
	
	public DefaultParameterValue() {
		super();
	}
	
	public DefaultParameterValue(Order order) {
		super();
		this.order = order;
	}

	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public FilterChain getFilterChain() {
		return chain;
	}

	@Override
	public BigDecimal getOnlinePayAmount() {
		return order.getPaymodeMapping().get(paymode);
	}

	@Override
	public BigDecimal getFreeAmount() {
		return order.getPaymodeMapping().get(PaymodeCode.FREE);
	}

	@Override
	public BigDecimal getOriginalAmount() {
		return order.getOriginPrice();
	}

	@Override
	public String getSchemeDescription() {
		return order.getSchemeName();
	}

	public void setChain(FilterChain chain) {
		this.chain = chain;
	}

	@Override
	public List<Scheme> getAppropriateSchemes() {
		return schemes;
	}

	@Override
	public void setAppropriateSchemes(List<Scheme> schemes) {
		this.schemes = schemes;
	}

	@Override
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@Override
	public void setPaymode(PaymodeCode paymode) {
		this.paymode = paymode;
	}

	@Override
	public Vendor getVendor() {
		return vendor;
	}

	@Override
	public PaymodeCode getPaymode() {
		return paymode;
	}
}
