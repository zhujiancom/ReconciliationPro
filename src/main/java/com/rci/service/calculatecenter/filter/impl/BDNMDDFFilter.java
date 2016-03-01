package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.exceptions.ServiceException;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;

/**
 * 
 * remark (备注): 百度糯米到店付节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：BDNMDDFFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:54:29
 *
 */
public class BDNMDDFFilter extends AbstractPaymodeFilter {
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.DDF);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		order.setFramework(OrderFramework.BDNM);
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.DDF);
		BigDecimal originAmount = order.getOriginPrice();
		/* 不参与打折的菜品  */
		BigDecimal nodiscountAmount = getUndiscountAmount(order.getItems());
		/* 最大可在线支付金额 */
		BigDecimal payAmount = originAmount.subtract(nodiscountAmount);
		if(onlineAmount.compareTo(payAmount) > 0){
			//实际在线支付金额大于理论上最大的在线支付金额
			order.setUnusual(YOrN.Y);
			logger.warn("---["+value.getPayNo()+"-百度糯米到店付支付异常]-最大可在线支付金额是"+payAmount+",实际在线支付金额是"+onlineAmount);
			value.joinWarningInfo("["+value.getPayNo()+"-百度糯米到店付支付异常]-最大可在线支付金额是"+payAmount+",实际在线支付金额是"+onlineAmount);
		}
		
		/* 设置订单中不可打折金额 */
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(order.getDay(), "yyyyMMdd");
			if(order.getCheckoutTime().after(DateUtil.getTimeOfDay(orderDate, 21, 0, 0, 0))){  
				//过了21:00到店付没有优惠
				value.addPayInfo(PaymodeCode.DDF, onlineAmount);
				return;
			}
			
			if(onlineAmount.compareTo(originAmount) == 0){//全部在到店付平台支付
				payAmount = originAmount.subtract(nodiscountAmount);
			}else{ //不可打折金额使用另外支付方式（如现金）等支付
				payAmount = onlineAmount;
			}
			BigDecimal[] result = calculator.doCalculateAmountForOnlinePay(payAmount,orderDate,Vendor.DDF);
			value.addPayInfo(PaymodeCode.DDF, result[0]);
			value.addPayInfo(PaymodeCode.ONLINE_FREE, result[1]);
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		} catch (ServiceException se){
			value.addPayInfo(PaymodeCode.DDF, onlineAmount);
		}
	}

}
