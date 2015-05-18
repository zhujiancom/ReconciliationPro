package com.rci.service.filter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.Scheme;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ELEFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(ELEFilter.class);

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_ELE);
	}

	@Override
	public void generateScheme(Order order, FilterChain chain) {
			BigDecimal onlineAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_ELE);
			BigDecimal freeAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_FREE);
			BigDecimal actualAmount = BigDecimal.ZERO;
			
			String schemeName = order.getSchemeName();
			if(StringUtils.hasText(schemeName)){
				schemeName = schemeName+","+"饿了么在线支付"+onlineAmount+"元";
			}else{
				schemeName = "饿了么在线支付"+onlineAmount+"元";
			}
			
			
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
			if(freeAmount != null){
				actualAmount = actualAmount.subtract(freeAmount);
//				schemeName = schemeName+","+"饿了么活动补贴"+freeAmount+"元";
//				Map<String,BigDecimal> freeMap = chain.getFreeMap();
//				if(freeMap.get(order.getPayNo()) == null){
//					freeMap.put(order.getPayNo(), freeAmount);
//				}
//				//保存饿了么补贴金额
//				preserveOAR(freeAmount,BusinessConstant.FREE_ELE_ACC,order);
				List<Scheme> schemes = schemeService.getSchemes(BusinessConstant.PAYMODE_ELE);
				for(Scheme scheme:schemes){
					if(scheme.isFinished()){
						continue;
					}
					if(freeAmount.remainder(scheme.getPrice()).compareTo(BigDecimal.ZERO) == 0){
						String day = order.getDay();
						try {
							Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
							if(orderDate.after(scheme.getStartDate()) && orderDate.before(scheme.getEndDate())){
								freeAmount = scheme.getPostPrice();
								schemeName = schemeName+","+scheme.getName();
								Map<String,BigDecimal> freeMap = chain.getFreeMap();
								if(freeMap.get(order.getPayNo()) == null){
									freeMap.put(order.getPayNo(), freeAmount);
								}
								//保存饿了么补贴金额
								preserveOAR(freeAmount,BusinessConstant.FREE_ELE_ACC,order);
								break;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if(actualAmount.compareTo(onlineAmount) != 0){
				order.setUnusual(YOrN.Y);
				logger.warn("--- 【"+order.getPayNo()+"】[饿了么在线支付异常] ---， 在线支付金额："+onlineAmount+" , 实际支付金额： "+actualAmount);
			}
			order.setSchemeName(schemeName);
			//保存饿了么在线支付金额
			preserveOAR(actualAmount,BusinessConstant.ELE_ACC,order);
	}


	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
