package com.rci.service.filter.algorithm;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.filter.FilterChain;
import com.rci.tools.DateUtil;

public class DefaultParameterValue implements ParameterValue {
	private static final Log logger = LogFactory.getLog(DefaultParameterValue.class);
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

	@Override
	public Date getOrderDate() {
		String day = order.getDay();
		try {
			return DateUtil.parseDate(day,"yyyyMMdd");
		} catch (ParseException pe) {
			logger.error("订单日期["+day+"]格式转换错误", pe);
		}
		return null;
	}
}
