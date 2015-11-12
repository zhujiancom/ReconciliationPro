package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TDDFilter extends AbstractFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.TDD);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain){
		order.setFramework(OrderFramework.TDD);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.TDD);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		validation(order); // 验证订单
		try {
			Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
			String day = order.getDay();
			Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
			/* 查找淘点点符合条件的活动 */
			SchemeQueryDTO queryDTO = new SchemeQueryDTO();
			queryDTO.setStatus(ActivityStatus.ACTIVE);
			queryDTO.setEndDate(orderDate);
			queryDTO.setStartDate(orderDate);
			queryDTO.setVendor(Vendor.TDD);
			List<Scheme> schemes = schemeService.getSchemes(queryDTO);
			BigDecimal postTotalAmount = BigDecimal.ZERO;
			if(CollectionUtils.isEmpty(schemes)){
				String schemeName = order.getSchemeName();
				if(StringUtils.hasText(schemeName)){
					schemeName = schemeName+",淘点点在线支付"+onlineAmount+"元";	
				}else{
					schemeName = "淘点点在线支付"+onlineAmount+"元";
				}
				order.setSchemeName(schemeName);
				//保存淘点点在线优惠金额
				if(freeAmount != null){
					if(freeMap.get(order.getPayNo()) == null){
						freeMap.put(order.getPayNo(), freeAmount);
					}
					preserveOAR(freeAmount,AccountCode.FREE_ONLINE,order);
				}
				//保存淘点点在线支付金额
				preserveOAR(onlineAmount,AccountCode.ALIPAY,order);
			}else{
				for(Scheme scheme:schemes){
					if(onlineAmount.compareTo(scheme.getFloorAmount())>=0 && onlineAmount.compareTo(scheme.getCeilAmount()) < 0 ){
						//满减活动
						if(freeAmount != null){
							freeAmount = freeAmount.add(scheme.getSpread());
						}else{
							freeAmount = scheme.getSpread();
						}
						postTotalAmount = onlineAmount.subtract(scheme.getSpread());
						String schemeName = order.getSchemeName();
						if(StringUtils.hasText(schemeName)){
							schemeName = schemeName+",淘点点在线支付"+postTotalAmount+"元 , "+scheme.getName();
						}else{
							schemeName = "淘点点在线支付"+postTotalAmount+"元 , "+scheme.getName();
						}
						if(freeMap.get(order.getPayNo()) == null){
							freeMap.put(order.getPayNo(), freeAmount);
						}
						order.setSchemeName(schemeName);
						//保存淘点点免单金额
						preserveOAR(freeAmount,AccountCode.FREE_ONLINE,order);
						//保存淘点点在线支付金额
						preserveOAR(postTotalAmount,AccountCode.ALIPAY,order);
						break;
					}else{
						continue;
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
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
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.TDD);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		BigDecimal originAmount = order.getOriginPrice(); //订单总金额
		BigDecimal payAmount = BigDecimal.ZERO;
		if(freeAmount == null){
			payAmount = originAmount;
		}else{
			payAmount = originAmount.subtract(freeAmount);
		}
		if(!payAmount.equals(onlineAmount)){
			order.setUnusual(YOrN.Y);
			logger.warn("--- 【"+order.getPayNo()+"】[淘点点支付异常] ---， 在线支付金额："+onlineAmount+" , 实际支付金额： "+payAmount);
			String warningInfo = "[淘点点支付异常]---  在线支付金额："+onlineAmount+" , 实际支付金额： "+payAmount;
			order.setWarningInfo(warningInfo);
		}
	}
}
