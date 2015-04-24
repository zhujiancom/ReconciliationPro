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
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.metadata.dto.OrderItemDTO;

/**
 * 大众点评
 * 
 * @author zj
 * 
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DPTGFilter extends AbstractFilter {
	private Map<SchemeType, Integer> suitMap;
	
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_DPTG);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		suitMap = new HashMap<SchemeType,Integer>();
		/* 标记该订单中是否有套餐 */
		boolean suitFlag = false;
		// 1. 有大众点评券
		/* 不能使用代金券的菜品总额 。 即酒水和配料 */
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		/* 正常菜品，条件满足使用代金券的总金额 */
		BigDecimal bediscountAmount = BigDecimal.ZERO;
		
		List<OrderItem> items = order.getItems();
		
		for (OrderItem item : items) {
			String dishNo = item.getDishNo();
			if ("Y".equals(item.getSuitFlag())) {
				// 2.如果是套餐，则过滤
				continue;
			}
			if ("P".equals(item.getSuitFlag())) {
				if (!suitFlag) {
					suitFlag = true;
				}
				SchemeType type = SchemeType.getType(dishNo);
				Integer count = suitMap.get(type);
				if (count != null) {
					count++;
				} else {
					count = 1;
				}
				suitMap.put(type, count);
			}
			BigDecimal originPrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countBack = item.getCountback();
			BigDecimal originTotalAmount = DigitUtil.mutiplyDown(originPrice, count.subtract(countBack));
			if (!suitFlag && isNodiscount(dishNo)) {
				// 3. 饮料酒水除外
				nodiscountAmount = nodiscountAmount.add(originTotalAmount);
				continue;
			}
			bediscountAmount = bediscountAmount.add(originTotalAmount);
			
			/* 判断是否有单品折扣  */
			BigDecimal rate = item.getDiscountRate();
			if(isSingleDiscount(rate) && (order.getSingleDiscount() == null || !order.getSingleDiscount())){
				order.setSingleDiscount(true);
			}
		}
		order.setNodiscountAmount(nodiscountAmount);
		// 分析客户使用了哪些代金券
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = order.getSchemes();
		if (CollectionUtils.isEmpty(schemes)) {
			schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
			order.setSchemes(schemes);
		}
		BigDecimal chitAmount = order.getPaymodeMapping().get(BusinessConstant.DPTG_NO);
		if(order.getFreeAmount()!=null){
			bediscountAmount = bediscountAmount.subtract(order.getFreeAmount());
		}
		if(bediscountAmount.compareTo(chitAmount) < 0){
			//如果可打折金额小于代金券实际使用金额，则这单属于异常单
			order.setUnusual(UNUSUAL);
			logger.warn("----【"+order.getOrderNo()+"】支付金额异常----， 实际支付金额："+chitAmount+" , 可打折金额： "+bediscountAmount+". 可打折金额不应该小于代金券支付金额");
		}
		schemes.putAll(createSchemes(chitAmount, BusinessConstant.DPTG_NO,suitFlag));
		//计算余额
		BigDecimal balance = chain.getBalance();
		logger.debug("DPTGFilter - balance = "+balance);
		if(balance.compareTo(chitAmount) < 0){
			logger.error("----【"+order.getOrderNo()+"】数据异常----,余额:"+balance+" , 需支付金额:"+chitAmount+". 余额不能小于需支付金额");
		}
		balance = balance.subtract(chitAmount);
		chain.setBalance(balance);
//		chain.doFilter(order, items, chain);
	}

	@Override
	public String getChit() {
		return "大众点评团购";
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return suitMap;
	}
}
