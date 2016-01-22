package com.rci.service.filter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

/**
 * 
 * remark (备注):百度糯米到店付服务
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：DDFFilter
 *
 * 包名称：com.rci.service.filter
 *
 * Create Time: 2016年1月8日 下午4:05:33
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DDFFilter extends AbstractFilter {
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.DDF);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		order.setFramework(OrderFramework.TS);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.DDF);
		BigDecimal originAmount = order.getOriginPrice();
		BigDecimal payAmount = BigDecimal.ZERO;
		/* 不能使用闪惠支付的菜品总额 。 即酒水和配料 */
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"到店付实际支付"+onlineAmount+"元";
		}else{
			schemeName = "到店付实际支付"+onlineAmount+"元";
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
			logger.warn("---【"+order.getPayNo()+"】[到店付支付异常]---，  在线支付金额："+onlineAmount+" , 实际最大在线支付金额：  "+payAmount+"， 不可在线支付金额："+nodiscountAmount);
			String warningInfo = "[到店付支付异常]---   在线支付金额："+onlineAmount+" , 实际最大在线支付金额：  "+payAmount+"， 不可在线支付金额："+nodiscountAmount;
			order.setWarningInfo(warningInfo);
		}
		
		/*设置订单中不可打折金额*/
		if(!nodiscountAmount.equals(BigDecimal.ZERO) && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		
		try {
			Date date = DateUtil.parseDate(order.getDay(), "yyyyMMdd");
			if(order.getCheckoutTime().after(DateUtil.getTimeOfDay(date, 21, 0, 0, 0))){  //过了21:00到店付没有优惠
				preserveOAR(onlineAmount,AccountCode.BDNM,order);
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		if(onlineAmount.compareTo(originAmount) == 0){
			BigDecimal[] result = calculatePostAmount(originAmount.subtract(nodiscountAmount),order.getDay(),Vendor.DDF);
			preserveOAR(result[0].add(nodiscountAmount),AccountCode.BDNM,order);
			preserveOAR(result[1],AccountCode.FREE_ONLINE,order);
		}else{
			BigDecimal[] result = calculatePostAmount(onlineAmount,order.getDay(),Vendor.DDF);
			preserveOAR(result[0],AccountCode.BDNM,order);
			preserveOAR(result[1],AccountCode.FREE_ONLINE,order);
		}
	}
	
	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
//		if(payAmount.compareTo(onlineAmount) < 0){
//		order.setUnusual(YOrN.Y);
//		logger.warn("--- 【"+order.getPayNo()+"】[大众闪惠支付异常] ---， 在线支付金额："+onlineAmount+" , 实际最大在线支付金额： "+payAmount+" ,不可在线支付金额："+nodiscountAmount);
//		String warningInfo = "[大众闪惠支付异常]--- 在线支付金额："+onlineAmount+",实际最大在线支付金额： "+payAmount+",不可在线支付金额："+nodiscountAmount;
//		order.setWarningInfo(warningInfo);
//	}		
	}
}
