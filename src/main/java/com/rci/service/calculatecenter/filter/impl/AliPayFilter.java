package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;

/**
 * 
 * remark (备注): 支付宝支付节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AliPayFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:52:51
 *
 */
public class AliPayFilter extends AbstractPaymodeFilter {
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.ALIPAY);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		order.setFramework(OrderFramework.ALIPAY);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.ALIPAY);
		BigDecimal originalAmount = order.getOriginPrice();
		BigDecimal postAmount = BigDecimal.ZERO;
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
			Scheme scheme = calculator.getAppropriteScheme(orderDate, BigDecimal.ZERO, Vendor.ALIPAY);
			if(scheme != null){
				postAmount = onlineAmount.add(scheme.getPostPrice());
				BigDecimal commission = scheme.getCommission();
				if(commission == null){
					value.joinWarningInfo("请确认支付宝活动方案是否真不需要佣金");
				}else{
					postAmount = DigitUtil.mutiplyDown(originalAmount, DigitUtil.precentDown(scheme.getCommission()));
				}
			}else{
				if(onlineAmount.compareTo(originalAmount) != 0){
					value.joinWarningInfo("支付宝支付金额应该与订单原价相同！");
				}
				postAmount = onlineAmount;
			}
			value.joinSchemeName("支付宝在线支付"+postAmount+"元");
			BigDecimal freeAmount = originalAmount.subtract(postAmount);
			value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);				//标记是线上免单优惠
			
			value.addPostAccountAmount(AccountCode.ONLINE_ALIPAY, postAmount);	//支付宝商家入账金额
			value.addPostAccountAmount(AccountCode.FREE_ALIPAY, freeAmount);  //支付宝商家免单金额
			value.addPostAccountAmount(AccountCode.FREE_ONLINE, freeAmount);
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		}
	}
}
