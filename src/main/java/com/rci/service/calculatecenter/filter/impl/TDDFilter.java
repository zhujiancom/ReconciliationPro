package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

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
 * remark (备注):淘点点节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：TDDFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:53:14
 *
 */
public class TDDFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.TDD);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		order.setFramework(OrderFramework.TDD);
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.TDD);
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		BigDecimal originalAmount = order.getOriginPrice();
		BigDecimal payAmount = BigDecimal.ZERO;
		
		// 验证order 开始
		if(freeAmount == null){
			payAmount = originalAmount;
		}else{
			payAmount = originalAmount.subtract(freeAmount);
		}
		if(!payAmount.equals(onlineAmount)){
			order.setUnusual(YOrN.Y);
			logger.warn("--- 【"+order.getPayNo()+"】[淘点点支付异常] ---， 在线支付金额："+onlineAmount+" , 实际支付金额： "+payAmount);
			String warningInfo = "[淘点点支付异常]---  在线支付金额："+onlineAmount+" , 实际支付金额： "+payAmount;
			value.joinWarningInfo(warningInfo);
		}
		//验证order 结束
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
			List<Scheme> schemes = calculator.getAppropriteSchemes(orderDate, Vendor.TDD);
			if(CollectionUtils.isEmpty(schemes)){
				if(freeAmount != null){
					value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
				}
				value.addPayInfo(PaymodeCode.TDD, onlineAmount);
				value.joinSchemeName("淘点点在线支付"+onlineAmount+"元");
			}else{
				for(Scheme scheme:schemes){
					if(originalAmount.compareTo(scheme.getFloorAmount())>=0 && originalAmount.compareTo(scheme.getCeilAmount()) < 0 ){
						//享受满减活动
						//满减活动
						if(freeAmount != null){
							freeAmount = freeAmount.add(scheme.getSpread());
						}else{
							freeAmount = scheme.getSpread();
						}
						BigDecimal postAmount = onlineAmount.subtract(scheme.getSpread());
						value.addPayInfo(PaymodeCode.TDD, postAmount);
						value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
						value.joinSchemeName(scheme.getName(),"淘点点在线支付"+postAmount+"元 ");
					}else{
						continue;
					}
				}
			}
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		}
		
	}
}
