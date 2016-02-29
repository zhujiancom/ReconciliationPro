package com.rci.service.calculatecenter.filter;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;


public interface PaymodeFilter extends Indexable{
	public void doFilter(ParameterValue value,PaymodeFilterChain chain);
	
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping);
}
