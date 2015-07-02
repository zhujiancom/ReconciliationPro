package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.SchemeWrapper;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

/**
 * 拉手网
 * 
 * @author zj
 * 
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LSFilter extends AbstractFilter {
	private Map<SchemeType, Integer> suitMap;
	
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_LS);
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
		/* 该订单包含的所有饮料总金额 */
//		BigDecimal beverageAmount = BigDecimal.ZERO;
		
		List<OrderItem> items = order.getItems();
		
		for (OrderItem item : items) {
			String dishNo = item.getDishNo();
			BigDecimal count = item.getCount();
			BigDecimal countBack = item.getCountback();
			if ("Y".equals(item.getSuitFlag())) {
				// 2.如果是套餐内菜品，则过滤
				continue;
			}
			if ("P".equals(item.getSuitFlag())) {
				if (!suitFlag) {
					suitFlag = true;
				}
				SchemeType type = getSuitSchemeType(dishNo);
				Integer suitCount = suitMap.get(type);
				Integer itemCount = count.subtract(countBack).intValue();
				if (suitCount != null) {
					suitCount += itemCount;
				} else {
					suitCount = itemCount;
				}
				suitMap.put(type, suitCount);
			}
			BigDecimal singlePrice = item.getPrice();
			BigDecimal singleRate = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(singleRate);
			BigDecimal originTotalAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countBack)),rate);
			
			/* 判断是否有单品折扣  */
			if((order.getSingleDiscount() == null || YOrN.N.equals(order.getSingleDiscount())) && isSingleDiscount(singleRate)){
				order.setSingleDiscount(YOrN.Y);
			}
			
			if (isNodiscount(dishNo)) {
				// 3. 饮料酒水除外
				nodiscountAmount = nodiscountAmount.add(originTotalAmount);
				continue;
			}
			bediscountAmount = bediscountAmount.add(originTotalAmount);
			
		}
		//将套餐中的饮料从不可打折金额中除去
		Integer suitACount = suitMap.get(SchemeType.SUIT_32);
		Integer suitBCount = suitMap.get(SchemeType.SUIT_68);
		if(suitACount != null && suitACount != 0){
			Integer beverageAmount = suitACount*7;
			nodiscountAmount = nodiscountAmount.subtract(new BigDecimal(beverageAmount));
			bediscountAmount = bediscountAmount.add(new BigDecimal(beverageAmount));
		}
		if(suitBCount != null && suitBCount != 0){
			Integer beverageAmount = suitBCount*16;
			nodiscountAmount = nodiscountAmount.subtract(new BigDecimal(beverageAmount));
			bediscountAmount = bediscountAmount.add(new BigDecimal(beverageAmount));
		}
		//设置订单中不可打折金额
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		// 分析客户使用了哪些代金券
		BigDecimal chitAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_LS);
		BigDecimal freeAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_FREE);
		if(freeAmount!=null){
			bediscountAmount = bediscountAmount.subtract(freeAmount);
		}
		if(bediscountAmount.compareTo(chitAmount) < 0){
			//如果可打折金额小于代金券实际使用金额，则这单属于异常单
			order.setUnusual(YOrN.Y);
			logger.warn("---【损失单】【"+order.getPayNo()+"】[拉手网支付异常]---， 实际支付金额："+chitAmount+" , 可打折金额： "+bediscountAmount+", 不可打折金额： "+nodiscountAmount+". 代金券支付金额不能大于可打折金额");
		}
//		schemes.putAll(createSchemes(chitAmount, BusinessConstant.PAYMODE_DPTG,suitFlag));
		Map<SchemeType,SchemeWrapper> schemes = createSchemes(chitAmount, BusinessConstant.PAYMODE_DPTG,suitFlag);
		if(!CollectionUtils.isEmpty(schemes)){
			createTicketStatistic(order.getDay(), Vendor.LS, schemes);
			String schemeName = order.getSchemeName();
			BigDecimal postAmount = BigDecimal.ZERO;
			for(Iterator<Entry<SchemeType,SchemeWrapper>> it=schemes.entrySet().iterator();it.hasNext();){
				Entry<SchemeType,SchemeWrapper> entry = it.next();
				SchemeWrapper wrapper = entry.getValue();
				if(StringUtils.hasText(schemeName)){
					schemeName = schemeName+","+wrapper.getName();
				}else{
					schemeName = wrapper.getName();
				}
				postAmount = postAmount.add(calculateTG(wrapper.getScheme(), wrapper.getCount()));
			}
			order.setSchemeName(schemeName);
			//保存账户关联信息
			preserveOAR(postAmount,BusinessConstant.LS_ACC,order);
		}
	}


	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return suitMap;
	}
	
}