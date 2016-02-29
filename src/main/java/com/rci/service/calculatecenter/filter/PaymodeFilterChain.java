package com.rci.service.calculatecenter.filter;

import com.rci.service.calculatecenter.ParameterValue;

public interface PaymodeFilterChain {
	public void doFilter(ParameterValue value);
}
