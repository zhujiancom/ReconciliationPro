package com.rci.service.filter.algorithm;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.filter.FilterChain;

public interface ParameterValue {
	Order getOrder();
	
	FilterChain getFilterChain();
	
	/**
	 * 获取在线支付金额
	 * @return
	 */
	BigDecimal getOnlinePayAmount();
	
	/**
	 * 获取免单金额
	 * @return
	 */
	BigDecimal getFreeAmount();
	
	/**
	 * 获取订单总金额
	 * @return
	 */
	BigDecimal getOriginalAmount();
	
	/**
	 * 获取订单支付方式描述信息
	 * @return
	 */
	String getSchemeDescription();
	
	/**
	 * 获取订单适用的方案
	 * @return
	 */
	List<Scheme> getAppropriateSchemes();
	
	/**
	 * 设置合适的方案
	 * @param schemes
	 */
	void setAppropriateSchemes(List<Scheme> schemes);
	/**
	 * 设置vendor
	 * @param vendor
	 */
	void setVendor(Vendor vendor);
	/**
	 * 设置支付方式
	 * @param paymode
	 */
	void setPaymode(PaymodeCode paymode);
	/**
	 * 获取vendor
	 * @return
	 */
	Vendor getVendor();
	/**
	 * 获取支付方式
	 * @return
	 */
	PaymodeCode getPaymode();
}
