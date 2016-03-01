package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
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
 * remark (备注):美团外卖节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：MTWMFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.algorithms
 *
 * Create Time: 2016年2月29日 下午2:43:21
 *
 */
public class MTWMFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MTWM);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		order.setFramework(OrderFramework.MTWM);
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.MTWM);
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		BigDecimal originalAmount = order.getOriginPrice();
		if(freeAmount != null){
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day, "yyyyMMdd");
				List<Scheme> schemes = calculator.getAppropriteSchemes(orderDate, Vendor.MTWM);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[美团外卖] 没有找到匹配的Scheme -----");
					value.joinWarningInfo("[美团外卖活动]-没有找到匹配的Scheme");
					order.setUnusual(YOrN.Y);
				}else{
					Scheme s = calculator.getSchemeForNewCustomer(freeAmount,schemes);
					BigDecimal postAmount = BigDecimal.ZERO;//商家到账金额
					BigDecimal onlineFreeAmount = BigDecimal.ZERO; //在线优惠金额，商家补贴金额
					if(s == null){ //非新用户
						for(Scheme scheme:schemes){
							if(originalAmount.compareTo(scheme.getFloorAmount()) >= 0 && originalAmount.compareTo(scheme.getCeilAmount()) < 0){
								//享受满减活动
								BigDecimal voucherAmount = scheme.getPrice();//方案优惠金额
								BigDecimal redpacketAmount = freeAmount.subtract(voucherAmount); //使用红包金额
								postAmount = onlineAmount.add(redpacketAmount).add(scheme.getPostPrice());//商家到账金额{在线支付+红包+平台补贴}
								onlineFreeAmount = scheme.getSpread(); //在线优惠金额，商家补贴金额
								if(scheme.getCommission() != null){
									BigDecimal unAccessoryAmount = wipeoutAccessoryAmount(order.getItems()); //所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
									BigDecimal commissionAmount = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(scheme.getCommission()));
									postAmount = postAmount.subtract(commissionAmount);
									onlineFreeAmount = onlineFreeAmount.add(commissionAmount);
								}
								value.joinSchemeName(scheme.getName());
								break;
							}
						}
					}else{
						postAmount = onlineAmount.add(s.getPostPrice());//商家到账金额，{在线支付金额+平台补贴金额}
						onlineFreeAmount = s.getSpread(); //商家补贴金额
						if(s.getCommission() != null){
							BigDecimal unAccessoryAmount = wipeoutAccessoryAmount(order.getItems()); //所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
							BigDecimal commissionAmount = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(s.getCommission()));
							postAmount = postAmount.subtract(commissionAmount);
							onlineFreeAmount = onlineFreeAmount.add(commissionAmount);
						}
					}
					/* 记录百度外卖在线支付免单金额 */
					if(value.getAmount(PaymodeCode.ONLINE_FREE) == null){
						value.addPayInfo(PaymodeCode.ONLINE_FREE, onlineFreeAmount);
					}
					/* 记录百度外卖商家到账金额 */
					value.addPayInfo(PaymodeCode.MTWM, postAmount);
					value.joinSchemeName("美团外卖在线支付到账-"+postAmount+"元");
				}
			} catch (ParseException pe) {
				logger.warn("日期["+day+"]转换错误", pe);
			}
		}
	}
}
