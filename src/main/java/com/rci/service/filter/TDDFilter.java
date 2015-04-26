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
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TDDFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_TDD);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_TDD);
		BigDecimal actualAmount = BigDecimal.ZERO;
		String schemeName = order.getSchemeName();
		schemeName = schemeName+","+"淘点点在线支付"+onlineAmount+"元";
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
			actualAmount = actualAmount.add(price);
		}
		if(actualAmount.compareTo(onlineAmount) != 0){
			order.setUnusual(YOrN.Y);
			logger.warn("[--- ELEFilter ---]: 在线支付金额："+onlineAmount+" , 实际支付金额： "+actualAmount);
		}
		order.setSchemeName(schemeName);
		//保存淘点点在线支付金额
		preserveOAR(actualAmount,BusinessConstant.TDD_ACC,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}
}
