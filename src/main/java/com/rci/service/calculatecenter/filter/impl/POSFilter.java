package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;

/**
 * 
 * remark (备注): pos机刷卡节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：POSFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:55:11
 *
 */
public class POSFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.POS);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.POS);
		BigDecimal postAmount = BigDecimal.ZERO;
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
			Scheme scheme = calculator.getAppropriteScheme(orderDate, BigDecimal.ZERO, Vendor.UNIONPAY);
			if(scheme != null){
				BigDecimal commission = scheme.getCommission();
				if(commission == null){
					value.joinWarningInfo("["+value.getPayNo()+"]-请确认POS机刷卡活动方案是否真不需要佣金");
					postAmount = onlineAmount;
				}else{
					postAmount = onlineAmount.subtract(DigitUtil.mutiplyDown(onlineAmount, DigitUtil.precentDown(scheme.getCommission(),3)));
				}
			}else{
				postAmount = onlineAmount;
			}
			BigDecimal freeAmount = onlineAmount.subtract(postAmount);
			value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
			
			value.addPostAccountAmount(AccountCode.ONLINE_POS, postAmount);
			value.addPostAccountAmount(AccountCode.FREE_POS, freeAmount);
			value.addPostAccountAmount(AccountCode.FREE_ONLINE, freeAmount);
			
			value.joinSchemeName("POS机刷卡支付"+postAmount+"元");
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		}
	}

}
