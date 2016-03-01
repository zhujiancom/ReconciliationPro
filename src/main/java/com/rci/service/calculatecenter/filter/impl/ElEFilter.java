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
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;

/**
 * 
 * remark (备注): 饿了么支付节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：ElEFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年3月1日 上午11:17:13
 *
 */
public class ElEFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.ELE);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		order.setFramework(OrderFramework.ELE);
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.ELE);
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		BigDecimal originalAmount = order.getOriginPrice();
		if(freeAmount != null){
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				Scheme scheme = calculator.getAppropriteScheme(orderDate, freeAmount, Vendor.ELE);
				if(scheme != null){
					value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
					BigDecimal postAmount = originalAmount.subtract(scheme.getSpread());
					if(onlineAmount.compareTo(postAmount) != 0){
						order.setUnusual(YOrN.Y);
						value.joinWarningInfo("["+value.getPayNo()+"],在线支付金额{"+onlineAmount+"}错误,应该是{"+postAmount+"}");
					}
					value.joinSchemeName(scheme.getName(),"饿了么在线支付到账-"+postAmount+"元");
					value.addPayInfo(PaymodeCode.ELE, scheme.getPostPrice());
					value.addPayInfo(PaymodeCode.ELE, postAmount);
				}else{
					logger.warn(order.getPayNo()+"---[饿了么 活动] 没有找到匹配的Scheme -----");
					String warningInfo = "[饿了么 活动]--- 没有找到匹配的Scheme";
					value.joinWarningInfo(warningInfo);
					order.setUnusual(YOrN.Y);
				}
			} catch (ParseException pe) {
				logger.warn("日期["+day+"]转换错误", pe);
			}
		}
	}

}
