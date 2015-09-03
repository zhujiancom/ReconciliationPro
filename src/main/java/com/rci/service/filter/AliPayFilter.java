/**
 * 
 */
package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.tools.StringUtils;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AliPayFilter
 *
 * 包名称：com.rci.service.filter
 *
 * Create Time: 2015年9月3日 下午4:39:27
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AliPayFilter extends AbstractFilter{

	/* 
	 * @see com.rci.service.filter.CalculateFilter#support(java.util.Map)
	 */
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.ZFB);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.ZFB);
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",支付宝支付"+onlineAmount+"元";	
		}else{
			schemeName = "支付宝支付"+onlineAmount+"元";
		}
		order.setSchemeName(schemeName);
		//保存淘点点在线支付金额
		preserveOAR(onlineAmount,AccountCode.ALIPAY,order);
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#getSuitMap()
	 */
	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
//		BigDecimal aliPayAmount = BigDecimal.ZERO;
//		BigDecimal otherAmount = BigDecimal.ZERO;
//		BigDecimal originAmount = order.getOriginPrice();
//		for(Iterator<Entry<PaymodeCode,BigDecimal>> it = order.getPaymodeMapping().entrySet().iterator();it.hasNext();){
//			Entry<PaymodeCode,BigDecimal> entry = it.next();
//			PaymodeCode paymode = entry.getKey();
//			BigDecimal amount = entry.getValue();
//			if(!PaymodeCode.TDD.equals(paymode)){
//				otherAmount = otherAmount.add(amount);
//			}
//		}
//		aliPayAmount = originAmount.subtract(otherAmount);
	}

}