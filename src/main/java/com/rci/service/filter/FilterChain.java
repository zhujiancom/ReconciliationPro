package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.PaymodeCode;

@Component("filterChain")
public class FilterChain implements CalculateFilter,InitializingBean {
//	LinkedList<CalculateFilter> filters = new LinkedList<CalculateFilter>();
	private static final Log logger = LogFactory.getLog(FilterChain.class);
	@Autowired
	private List<CalculateFilter> filters;
	
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
			reset();
			logger.debug("order["+order.getPayNo()+"] -  链条执行完成！");
			return;
		}
		CalculateFilter filter = filters.get(pos);
		logger.debug("order["+order.getPayNo()+"] - 正在执行链条【"+pos+"】-"+filter.getClass().getName());
		pos++;
		filter.doFilter(order,chain);
	}

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		throw new UnsupportedOperationException();
	}

	public void reset(){
		this.pos = 0;
		freeOnlineMap.clear();
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

	@Override
	public void afterPropertiesSet() throws Exception {
		Collections.sort(filters, new Comparator<CalculateFilter>() {

			@Override
			public int compare(CalculateFilter f1, CalculateFilter f2) {
				if(f1 instanceof FreeFilter || f2 instanceof FreeFilter){
					return 0;
				}
				return -1;
			}
		});
		for(CalculateFilter filter:filters){
			logger.debug("初始化链条排列顺序-"+filter.getClass().getName());
		}
	}


}
