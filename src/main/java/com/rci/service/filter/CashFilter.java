package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

/**
 * pos机现金
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CashFilter extends AbstractFilter {
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.CASH_MACHINE);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		order.setFramework(OrderFramework.TS);
		BigDecimal cashAmount = order.getPaymodeMapping().get(PaymodeCode.CASH_MACHINE);
		BigDecimal originAmount = order.getOriginPrice();
		BigDecimal actualAmount = BigDecimal.ZERO;
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo=item.getDishNo();
			isNodiscount(dishNo);
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate);
			actualAmount = actualAmount.add(price);
			/* 判断是否有单品折扣  */
			if((order.getSingleDiscount() == null || YOrN.N.equals(order.getSingleDiscount())) && isSingleDiscount(ratepercent)){
				order.setSingleDiscount(YOrN.Y);
			}
		}
		//整单结算 向上取整
		String schemeName = order.getSchemeName();
		actualAmount = actualAmount.setScale(0, BigDecimal.ROUND_CEILING);
		if(actualAmount.compareTo(originAmount)<0){
			//可享受8折优惠
			if(cashAmount.compareTo(actualAmount) != 0){
				//如果收银机显示现金收入和计算收入不相符时，报异常
				order.setUnusual(YOrN.Y);
				logger.debug("--- 【"+order.getPayNo()+"】[收银机支付异常] ---，#8折优惠# 收银机显示金额："+cashAmount+" , 应该显示金额： "+actualAmount);
				String warningInfo = "[收银机支付异常]--- #8折优惠#收银机实际入账金额："+cashAmount+",应入账金额："+actualAmount;
				order.setWarningInfo(warningInfo);
			}
			if(StringUtils.hasText(schemeName)){
				schemeName = schemeName+",店内现金优惠-"+actualAmount;
			}else{
				schemeName = "店内现金优惠-"+actualAmount;
			}
			order.setSchemeName(schemeName);
			preserveOAR(actualAmount, AccountCode.CASH_MACHINE, order);
		}
		else if(actualAmount.compareTo(originAmount)==0){
			//无折扣
			if(StringUtils.hasText(schemeName)){
				schemeName = schemeName+",现金支付-"+cashAmount;
			}else{
				schemeName = "现金支付-"+cashAmount;
			}
			order.setSchemeName(schemeName);
			preserveOAR(cashAmount, AccountCode.CASH_MACHINE, order);
		}
		else{
			logger.error("----【"+order.getPayNo()+"】[收银机支付异常] ---, 实际金额不应该大于原价");
			String warningInfo = "[收银机支付异常]--- 收银机实际入账金额："+cashAmount+" 不应大于原价："+originAmount;
			order.setWarningInfo(warningInfo);
			preserveOAR(cashAmount, AccountCode.CASH_MACHINE, order);
		}
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
