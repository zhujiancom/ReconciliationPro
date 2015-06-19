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
			Map<String,BigDecimal> freeMap = chain.getFreeMap();
			actualAmount = totalAmount.subtract(freeAmount);
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				/* 查找美团外卖符合条件的活动 */
				List<Scheme> schemes = schemeService.getSchemes(Vendor.MTWM,orderDate);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[美团外卖 ] 没有找到匹配的Scheme -----");
				}else{
					for(Scheme scheme:schemes){
						if(totalAmount.compareTo(scheme.getFloorAmount())>=0 && totalAmount.compareTo(scheme.getCeilAmount()) < 0 ){
							//满减活动
							BigDecimal price = scheme.getPrice();
							BigDecimal redundant = freeAmount.remainder(price);
							BigDecimal realFreeAmount = redundant.add(scheme.getPostPrice());
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), realFreeAmount);
							}
							schemeName = ","+schemeName+scheme.getName();
							//保存美团外卖补贴金额
							preserveOAR(realFreeAmount,BusinessConstant.FREE_MTWM_ACC,order);
							break;
						}else if(freeAmount.equals(scheme.getFloorAmount()) && freeAmount.equals(scheme.getCeilAmount())){
							//新用户下单
							BigDecimal realFreeAmount = scheme.getPostPrice();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), realFreeAmount);
							}
							schemeName = ","+schemeName+scheme.getName();
							//保存美团外卖补贴金额
							preserveOAR(realFreeAmount,BusinessConstant.FREE_MTWM_ACC,order);
							break;
						}else{
							logger.debug(order.getPayNo()+"--- 【美团外卖方案】："+scheme.getName()+" 不匹配！");
						}
					}
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
