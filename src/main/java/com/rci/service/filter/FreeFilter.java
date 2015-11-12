package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.StringUtils;

/**
 * 免单
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FreeFilter extends AbstractFilter {
	private String[] freeOnlinePaymodes = new String[]{"11","12","14","96"};

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.FREE);
	}

	@Override
	protected void generateScheme(Order order,FilterChain chain) {
		boolean onlineFree = false;
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		BigDecimal normalAmount = freeAmount;
		Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
		BigDecimal freeOnlineAmount = freeMap.get(order.getPayNo());
		if(freeOnlineAmount != null){
			for(String payno:freeOnlinePaymodes){
				PaymodeCode paymode = PaymodeCode.paymodeCode(payno);
				if(order.getPaymodeMapping().containsKey(paymode)){
					onlineFree = true;
					break;
				}
			}
		}
		if(onlineFree){
			return;
		}
		String schemeName = order.getSchemeName();
		if(normalAmount.compareTo(BigDecimal.ZERO) > 0){
			if(StringUtils.hasText(schemeName)){
				schemeName = schemeName+",堂食免单"+normalAmount+"元";
			}else{
				schemeName = "堂食免单"+normalAmount+"元";
			}
		}
		if(normalAmount.compareTo(BigDecimal.ZERO) < 0){
			logger.error("----【"+order.getPayNo()+"】[堂食免单金额异常] --- , 免单金额超出了原价");
			order.setUnusual(YOrN.Y);
			String warningInfo = "[堂食免单金额异常]--- 免单金额超出了原价";
			order.setWarningInfo(warningInfo);
		}
		order.setSchemeName(schemeName);
		preserveOAR(normalAmount, AccountCode.FREE, order);
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
	}

}
