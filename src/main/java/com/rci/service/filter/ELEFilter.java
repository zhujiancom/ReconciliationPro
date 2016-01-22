package com.rci.service.filter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ELEFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(ELEFilter.class);

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.ELE);
	}

	@Override
	public void generateScheme(Order order, FilterChain chain) {
		order.setFramework(OrderFramework.ELE);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.ELE);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"饿了么在线支付"+onlineAmount+"元";
		}else{
			schemeName = "饿了么在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			String day = order.getDay();
			try{
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				Scheme scheme = schemeService.getScheme(Vendor.ELE, freeAmount, orderDate);
				if(scheme != null){
					schemeName = schemeName+","+scheme.getName();
					Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
					if(freeMap.get(order.getPayNo()) == null){
						freeMap.put(order.getPayNo(), freeAmount);
					}
					//保存饿了么补贴金额
					preserveOAR(scheme.getPostPrice(),AccountCode.FREE_ELE,order);
					preserveOAR(scheme.getSpread(),AccountCode.FREE_ONLINE,order);
				}else{
					logger.warn(order.getPayNo()+"---[饿了么 活动补贴] 没有找到匹配的Scheme -----");
					String warningInfo = "[饿了么 活动补贴]--- 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}
			}catch(ParseException e){
				e.printStackTrace();
			}
		}
		
		order.setSchemeName(schemeName);
		//保存饿了么在线支付金额
		preserveOAR(onlineAmount,AccountCode.ELE,order);
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
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.ELE);
		BigDecimal originAmount = order.getOriginPrice();
		if(originAmount.compareTo(onlineAmount) != 0){
			order.setUnusual(YOrN.Y);
			logger.warn("--- 【"+order.getPayNo()+"】[饿了么在线支付异常] ---， 在线支付金额："+onlineAmount+" , 实际支付金额： "+originAmount);
			String warningInfo = "[饿了么在线支付异常]--- 在线支付金额："+onlineAmount+" , 应支付金额： "+originAmount;
			order.setWarningInfo(warningInfo);
		}		
	}
}
