package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.PaymodeCode;

public class FilterChain implements CalculateFilter {
	LinkedList<CalculateFilter> filters = new LinkedList<CalculateFilter>();
	int pos = 0;
	BigDecimal balance = BigDecimal.ZERO;
	private Map<String,BigDecimal> freeOnlineMap = new HashMap<String,BigDecimal>();
	
	
	public void addFilter(CalculateFilter filter){
		this.filters.add(filter);
	}
	
	public void addFilters(List<CalculateFilter> filters){
		this.filters.addAll(filters);
	}
	
	
	@Override
	public void doFilter(Order order, FilterChain chain) {
		if(pos == filters.size()){
			return;
		}
		CalculateFilter filter = filters.get(pos);
		pos++;
		filter.doFilter(order,chain);
	}

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		throw new UnsupportedOperationException();
	}

	public void reset(){
		this.pos = 0;
		this.balance = BigDecimal.ZERO;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the freeOnlineMap
	 */
	public Map<String, BigDecimal> getFreeOnlineMap() {
		return freeOnlineMap;
	}

	/**
	 * @param freeOnlineMap the freeOnlineMap to set
	 */
	public void setFreeOnlineMap(Map<String, BigDecimal> freeOnlineMap) {
		this.freeOnlineMap = freeOnlineMap;
	}


}
