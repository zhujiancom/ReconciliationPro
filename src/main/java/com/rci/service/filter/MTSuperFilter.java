package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

public class MTSuperFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_MTSUPER);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_MTSUPER);
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		
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
			totalAmount = totalAmount.add(price);
		}
		
		if(onlineAmount != totalAmount){
			order.setUnusual(YOrN.Y);
			logger.warn("---【"+order.getPayNo()+"】[美团超级代金券支付异常]---， 代金券可支付金额："+totalAmount+" , 代金券实际支付金额： "+onlineAmount+"， 不可打折金额："+nodiscountAmount+". 可打折金额不应该小于代金券支付金额");
		}
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"美团超级代金券在线支付"+onlineAmount+"元";
		}else{
			schemeName = "美团超级代金券在线支付"+onlineAmount+"元";
		}
		order.setSchemeName(schemeName);
		//保存美团超级代金券在线支付金额
		preserveOAR(totalAmount,BusinessConstant.MT_SUPER_ACC,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
