package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.StringUtils;

/**
 * pos机现金
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SQBFilter extends AbstractFilter {
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.SQB);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		order.setFramework(OrderFramework.TS);
		BigDecimal sqbAmount = order.getPaymodeMapping().get(PaymodeCode.SQB); //收钱吧金额
		boolean flag = false; //收钱吧只能支付单个菜品
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo=item.getDishNo();
			isNodiscount(dishNo);
			BigDecimal singlePrice = item.getPrice();
			if(singlePrice.compareTo(sqbAmount) == 0){ //如果收钱吧支付金额正好是单个菜品的金额，则表示收钱吧支付符合规则，否则标记出错
				flag = true;
				break;
			}
		}
		if(!flag){
			order.setUnusual(YOrN.Y);
			logger.debug("--- 【"+order.getPayNo()+"】[收钱吧支付异常] ---,收钱吧只能支付单个菜品");
			String warningInfo = "[收钱吧支付异常]--- 收钱吧只能支付单个菜品";
			order.setWarningInfo(warningInfo);
		}
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",收钱吧支付-"+sqbAmount;
		}else{
			schemeName = "收钱吧支付-"+sqbAmount;
		}
		order.setSchemeName(schemeName);
		preserveOAR(sqbAmount, AccountCode.SQB, order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
		// TODO Auto-generated method stub
		
	}
}
