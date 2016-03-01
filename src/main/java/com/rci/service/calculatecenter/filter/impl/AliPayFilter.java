package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
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
		BigDecimal postAmount = BigDecimal.ZERO;
		BigDecimal freeAmount = BigDecimal.ZERO;
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
			Scheme scheme = calculator.getAppropriteScheme(orderDate, BigDecimal.ZERO, Vendor.ALIPAY);
			if(scheme != null){
				BigDecimal commission = scheme.getCommission();
				if(commission == null){
					value.joinWarningInfo("["+value.getPayNo()+"]-支付宝活动方案缺少佣金值");
					postAmount = onlineAmount;
				}else{
					postAmount = DigitUtil.mutiplyDown(onlineAmount, DigitUtil.precentDown(scheme.getCommission()));
				}
			}else{
				postAmount = onlineAmount;
			}
			freeAmount = onlineAmount.subtract(postAmount);
			if(freeAmount.compareTo(BigDecimal.ZERO) != 0){
				value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
			}
			value.addPayInfo(PaymodeCode.ALIPAY, postAmount);
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		}
	}
}
