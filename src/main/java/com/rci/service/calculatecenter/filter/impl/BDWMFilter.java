package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;
/**
 * 
 * remark (备注):百度外卖节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：BDWMFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.algorithms
 *
 * Create Time: 2016年2月29日 下午2:43:39
 *
 */
//@Component("bdwmFilter")
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BDWMFilter extends AbstractPaymodeFilter{
	private BigDecimal commission;
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.BDWM);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		order.setFramework(OrderFramework.BDWM);
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.BDWM);
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		/* 记录百度外卖商家到账金额 */
//		value.addPostAccountAmount(AccountCode.ONLINE_BDWM, onlineAmount);
//		value.joinSchemeName("百度外卖在线支付"+onlineAmount+"元");
		
		BigDecimal originalAmount = order.getOriginPrice();
		if(freeAmount != null){
			boolean matchScheme = false;
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day, "yyyyMMdd");
				List<Scheme> schemes = calculator.getAppropriteSchemes(orderDate, Vendor.BDWM);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[百度外卖] 没有找到匹配的Scheme -----");
					value.joinWarningInfo("[百度外卖活动]-没有找到匹配的Scheme");
					value.joinSchemeName("百度外卖无法解析活动方案","在线支付"+onlineAmount);
					order.setUnusual(YOrN.Y);
					value.addPostAccountAmount(AccountCode.ONLINE_BDWM, onlineAmount);
				}else{
					Scheme s = calculator.getSchemeForNewCustomer(freeAmount,schemes);
					BigDecimal postAmount = BigDecimal.ZERO;//商家到账金额
					BigDecimal onlineFreeAmount = BigDecimal.ZERO; //在线优惠金额，商家补贴金额
					BigDecimal allowanceAmount = BigDecimal.ZERO; //平台返回给商家的补贴金额
					if(s == null){ //非新用户
						for(Scheme scheme:schemes){
							if(originalAmount.compareTo(scheme.getFloorAmount()) >= 0 && originalAmount.compareTo(scheme.getCeilAmount()) < 0){
								//享受满减活动
								BigDecimal voucherAmount = scheme.getPrice();//方案优惠金额
								BigDecimal redpacketAmount = freeAmount.subtract(voucherAmount); //使用红包金额
								allowanceAmount = redpacketAmount.add(scheme.getPostPrice());//平台返回给商家的金额
								postAmount = onlineAmount;//商家到账金额
								onlineFreeAmount = scheme.getSpread(); //在线优惠金额，商家补贴金额
								if(scheme.getCommission() != null){
									BigDecimal[] amountExtract = wipeoutAccessoryAmount(order.getItems()); //所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
									BigDecimal unAccessoryAmount = amountExtract[0];
									BigDecimal commissionAmount = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(scheme.getCommission()));
									postAmount = postAmount.subtract(commissionAmount);
									onlineFreeAmount = onlineFreeAmount.add(commissionAmount);
									if(amountExtract[1].compareTo(BigDecimal.ZERO) != 0){
										postAmount = postAmount.subtract(amountExtract[1]);
										value.joinSchemeName("去除百度外卖配送费");
									}
								}
								value.joinSchemeName(scheme.getName());
								matchScheme = true;
								break;
							}
						}
					}else{
						postAmount = s.getPostPrice();//商家到账金额，平台补贴金额
						allowanceAmount = s.getPostPrice();
						onlineFreeAmount = s.getSpread(); //商家补贴金额
						if(s.getCommission() != null){
							BigDecimal[] amountExtract = wipeoutAccessoryAmount(order.getItems()); //所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
							BigDecimal unAccessoryAmount = amountExtract[0];
							BigDecimal commissionAmount = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(s.getCommission()));
							postAmount = postAmount.subtract(commissionAmount);
							onlineFreeAmount = onlineFreeAmount.add(commissionAmount);
							if(amountExtract[1].compareTo(BigDecimal.ZERO) != 0){
								postAmount = postAmount.subtract(amountExtract[1]);
								value.joinSchemeName("去除百度外卖配送费");
							}
						}
						matchScheme = true;
						value.joinSchemeName(s.getName());
					}
					if(!matchScheme){
						/* 记录百度外卖在线支付免单金额 */
//						value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
						value.joinWarningInfo("[百度外卖]--- 没有找到匹配的Scheme");
						value.joinSchemeName("百度外卖无法解析活动方案","在线支付"+onlineAmount);
						order.setUnusual(YOrN.Y);
						return;
					}
					/* 记录百度外卖在线支付免单金额 */
					value.addPayInfo(PaymodeCode.ONLINE_FREE, onlineFreeAmount);
					/* 记录百度外卖商家到账金额 */
					value.addPostAccountAmount(AccountCode.ONLINE_BDWM, postAmount);
					value.addPostAccountAmount(AccountCode.ALLOWANCE_BDWM, allowanceAmount);
					value.addPostAccountAmount(AccountCode.FREE_BDWM, onlineFreeAmount);
					value.addPostAccountAmount(AccountCode.FREE_ONLINE, onlineFreeAmount);
					
					value.joinSchemeName("百度外卖在线支付到账-"+postAmount+"元","平台补贴"+allowanceAmount+"元");
				}
			} catch (ParseException pe) {
				logger.warn("日期["+day+"]转换错误", pe);
			}
		}else{
			if(onlineAmount.compareTo(originalAmount) != 0){
				value.joinWarningInfo("百度外卖在线支付金额不正确！");
				order.setUnusual(YOrN.Y);
			}
			BigDecimal postAmount = onlineAmount;
			if(commission != null){
				BigDecimal[] amountExtract = wipeoutAccessoryAmount(order.getItems()); //所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
				BigDecimal unAccessoryAmount = amountExtract[0];
				BigDecimal commissionAmount = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(commission));
				postAmount = onlineAmount.subtract(commissionAmount);
				if(amountExtract[1].compareTo(BigDecimal.ZERO) != 0){
					postAmount = postAmount.subtract(amountExtract[1]);
					value.joinSchemeName("去除百度外卖配送费");
				}
			}
			value.joinSchemeName("百度外卖在线支付到账-"+postAmount+"元");
			value.addPostAccountAmount(AccountCode.ONLINE_BDWM, postAmount);
		}
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
}
