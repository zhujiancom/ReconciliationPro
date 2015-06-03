package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.Scheme;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
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
		BigDecimal actualAmount = totalAmount;
		if(freeAmount != null){
			actualAmount = totalAmount.subtract(freeAmount);
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				Scheme activeScheme = null;
				List<Scheme> schemes = schemeService.getSchemes(Vendor.MTWM, freeAmount, orderDate);
				if(CollectionUtils.isEmpty(schemes)){
					BigDecimal redundant = freeAmount.remainder(new BigDecimal("18"));
					BigDecimal price = freeAmount.subtract(redundant);
					Scheme _scheme = schemeService.getScheme(Vendor.MTWM, price, orderDate);
					if(_scheme != null){
						activeScheme = _scheme;
					}else{
						logger.warn(order.getPayNo()+"---[美团外卖 活动补贴] 没有找到匹配的Scheme -----");
					}
				}
				for(Scheme scheme:schemes){
					if(freeAmount.compareTo(new BigDecimal("15")) == 0 && scheme.getPrice().intValue() == 15){
						//新用户
						if(activeScheme == null){
							activeScheme = scheme;
							break;
						}
					}
					int c = totalAmount.divideToIntegralValue(scheme.getFloorAmount()).intValue();
					if(c == 1){
						if(activeScheme == null){
							activeScheme = scheme;
							break;
						}
					}
					if(c == 0){
						BigDecimal redundant = freeAmount.remainder(new BigDecimal("18"));
						BigDecimal price = freeAmount.subtract(redundant);
						Scheme _scheme = schemeService.getScheme(Vendor.MTWM, price, orderDate);
						if(_scheme != null){
							activeScheme = _scheme;
						}else{
							redundant = freeAmount.remainder(new BigDecimal("8"));
							price = freeAmount.subtract(redundant);
							_scheme = schemeService.getScheme(Vendor.MTWM, price, orderDate);
							if(_scheme != null){
								activeScheme = _scheme;
							}else{
								logger.warn(order.getPayNo()+"---[美团外卖 活动补贴] 没有找到匹配的Scheme -----");
							}
						}
						break;
					}
					if(totalAmount.compareTo(new BigDecimal("100")) > 0 && c > 1){
						activeScheme = scheme;
						break;
					}
				}
				if(activeScheme != null){
					freeAmount = freeAmount.subtract(activeScheme.getSpread());
					schemeName = schemeName+","+activeScheme.getName();
					Map<String,BigDecimal> freeMap = chain.getFreeMap();
					if(freeMap.get(order.getPayNo()) == null){
						freeMap.put(order.getPayNo(), freeAmount);
					}
					//保存美团外卖补贴金额
					preserveOAR(freeAmount,BusinessConstant.FREE_MTWM_ACC,order);
				}
			}catch(Exception e){
				e.printStackTrace();
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
