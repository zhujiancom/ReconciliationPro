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
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MTSuperFilter extends AbstractFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MTSUPER);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.MTSUPER);
		/* 最大可在线支付金额,参与优惠金额 */
		BigDecimal payAmount = BigDecimal.ZERO;
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"美团超级代金券在线支付"+onlineAmount+"元";
		}else{
			schemeName = "美团超级代金券在线支付"+onlineAmount+"元";
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
			if (isNodiscount(dishNo)) {
				// 3. 饮料酒水除外
				nodiscountAmount = nodiscountAmount.add(price);
				continue;
			}
			payAmount = payAmount.add(price);
		}
		
		if(onlineAmount.compareTo(payAmount) > 0){
			order.setUnusual(YOrN.Y);
			logger.warn("---【"+order.getPayNo()+"】[美团超级代金券支付异常]---，  在线支付金额："+onlineAmount+" , 实际最大在线支付金额：  "+payAmount+"， 不可在线支付金额："+nodiscountAmount);
			String warningInfo = "[美团超级代金券支付异常]---   在线支付金额："+onlineAmount+" , 实际最大在线支付金额：  "+payAmount+"， 不可在线支付金额："+nodiscountAmount;
			order.setWarningInfo(warningInfo);
		}
		
		order.setSchemeName(schemeName);
		//设置订单中不可打折金额
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		
		//保存美团超级代金券在线支付金额
//		preserveOAR(BigDecimal.TEN,BusinessConstant.FREE_MT_SUPER_ACC,order);
		BigDecimal chitAmount = new BigDecimal("50");
		BigDecimal count = onlineAmount.divideToIntegralValue(chitAmount);
		BigDecimal singleActualAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(chitAmount, new BigDecimal("0.88")),new BigDecimal("0.99"));
		BigDecimal totalChitAmount = DigitUtil.mutiplyDown(singleActualAmount, count);
		BigDecimal balance = onlineAmount.subtract(chitAmount.multiply(count));
		BigDecimal onlineFreeAmount = DigitUtil.mutiplyDown(chitAmount.subtract(singleActualAmount),count);
		/* 入账金额  */
		BigDecimal postAmount =totalChitAmount.add(balance);
		preserveOAR(postAmount,AccountCode.MT_SUPER,order);
		preserveOAR(onlineFreeAmount,AccountCode.FREE_ONLINE,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}
	
	public static void main(String[] args){
		BigDecimal a = new BigDecimal(98);
		BigDecimal b = new BigDecimal(50);
		System.out.println(a.divide(b,BigDecimal.ROUND_CEILING));
		System.out.println(a.divide(b).intValue());
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
		// TODO Auto-generated method stub
		
	}

}
