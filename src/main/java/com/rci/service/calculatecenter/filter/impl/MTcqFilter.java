package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ServiceException;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;

/**
 * 
 * remark (备注): 美团超券支付节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：MTCQFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:51:17
 *
 */
public class MTcqFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MTSUPER);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.MTSUPER);
		/* 不参与打折的菜品  */
		BigDecimal nodiscountAmount = getUndiscountAmount(order.getItems());
		
		/* 设置订单中不可打折金额 */
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(order.getDay(), "yyyyMMdd");
			if(order.getCheckoutTime().after(DateUtil.getTimeOfDay(orderDate, 23, 0, 0, 0))){  
				//过了22:00到店付没有优惠
				value.addPostAccountAmount(AccountCode.MT_SUPER, onlineAmount);
				value.joinSchemeName("美团超券在线支付"+onlineAmount+"元");
				return;
			}
			/* 最大可在线支付金额 */
			BigDecimal payAmount = onlineAmount.subtract(nodiscountAmount);
			BigDecimal[] actualResult = calculator.doCalculateAmountForOnlinePay(payAmount,orderDate,Vendor.MTSUPER);
			
			value.addPayInfo(PaymodeCode.ONLINE_FREE, actualResult[1]);
			
			BigDecimal postAmount = actualResult[0].add(nodiscountAmount);
			value.addPostAccountAmount(AccountCode.MT_SUPER, postAmount);
			value.addPostAccountAmount(AccountCode.FREE_MT_SUPER, actualResult[1].negate());
			value.addPostAccountAmount(AccountCode.FREE_ONLINE, actualResult[1].negate());
			
			value.joinSchemeName("美团超券在线支付"+postAmount+"元");
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		} catch (ServiceException se){
			value.addPostAccountAmount(AccountCode.MT_SUPER, onlineAmount);
			value.joinSchemeName("美团超券在线支付"+onlineAmount+"元");
			value.joinWarningInfo(se.getMessage());
		}
	}
}
