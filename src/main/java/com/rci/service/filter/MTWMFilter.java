package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

/**
 * 美团外卖
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MTWMFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(MTWMFilter.class);
	
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MTWM);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		order.setFramework(OrderFramework.MTWM);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.MTWM);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		BigDecimal originAmount = order.getOriginPrice();
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",美团外卖在线支付"+onlineAmount+"元";
		}else{
			schemeName = "美团外卖在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				/* 查找美团外卖符合条件的活动 */
				SchemeQueryDTO queryDTO = new SchemeQueryDTO();
				queryDTO.setStatus(ActivityStatus.ACTIVE);
				queryDTO.setEndDate(orderDate);
				queryDTO.setStartDate(orderDate);
				queryDTO.setVendor(Vendor.MTWM);
				List<Scheme> schemes = schemeService.getSchemes(queryDTO);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[美团外卖 ] 没有找到匹配的Scheme -----");
					String warningInfo = "[美团外卖活动 ] 没有找到匹配的Scheme";
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
							preserveOAR(allowanceAmount,AccountCode.FREE_MTWM,order);
							//在线优惠金额
							preserveOAR(scheme.getSpread(),AccountCode.FREE_ONLINE,order);
							break;
						}else if(freeAmount.compareTo(scheme.getFloorAmount()) == 0 && freeAmount.compareTo(scheme.getCeilAmount()) == 0){
							//新用户下单
							BigDecimal allowanceAmount = scheme.getPostPrice();
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), allowanceAmount);
							}
							schemeName = ","+schemeName+scheme.getName();
							//保存美团外卖补贴金额
							preserveOAR(allowanceAmount,AccountCode.FREE_MTWM,order);
							preserveOAR(scheme.getSpread(),AccountCode.FREE_ONLINE,order);
							break;
						}else{
							logger.debug(order.getPayNo()+"--- 【美团外卖活动】："+scheme.getName()+" 不匹配！");
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		order.setSchemeName(schemeName);
		//保存美团外卖在线支付金额
		preserveOAR(onlineAmount,AccountCode.MTWM,order);
	}

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
