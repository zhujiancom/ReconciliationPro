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
import com.rci.bean.entity.SchemeType;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
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
		return paymodeMapping.containsKey(PaymodeCode.ALIPAY);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		/* 只有堂食或外带等到店消费的形式才能用支付宝  */
		order.setFramework(OrderFramework.ALIPAY);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.ALIPAY);
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",支付宝支付"+onlineAmount+"元";	
		}else{
			schemeName = "支付宝支付"+onlineAmount+"元";
		}
		order.setSchemeName(schemeName);
		//保存淘点点在线支付金额
		preserveOAR(onlineAmount,BusinessConstant.AccountCode_ALIPAY,order);
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
	}
}
