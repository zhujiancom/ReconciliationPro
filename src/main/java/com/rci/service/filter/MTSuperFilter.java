package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
public class MTSuperFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(MTSuperFilter.class);

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MTSUPER);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		order.setFramework(OrderFramework.TS);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.MTSUPER);
		BigDecimal originAmount = order.getOriginPrice();
		/* 最大可在线支付金额,参与优惠金额 */
		BigDecimal payAmount = BigDecimal.ZERO;
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"美团超级代金券在线支付"+onlineAmount+"元";
		}else{
			schemeName = "美团超级代金券在线支付"+onlineAmount+"元";
		}
		order.setSchemeName(schemeName);
		
		List<OrderItem> items = order.getItems();
		for(OrderItem item:items){
			String dishNo = item.getDishNo();
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
			if (isNodiscount(dishNo)) {
				// 3. 饮料酒水除外
				nodiscountAmount = nodiscountAmount.add(price);
				continue;
			}
			payAmount = payAmount.add(price);
		}
		
		if(originAmount.compareTo(payAmount.add(nodiscountAmount)) != 0){
			order.setUnusual(YOrN.Y);
			logger.warn("---【"+order.getPayNo()+"】[美团超级代金券支付异常]---，  在线支付金额："+onlineAmount+" , 实际最大在线支付金额：  "+payAmount+"， 不可在线支付金额："+nodiscountAmount);
			String warningInfo = "[美团超级代金券支付异常]---   在线支付金额："+onlineAmount+" , 实际最大在线支付金额：  "+payAmount+"， 不可在线支付金额："+nodiscountAmount;
			order.setWarningInfo(warningInfo);
		}
		
		//设置订单中不可打折金额
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		
		if(onlineAmount.compareTo(originAmount) == 0){
			BigDecimal[] result = calculatePostAmount(originAmount.subtract(nodiscountAmount),order.getDay(),Vendor.MTSUPER);
			preserveOAR(result[0].add(nodiscountAmount),AccountCode.MT_SUPER,order);
			preserveOAR(result[1],AccountCode.FREE_ONLINE,order);
		}else{
			BigDecimal[] result = calculatePostAmount(onlineAmount,order.getDay(),Vendor.MTSUPER);
			preserveOAR(result[0],AccountCode.MT_SUPER,order);
			preserveOAR(result[1],AccountCode.FREE_ONLINE,order);
		}
		//保存美团超级代金券在线支付金额
//		preserveOAR(BigDecimal.TEN,BusinessConstant.FREE_MT_SUPER_ACC,order);
//		BigDecimal chitAmount = new BigDecimal("50");
//		BigDecimal count = calculateAmount.divideToIntegralValue(chitAmount);
//		BigDecimal singleActualAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(chitAmount, new BigDecimal("0.90")),new BigDecimal("0.99"));
//		BigDecimal totalChitAmount = DigitUtil.mutiplyDown(singleActualAmount, count);
//		BigDecimal balance = calculateAmount.subtract(chitAmount.multiply(count));
//		BigDecimal onlineFreeAmount = DigitUtil.mutiplyDown(chitAmount.subtract(singleActualAmount),count);
//		/* 入账金额  */
//		BigDecimal postAmount =totalChitAmount.add(balance);
//		preserveOAR(postAmount,AccountCode.MT_SUPER,order);
//		preserveOAR(onlineFreeAmount,AccountCode.FREE_ONLINE,order);
	}
//	
//	private BigDecimal[] calculatePostAmount(BigDecimal payAmount,String day){
//		try{
//			SchemeQueryDTO queryDTO = new SchemeQueryDTO();
//			queryDTO.setVendor(Vendor.MTSUPER);
//			Date queryDate = DateUtil.parseDate(day, "yyyyMMdd");
//			queryDTO.setStartDate(queryDate);
//			queryDTO.setEndDate(queryDate);
//			queryDTO.setStatus(ActivityStatus.ACTIVE);
//			List<Scheme> schemes = schemeService.getSchemes(queryDTO);
//			if(!CollectionUtils.isEmpty(schemes) && schemes.size() > 1){
//				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "活动重复");
//				for(Scheme scheme:schemes){
//					logger.error("----重复活动名称："+scheme);
//				}
//			}
//			if(CollectionUtils.isEmpty(schemes)){
//				logger.warn("----没有匹配到活动----");
//				return new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO};
//			}
//			Scheme scheme = schemes.get(0);
//			BigDecimal chitAmount = scheme.getPrice();
//			BigDecimal count = payAmount.divideToIntegralValue(chitAmount);
//			BigDecimal singleActualAmount = scheme.getPostPrice();
//			BigDecimal totalChitAmount = DigitUtil.mutiplyDown(singleActualAmount, count);
//			BigDecimal balance = payAmount.subtract(chitAmount.multiply(count));
//			BigDecimal onlineFreeAmount = DigitUtil.mutiplyDown(chitAmount.subtract(singleActualAmount),count);
//			/* 入账金额  */
//			BigDecimal postAmount =totalChitAmount.add(balance);
//			return new BigDecimal[]{postAmount,onlineFreeAmount};
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO};
////		BigDecimal chitAmount = new BigDecimal("50");
////		BigDecimal count = payAmount.divideToIntegralValue(chitAmount);
////		BigDecimal singleActualAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(chitAmount, new BigDecimal("0.90")),new BigDecimal("0.99"));
////		BigDecimal totalChitAmount = DigitUtil.mutiplyDown(singleActualAmount, count);
////		BigDecimal balance = payAmount.subtract(chitAmount.multiply(count));
////		BigDecimal onlineFreeAmount = DigitUtil.mutiplyDown(chitAmount.subtract(singleActualAmount),count);
////		/* 入账金额  */
////		BigDecimal postAmount =totalChitAmount.add(balance);
////		
////		return new BigDecimal[]{postAmount,onlineFreeAmount};
//	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}
	
	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
		// TODO Auto-generated method stub
		
	}

}
