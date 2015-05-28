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

/**
 * 美团外卖
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MTWMFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(MTWMFilter.class);
	
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_MTWM);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_MTWM);
		BigDecimal freeAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_FREE);
//		BigDecimal allowanceAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_FREE);
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal actualAmount = BigDecimal.ZERO;
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",美团外卖在线支付"+onlineAmount+"元";
		}else{
			schemeName = "美团外卖在线支付"+onlineAmount+"元";
		}
		
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo=item.getDishNo();
			isNodiscount(dishNo);
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
			totalAmount = totalAmount.add(price);
		}
		if(freeAmount != null){
//			if(actualAmount.compareTo(new BigDecimal("100")) >= 0 || actualAmount.compareTo(new BigDecimal("50")) >= 0 || actualAmount.compareTo(new BigDecimal("15")) >= 0){
//				allowanceAmount = freeAmount.subtract(new BigDecimal("2"));
//			}
//			actualAmount = actualAmount.subtract(freeAmount);
//			schemeName = schemeName+","+"美团外卖活动补贴"+allowanceAmount+"元";
//			Map<String,BigDecimal> freeMap = chain.getFreeMap();
//			if(freeMap.get(order.getPayNo()) == null){
//				freeMap.put(order.getPayNo(), allowanceAmount);
//			}
//			//保存美团外卖补贴金额
//			preserveOAR(allowanceAmount,BusinessConstant.FREE_MTWM_ACC,order);
			actualAmount = totalAmount.subtract(freeAmount);
			List<Scheme> schemes = schemeService.getSchemes(BusinessConstant.PAYMODE_MTWM);
			for(Scheme scheme:schemes){
				String day = order.getDay();
				try {
					Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
					if(orderDate.after(scheme.getStartDate()) && orderDate.before(scheme.getEndDate())){
						if(totalAmount.compareTo(new BigDecimal("100")) >= 0 && scheme.getPrice().compareTo(new BigDecimal("30")) == 0){
							// 满100减30
							freeAmount = freeAmount.subtract(scheme.getSpread());
							schemeName = schemeName+","+scheme.getName();
							Map<String,BigDecimal> freeMap = chain.getFreeMap();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), freeAmount);
							}
							//保存美团外卖补贴金额
							preserveOAR(freeAmount,BusinessConstant.FREE_MTWM_ACC,order);
							break;
						}
						if(totalAmount.compareTo(new BigDecimal("50")) >= 0 && totalAmount.compareTo(new BigDecimal("100")) < 0 && scheme.getPrice().compareTo(new BigDecimal("20")) == 0){
							// 满50减20 
							freeAmount = freeAmount.subtract(scheme.getSpread());
							schemeName = schemeName+","+scheme.getName();
							Map<String,BigDecimal> freeMap = chain.getFreeMap();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), freeAmount);
							}
							//保存美团外卖补贴金额
							preserveOAR(freeAmount,BusinessConstant.FREE_MTWM_ACC,order);
							break;
						}
						if(totalAmount.compareTo(new BigDecimal("15")) >= 0 && totalAmount.compareTo(new BigDecimal("50")) < 0 && scheme.getPrice().compareTo(new BigDecimal("8")) == 0){
							// 满15减8
							freeAmount = freeAmount.subtract(scheme.getSpread());
							schemeName = schemeName+","+scheme.getName();
							Map<String,BigDecimal> freeMap = chain.getFreeMap();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), freeAmount);
							}
							//保存美团外卖补贴金额
							preserveOAR(freeAmount,BusinessConstant.FREE_MTWM_ACC,order);
							break;
						}
						if(freeAmount.compareTo(new BigDecimal("15")) >= 0 && totalAmount.compareTo(new BigDecimal("50")) < 0 && scheme.getPrice().compareTo(new BigDecimal("15")) == 0){
							//新用户首次下单立减15
							freeAmount = freeAmount.subtract(scheme.getSpread());
							schemeName = schemeName+","+scheme.getName();
							Map<String,BigDecimal> freeMap = chain.getFreeMap();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), freeAmount);
							}
							//保存美团外卖补贴金额
							preserveOAR(freeAmount,BusinessConstant.FREE_MTWM_ACC,order);
							break;
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		if(actualAmount.compareTo(onlineAmount) != 0){
			order.setUnusual(YOrN.Y);
			logger.warn("--- 【"+order.getPayNo()+"】[美团外卖支付异常] ---， 在线支付金额："+onlineAmount+" , 实际应支付金额： "+actualAmount);
		}
		order.setSchemeName(schemeName);
		//保存美团外卖在线支付金额
		preserveOAR(onlineAmount,BusinessConstant.MTWM_ACC,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
