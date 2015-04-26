package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.PairKey;
import com.rci.bean.SchemeWrapper;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.FreeType;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.dto.OrderItemDTO;

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
		Map<FreeType,BigDecimal> freeMap = chain.getFreeMap();
		BigDecimal freeELEAmount = freeMap.get(FreeType.ELE);
		BigDecimal freeMTWMAmount = freeMap.get(FreeType.MTWM);
		if(freeELEAmount != null){
			normalAmount = normalAmount.subtract(freeELEAmount);
		}
		if(freeMTWMAmount != null){
			normalAmount = normalAmount.subtract(freeMTWMAmount);
		}
		String schemeName = order.getSchemeName();
		if(normalAmount.compareTo(BigDecimal.ZERO) > 0){
			schemeName = schemeName+",免单"+normalAmount+"元";
		}
		if(normalAmount.compareTo(BigDecimal.ZERO) < 0){
			logger.error("----【"+order.getOrderNo()+"】:免单金额超出了原价");
			order.setUnusual(YOrN.Y);
		}
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
