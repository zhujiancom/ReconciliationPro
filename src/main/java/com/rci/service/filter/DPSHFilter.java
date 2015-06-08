package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DPSHFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(DPSHFilter.class);
	
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_DPSH);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_DPSH);
		/* 不能使用闪惠支付的菜品总额 。 即酒水和配料 */
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"大众点评闪惠实际支付"+onlineAmount+"元";
		}else{
			schemeName = "大众点评闪惠实际支付"+onlineAmount+"元";
		}
		
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo = item.getDishNo();
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
			totalAmount = totalAmount.add(price);
			if (isNodiscount(dishNo)) {
				// 3. 饮料酒水除外
				nodiscountAmount = nodiscountAmount.add(price);
				continue;
			}
		}
		/* 最大可在线支付金额 */
		BigDecimal payAmount = totalAmount.subtract(nodiscountAmount);
		if(payAmount.compareTo(onlineAmount) < 0){
			order.setUnusual(YOrN.Y);
			logger.warn("--- 【"+order.getPayNo()+"】[大众闪惠支付异常] ---， 在线支付金额："+onlineAmount+" , 实际最大在线支付金额： "+payAmount+" ,不可在线支付金额："+nodiscountAmount);
		}
		BigDecimal freeAmount = payAmount.divideToIntegralValue(new BigDecimal("100")).multiply(new BigDecimal("12"));
		BigDecimal actualAmount = onlineAmount.subtract(freeAmount);
		
		order.setSchemeName(schemeName);
		//保存大众闪惠实际到账金额
		preserveOAR(actualAmount,BusinessConstant.DPSH_ACC,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
