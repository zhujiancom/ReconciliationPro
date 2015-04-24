package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.bean.entity.Order;

public interface CalculateFilter {
	public static final Integer NORMAL=0;
	public static final Integer UNUSUAL=1;
	
	boolean support(Map<String,BigDecimal> paymodeMapping);
	
	void doFilter(Order order,FilterChain chain);
	
	String getChit();
}
