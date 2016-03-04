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
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;

/**
 * 
 * remark (备注): 派乐趣节点
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：PLQFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午2:51:51
 *
 */
public class PLQFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.PLQ);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		BigDecimal onlineAmount = value.getAmount(PaymodeCode.PLQ);
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		BigDecimal originalAmount = order.getOriginPrice();
		if(freeAmount != null){
			String day = order.getDay();
			boolean matchScheme = false;
			try {
				Date orderDate = DateUtil.parseDate(day, "yyyyMMdd");
				List<Scheme> schemes = calculator.getAppropriteSchemes(orderDate, Vendor.PLQ);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[派乐趣 ] 没有找到匹配的Scheme -----");
					value.joinWarningInfo("[派乐趣 ]-没有找到匹配的Scheme");
					value.joinSchemeName("派乐趣无法解析活动方案");
					order.setUnusual(YOrN.Y);
				}else{
					BigDecimal postAmount = BigDecimal.ZERO;//商家到账金额
					BigDecimal onlineFreeAmount = BigDecimal.ZERO; //在线优惠金额，商家补贴金额
					BigDecimal allowanceAmount = BigDecimal.ZERO;
					for(Scheme scheme:schemes){
						if(originalAmount.compareTo(scheme.getFloorAmount()) >= 0 && originalAmount.compareTo(scheme.getCeilAmount()) < 0){
							//享受满减活动
							BigDecimal voucherAmount = scheme.getPrice();//方案优惠金额
							BigDecimal redpacketAmount = freeAmount.subtract(voucherAmount); //使用红包金额
							allowanceAmount = redpacketAmount.add(scheme.getPostPrice()); //平台返回给商家的补贴金额
							postAmount = onlineAmount;
							onlineFreeAmount = scheme.getSpread(); //在线优惠金额，商家补贴金额
							if(scheme.getCommission() != null){
								BigDecimal unAccessoryAmount = wipeoutAccessoryAmount(order.getItems()); //所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
								BigDecimal commissionAmount = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(scheme.getCommission()));
								postAmount = postAmount.subtract(commissionAmount);
								onlineFreeAmount = onlineFreeAmount.add(commissionAmount);
							}
							matchScheme = true;
							value.joinSchemeName(scheme.getName());
							break;
						}
					}
					if(!matchScheme){
						/* 记录美团外卖在线支付免单金额 */
						value.addPayInfo(PaymodeCode.ONLINE_FREE, freeAmount);
						value.joinWarningInfo("[派乐趣]--- 没有找到匹配的Scheme");
						value.joinSchemeName("派乐趣无法解析活动方案");
						order.setUnusual(YOrN.Y);
						return;
					}
					/* 记录派乐趣在线支付免单金额 */
					value.addPayInfo(PaymodeCode.ONLINE_FREE, onlineFreeAmount);
					/* 记录派乐趣商家到账金额 */
					value.addPostAccountAmount(AccountCode.ONLINE_PLQ, postAmount);
					value.addPostAccountAmount(AccountCode.ALLOWANCE_PLQ, allowanceAmount);
					value.addPostAccountAmount(AccountCode.FREE_PLQ, onlineFreeAmount);
					value.addPostAccountAmount(AccountCode.FREE_ONLINE, onlineFreeAmount);
					
					value.joinSchemeName("派乐趣在线支付到账-"+postAmount+"元");
				}
			} catch (ParseException pe) {
				logger.warn("日期["+day+"]转换错误", pe);
			}
		}
	}
}
