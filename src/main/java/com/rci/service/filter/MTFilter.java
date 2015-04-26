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
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MTFilter extends AbstractFilter {
	private Map<SchemeType, Integer> suitMap;
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.PAYMODE_MT);
	}

	@Override
	public void generateScheme(Order order, FilterChain chain) {
			suitMap = new HashMap<SchemeType,Integer>();
			/* 标记该订单中是否有套餐 */
			boolean suitFlag = false;
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
					SchemeType type = getSuitSchemeType(dishNo);
					Integer count = suitMap.get(type);
					if (count != null) {
						count++;
					} else {
						count = 1;
					}
					suitMap.put(type, count);
				}
				
				BigDecimal singlePrice = item.getPrice();
				BigDecimal count = item.getCount();
				BigDecimal countBack = item.getCountback();
				BigDecimal singleRate = item.getDiscountRate();
				BigDecimal rate = DigitUtil.precentDown(singleRate, new BigDecimal(100));
				BigDecimal originTotalAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countBack)),rate);

				if (isNodiscount(dishNo)) {
					// 3. 饮料酒水除外
					nodiscountAmount = nodiscountAmount.add(originTotalAmount);
					continue;
				}
				bediscountAmount = bediscountAmount.add(originTotalAmount);
				
				/* 判断是否有单品折扣  */
				if((order.getSingleDiscount() == null || YOrN.N.equals(order.getSingleDiscount())) && isSingleDiscount(rate)){
					order.setSingleDiscount(YOrN.Y);
				}
			}
			
			//将套餐中的饮料从不可打折金额中除去
			Integer suitACount = suitMap.get(SchemeType.SUIT_32);
			Integer suitBCount = suitMap.get(SchemeType.SUIT_68);
			if(suitACount != null && suitACount != 0){
				Integer beverageAmount = suitACount*5;
				nodiscountAmount = nodiscountAmount.subtract(new BigDecimal(beverageAmount));
				bediscountAmount = bediscountAmount.add(new BigDecimal(beverageAmount));
			}
			if(suitBCount != null && suitBCount != 0){
				Integer beverageAmount = suitBCount*14;
				nodiscountAmount = nodiscountAmount.subtract(new BigDecimal(beverageAmount));
				bediscountAmount = bediscountAmount.add(new BigDecimal(beverageAmount));
			}
			
			order.setNodiscountAmount(nodiscountAmount);
			// 分析客户使用了哪些代金券
			BigDecimal chitAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_MT);
			BigDecimal freeAmount = order.getPaymodeMapping().get(BusinessConstant.PAYMODE_FREE);
			if(freeAmount!=null){
				bediscountAmount = bediscountAmount.subtract(freeAmount);
			}
			if(bediscountAmount.compareTo(chitAmount) < 0){
				//如果可打折金额小于代金券实际使用金额，则这单属于异常单
				order.setUnusual(YOrN.Y);
				logger.warn("----【"+order.getOrderNo()+"】支付金额异常----， 实际支付金额："+chitAmount+" , 可打折金额： "+bediscountAmount+". 可打折金额不应该小于代金券支付金额");
			}
//			schemes.putAll(createSchemes(chitAmount, BusinessConstant.PAYMODE_MT,suitFlag));
			Map<SchemeType,SchemeWrapper> schemes = createSchemes(chitAmount, BusinessConstant.PAYMODE_MT,suitFlag);
			if(!CollectionUtils.isEmpty(schemes)){
				String schemeName = StringUtils.trimToEmpty(order.getSchemeName());
				BigDecimal postAmount = BigDecimal.ZERO;
				for(Iterator<Entry<SchemeType,SchemeWrapper>> it=schemes.entrySet().iterator();it.hasNext();){
					Entry<SchemeType,SchemeWrapper> entry = it.next();
					SchemeWrapper wrapper = entry.getValue();
					schemeName = schemeName+","+wrapper.getName();
					postAmount = postAmount.add(calculateTG(wrapper.getScheme(), wrapper.getCount()));
				}
				order.setSchemeName(schemeName);
				//保存账户关联信息
				preserveOAR(postAmount,BusinessConstant.MT_ACC,order);
			}
			//计算订余额
			BigDecimal balance = chain.getBalance();
			logger.debug("MTFilter - balance = "+balance);
			if(balance.compareTo(chitAmount) < 0){
				logger.error("----【"+order.getOrderNo()+"】数据异常----,余额:"+balance+" , 需支付金额:"+chitAmount+". 余额不能小于需支付金额");
			}
			balance = balance.subtract(chitAmount);
			chain.setBalance(balance);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return suitMap;
	}

}
