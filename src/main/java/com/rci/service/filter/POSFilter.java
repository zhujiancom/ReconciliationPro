/**
 * 
 */
package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：POSFilter
 *
 * 包名称：com.rci.service.filter
 *
 * Create Time: 2015年5月19日 下午11:57:16
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POSFilter extends AbstractFilter {
	/* 
	 * @see com.rci.service.filter.CalculateFilter#support(java.util.Map)
	 */
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_POS);
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#generateScheme(com.rci.bean.entity.Order, com.rci.service.filter.FilterChain)
	 */
	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_POS);
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"银联支付"+onlineAmount+"元";
		}else{
			schemeName = "银联支付"+onlineAmount+"元";
		}
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			isNodiscount(item.getDishNo());
		}
		
		BigDecimal postAmount = DigitUtil.mutiplyDown(onlineAmount, new BigDecimal("0.996"),3);
		order.setSchemeName(schemeName);
		//保存银联卡支付金额
		preserveOAR(postAmount,BusinessConstant.POS_ACC,order);
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#getSuitMap()
	 */
	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
