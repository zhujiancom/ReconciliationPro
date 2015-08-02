package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
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

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_FREE);
	}

	@Override
	protected void generateScheme(Order order,FilterChain chain) {
		BigDecimal freeAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_FREE);
		BigDecimal normalAmount = freeAmount;
		Map<String,BigDecimal> freeMap = chain.getFreeMap();
		BigDecimal otherAmount = freeMap.get(order.getPayNo());
		if(otherAmount != null || order.getPaymodeMapping().containsKey(BusinessConstant.PAYMODE_ELE)){
			return;
		}
		if(otherAmount != null || order.getPaymodeMapping().containsKey(BusinessConstant.PAYMODE_MTWM)){
			return;
		}
		String schemeName = order.getSchemeName();
		if(normalAmount.compareTo(BigDecimal.ZERO) > 0){
			if(StringUtils.hasText(schemeName)){
				schemeName = schemeName+",免单"+normalAmount+"元";
			}else{
				schemeName = "免单"+normalAmount+"元";
			}
		}
		if(normalAmount.compareTo(BigDecimal.ZERO) < 0){
			logger.error("----【"+order.getPayNo()+"】[免单金额异常] --- , 免单金额超出了原价");
			order.setUnusual(YOrN.Y);
			String warningInfo = "[免单金额异常]--- 免单金额超出了原价";
			order.setWarningInfo(warningInfo);
		}
		order.setSchemeName(schemeName);
		preserveOAR(normalAmount, BusinessConstant.FREE_ACC, order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
