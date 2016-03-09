package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

import com.rci.bean.SchemeWrapper;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;

/**
 * 
 * remark (备注): 百度糯米节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：BDNMFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:54:00
 *
 */
public class BDNMFilter extends AbstractPaymodeFilter {
	private Map<SchemeType, Integer> suitMap;
	
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.BDNM);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		suitMap = new HashMap<SchemeType,Integer>();
		BigDecimal nodiscountAmount = BigDecimal.ZERO; //订单中不可打折金额
		BigDecimal originalAmount = order.getOriginPrice(); //订单总金额
		//解析订单中菜品
		for (OrderItem item : order.getItems()) {
			//1. 如果是套餐内菜品，则过滤
			if("Y".equals(item.getSuitFlag())){
				continue;
			}
			BigDecimal count = item.getCount(); //点菜数量
			BigDecimal countBack = item.getCountback();//退菜数量
			String dishNo = item.getDishNo();
			//2. 如果点套餐的数量至少是一份
			if(count.compareTo(countBack) != 0 && "P".equalsIgnoreCase(item.getSuitFlag())){
				// 获取套餐类型
				SchemeType type = sdrefService.querySchemeTypeByDishno(dishNo);
				if(type == null){
					logger.warn("没找到匹配的套餐类型-菜品["+dishNo+"]");
					value.joinWarningInfo("["+value.getPayNo()+"]-没找到匹配的套餐类型-菜品["+dishNo+"]");
				}else{
					// 根据套餐类型找到各种套餐的数量并累加
					Integer suitCount = suitMap.get(type);
					Integer itemCount = count.subtract(countBack).intValue();
					if (suitCount != null) {
						suitCount += itemCount;
					} else {
						suitCount = itemCount;
					}
					suitMap.put(type, suitCount);
				}
			}
			BigDecimal singlePrice = item.getPrice(); //菜品单价
			BigDecimal singleRate = item.getDiscountRate();//菜品折扣
			BigDecimal rate = DigitUtil.precentDown(singleRate);//将菜品折扣转换成百分比：如15 --> 15%
			//算出该条菜品的总价
			BigDecimal itemPrice =  DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countBack)),rate);
			//3.判断菜品是否不可打折
			if(isUndiscount(dishNo)){
				nodiscountAmount = nodiscountAmount.add(itemPrice);
				continue;
			}
		}
		//设置不可打折金额
		if(nodiscountAmount.intValue() != 0 && order.getNodiscountAmount() == null){
			order.setNodiscountAmount(nodiscountAmount);
		}
		//在线支付金额
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.BDNM);
		BigDecimal discountAmount = originalAmount.subtract(nodiscountAmount);
		if(discountAmount.compareTo(onlineAmount) < 0){//该订单可打折金额小于实际使用券金额，则这单属于异常
			order.setUnusual(YOrN.Y);
			logger.warn("["+order.getPayNo()+"]-[百度糯米支付异常]， 实际支付金额："+onlineAmount+" , 可打折金额： "+discountAmount+", 不可打折金额： "+nodiscountAmount+". 代金券支付金额不能大于可打折金额");
			value.joinWarningInfo("[百度糯米支付异常]--- 实际支付金额："+onlineAmount+", 可打折金额："+discountAmount+", 不可打折金额： "+nodiscountAmount+". 代金券支付金额不能大于可打折金额");
		}
		//开始解析订单使用了哪些代金券
		String day = order.getDay();
		try {
			Date orderDate = DateUtil.parseDate(order.getDay(), "yyyyMMdd");
			Map<SchemeType,SchemeWrapper> schemes = calculator.getSchemesForTG(onlineAmount, Vendor.BDNM,suitMap,orderDate);
			if(!CollectionUtils.isEmpty(schemes)){ //存在匹配的代金券
				calculator.createTicketRecord(orderDate, Vendor.BDNM, schemes);//记录代金券的使用情况
				BigDecimal postAmount = BigDecimal.ZERO;
				BigDecimal freeAmount = BigDecimal.ZERO; //商家优惠金额
				for(Iterator<Entry<SchemeType,SchemeWrapper>> it=schemes.entrySet().iterator();it.hasNext();){
					Entry<SchemeType,SchemeWrapper> entry = it.next();
					SchemeWrapper wrapper = entry.getValue();
					value.joinSchemeName(wrapper.getName());
					BigDecimal[] amount = calculator.doCalculateAmountForTG(wrapper.getScheme(), wrapper.getCount());
					postAmount = postAmount.add(amount[0]);
					freeAmount = freeAmount.add(amount[1]);
				}
				value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
				
				value.addPostAccountAmount(AccountCode.ONLINE_BDNM, postAmount);
				value.addPostAccountAmount(AccountCode.FREE_BDNM, freeAmount.negate());
				value.addPostAccountAmount(AccountCode.FREE_ONLINE, freeAmount.negate());
			}else{		//不存在直接返回
				return;
			}
		} catch (ParseException pe) {
			logger.warn("日期["+day+"]转换错误", pe);
		} 
	}
}
