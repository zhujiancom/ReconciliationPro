package com.rci.service.calculatecenter;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.enums.BusinessEnums.PaymodeCode;


public interface ParameterValue {
	Object getSourceData();
	
	/**
	 * 
	 * Describle(描述)：获取订单结账编号
	 *
	 * 方法名称：getPayNo
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年2月29日 下午3:41:03
	 *  
	 * @return
	 */
	String getPayNo();
	
	/**
	 * 
	 * Describle(描述)：记录每个订单的支付信息，主要用来判断该订单是否经过了在线优惠免单的处理
	 * if(result.get(PaymodeCode.ONLINE_FREE) != null){
	 * 	表示经过了线上免单处理，线下免单处理过滤器则不再执行
	 * }
	 *
	 * 方法名称：getPayInfo
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年2月29日 下午3:25:09
	 *  
	 * @param payno
	 * @return
	 */
	Map<PaymodeCode,BigDecimal> getPayInfo();
	
	/**
	 * 
	 * Describle(描述)：主要新增线下，线上免单金额
	 *
	 * 方法名称：addPayInfo
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年2月29日 下午3:56:08
	 *  
	 * @param code
	 * @param amount
	 */
	void addPayInfo(PaymodeCode code,BigDecimal amount);
	
	/**
	 * 
	 * Describle(描述)：获取每种支付方式的支付金额
	 *
	 * 方法名称：getAmount
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年2月29日 下午3:58:35
	 *  
	 * @param code
	 * @return
	 */
	BigDecimal getAmount(PaymodeCode code);
	
	/**
	 * 
	 * Describle(描述)：添加order所关联的方案信息
	 *
	 * 方法名称：joinSchemeName
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年3月1日 上午9:41:43
	 *  
	 * @param schemeName
	 * @return
	 */
	String joinSchemeName(String... schemeName);
	
	/**
	 * 
	 * Describle(描述)： 添加order上产生的警告信息
	 *
	 * 方法名称：joinWarningInfo
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年3月1日 上午10:12:47
	 *  
	 * @param warningInfo
	 * @return
	 */
	String joinWarningInfo(String... warningInfos);
}
