package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.PairKey;
import com.rci.bean.SchemeWrapper;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.Scheme;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.FreeType;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.tools.DigitUtil;

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
		BigDecimal actualAmount = BigDecimal.ZERO;
		
		String schemeName = order.getSchemeName();
		schemeName = schemeName+","+"美团外卖在线支付"+onlineAmount+"元";
		
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent, new BigDecimal(100));
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
			actualAmount = actualAmount.add(price);
		}
		if(freeAmount != null){
			actualAmount = actualAmount.subtract(freeAmount);
			schemeName = schemeName+","+"美团外卖活动补贴"+freeAmount+"元";
			Map<FreeType,BigDecimal> freeMap = chain.getFreeMap();
			if(freeMap.get(FreeType.MTWM) == null){
				freeMap.put(FreeType.MTWM, freeAmount);
			}
		}
		if(actualAmount.compareTo(onlineAmount) != 0){
			order.setUnusual(YOrN.Y);
			logger.warn("[--- ELEFilter ---]: 在线支付金额："+onlineAmount+" , 实际支付金额： "+actualAmount);
		}
		order.setSchemeName(schemeName);
		//保存饿了么在线支付金额
		preserveOAR(actualAmount,BusinessConstant.MTWM_ACC,order);
		//保存饿了么补贴金额
		preserveOAR(actualAmount,BusinessConstant.FREE_MTWM_ACC,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
