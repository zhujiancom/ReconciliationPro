/**
 * 
 */
package com.rci.service.filter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AliPayFilter
 *
 * 包名称：com.rci.service.filter
 *
 * Create Time: 2015年9月3日 下午4:39:27
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WMCRFilter extends AbstractFilter{

	/* 
	 * @see com.rci.service.filter.CalculateFilter#support(java.util.Map)
	 */
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.WMCR);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		order.setFramework(OrderFramework.WMCR);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.WMCR);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		BigDecimal originAmount = order.getOriginPrice();
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"外卖超人在线支付"+onlineAmount+"元";
		}else{
			schemeName = "外卖超人在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
			String day = order.getDay();
			try{
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				/* 查找美团外卖符合条件的活动 */
				SchemeQueryDTO queryDTO = new SchemeQueryDTO();
				queryDTO.setStatus(ActivityStatus.ACTIVE);
				queryDTO.setEndDate(orderDate);
				queryDTO.setStartDate(orderDate);
				queryDTO.setVendor(Vendor.WMCR);
				List<Scheme> schemes = schemeService.getSchemes(queryDTO);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[外卖超人] 没有找到匹配的Scheme -----");
					String warningInfo = "[外卖超人 ] 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}else{
					for(Scheme scheme:schemes){
						if(originAmount.compareTo(scheme.getFloorAmount())>=0 && originAmount.compareTo(scheme.getCeilAmount()) < 0 ){
							//满减活动
							BigDecimal price = scheme.getPrice();
//							if(freeAmount.divideToIntegralValue(price).compareTo(BigDecimal.ONE) > 0){
//								continue;
//							}
//							BigDecimal redundant = freeAmount.remainder(price); //红包支付金额
							BigDecimal redundant = freeAmount.subtract(price); //红包支付金额
							BigDecimal allowanceAmount = redundant.add(scheme.getPostPrice());
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), allowanceAmount);
							}
							schemeName = schemeName+","+scheme.getName();
							//保存美团外卖补贴金额
							preserveOAR(allowanceAmount,BusinessConstant.AccountCode_FREE_WMCR,order);
							//在线优惠金额
							preserveOAR(scheme.getSpread(),BusinessConstant.AccountCode_FREE_ONLINE,order);
							break;
						}else if(freeAmount.compareTo(scheme.getFloorAmount()) == 0 && freeAmount.compareTo(scheme.getCeilAmount()) == 0){
							//新用户下单
							BigDecimal allowanceAmount = scheme.getPostPrice();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), allowanceAmount);
							}
							schemeName = schemeName+","+scheme.getName();
							//保存美团外卖补贴金额
							preserveOAR(allowanceAmount,BusinessConstant.AccountCode_FREE_WMCR,order);
							preserveOAR(scheme.getSpread(),BusinessConstant.AccountCode_FREE_ONLINE,order);
							break;
						}else{
							logger.info(order.getPayNo()+"--- 【外卖超人】："+scheme.getName()+" 不匹配！");
						}
					}
				}
			}catch(ParseException e){
				e.printStackTrace();
			}
		}
		
		order.setSchemeName(schemeName);
		//保存外卖超人在线支付金额
		preserveOAR(onlineAmount,BusinessConstant.AccountCode_WMCR,order);
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#getSuitMap()
	 */
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
	}
}
