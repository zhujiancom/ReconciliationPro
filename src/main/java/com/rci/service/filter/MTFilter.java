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
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MTFilter extends AbstractFilter {
	private Map<SchemeType, Integer> suitMap;
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MT);
	}

	@Override
	public void generateScheme(Order order, FilterChain chain) {
		order.setFramework(OrderFramework.TS);
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
			BigDecimal count = item.getCount();
			BigDecimal countBack = item.getCountback();
			if ("Y".equals(item.getSuitFlag())) {
				// 2.如果是套餐，则过滤
				continue;
			}
			if (count.compareTo(countBack) != 0 && "P".equals(item.getSuitFlag())) {
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
			
//				BigDecimal count = item.getCount();
//				BigDecimal countBack = item.getCountback();
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
		
		/*设置订单中不可打折金额*/
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		// 分析客户使用了哪些代金券
		BigDecimal chitAmount = order.getPaymodeMapping().get(PaymodeCode.MT);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		if(freeAmount!=null){
			bediscountAmount = bediscountAmount.subtract(freeAmount);
		}
		if(bediscountAmount.compareTo(chitAmount) < 0){
			//如果可打折金额小于代金券实际使用金额，则这单属于异常单
			order.setUnusual(YOrN.Y);
			logger.warn("---【损失单】【"+order.getPayNo()+"】[美团支付异常]---， 实际支付金额："+chitAmount+" ,可打折金额： "+bediscountAmount+", 不可打折金额： "+nodiscountAmount+". 代金券支付金额不能大于可打折金额");
			String warningInfo = "[美团支付异常]--- 实际支付金额："+chitAmount+" ,可打折金额： "+bediscountAmount+", 不可打折金额： "+nodiscountAmount+". 代金券支付金额不能大于可打折金额";
			order.setWarningInfo(warningInfo);
		}
		Map<SchemeType,SchemeWrapper> schemes = createSchemes(chitAmount, Vendor.MT,suitFlag);
		if(!CollectionUtils.isEmpty(schemes)){
			createTicketStatistic(order.getDay(), Vendor.MT, schemes);
			String schemeName = order.getSchemeName();
			BigDecimal postAmount = BigDecimal.ZERO;
			BigDecimal onlineFreeAmount = BigDecimal.ZERO;
			for(Iterator<Entry<SchemeType,SchemeWrapper>> it=schemes.entrySet().iterator();it.hasNext();){
				Entry<SchemeType,SchemeWrapper> entry = it.next();
				SchemeWrapper wrapper = entry.getValue();
				if(StringUtils.hasText(schemeName)){
					schemeName = schemeName+","+wrapper.getName();
				}else{
					schemeName = wrapper.getName();
				}
				postAmount = postAmount.add(calculateTG(wrapper.getScheme(), wrapper.getCount()));
				onlineFreeAmount = onlineFreeAmount.add(calculateOnlineFreeAmount(wrapper.getScheme(), wrapper.getCount()));
			}
			order.setSchemeName(schemeName);
			//保存账户关联信息
			preserveOAR(postAmount,AccountCode.MT,order);
			preserveOAR(onlineFreeAmount,AccountCode.FREE_ONLINE,order);
		}
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return suitMap;
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
		// TODO Auto-generated method stub
		
	}

}
