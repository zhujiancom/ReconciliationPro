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
public class ELEFilter extends AbstractPaymodeFilter {

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
					BigDecimal postAmount = originalAmount.subtract(scheme.getSpread());		//商家到账金额
					BigDecimal allowanceAmount = scheme.getPostPrice();							//平台给商家的补贴金额
					BigDecimal predictOnlineAmount = originalAmount.subtract(scheme.getPrice());  //预期收银机应显示的金额{菜品总价-商家补贴-平台补贴} 
					if(predictOnlineAmount.compareTo(onlineAmount) != 0){
						order.setUnusual(YOrN.Y);
						value.joinWarningInfo("["+value.getPayNo()+"],在线支付金额{"+onlineAmount+"}错误,应该是{"+predictOnlineAmount+"}");
					}
					value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
					
					value.addPostAccountAmount(AccountCode.ONLINE_ELE, predictOnlineAmount);
					value.addPostAccountAmount(AccountCode.ALLOWANCE_ELE, allowanceAmount);
					value.addPostAccountAmount(AccountCode.FREE_ELE, scheme.getSpread());
					value.addPostAccountAmount(AccountCode.FREE_ONLINE, scheme.getSpread());
					value.joinSchemeName(scheme.getName(),"饿了么在线支付到账-"+postAmount+"元","平台补贴"+allowanceAmount+"元");
				}else{
					logger.warn(order.getPayNo()+"---[饿了么 ] 没有找到匹配的Scheme -----");
					String warningInfo = "[饿了么]--- 没有找到匹配的Scheme";
					value.joinWarningInfo(warningInfo);
					value.joinSchemeName("饿了么无法解析活动方案");
					order.setUnusual(YOrN.Y);
					
					value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
					value.addPostAccountAmount(AccountCode.ONLINE_ELE, onlineAmount);
					value.addPostAccountAmount(AccountCode.FREE_ELE, freeAmount);
					value.addPostAccountAmount(AccountCode.FREE_ONLINE, freeAmount);
				}
			} catch (ParseException pe) {
				logger.warn("日期["+day+"]转换错误", pe);
			}
		}else{
			if(onlineAmount.compareTo(originalAmount) != 0){
				value.joinWarningInfo("饿了么在线支付金额不正确！");
				order.setUnusual(YOrN.Y);
			}
			value.joinSchemeName("饿了么在线支付到账-"+onlineAmount+"元");
			value.addPostAccountAmount(AccountCode.ONLINE_ELE, originalAmount);
		}
	}

}
