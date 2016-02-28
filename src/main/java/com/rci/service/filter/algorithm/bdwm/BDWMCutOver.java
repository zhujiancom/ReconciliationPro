package com.rci.service.filter.algorithm.bdwm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.filter.FilterChain;
import com.rci.service.filter.algorithm.AbstractAlgorithmCallback;
import com.rci.service.filter.algorithm.ParameterValue;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

@Component("bdwmCutOver")
public class BDWMCutOver extends AbstractAlgorithmCallback {

	@Override
	protected void setSchemeDescription(Order order, BigDecimal onlineAmount) {
		String schemeDesc = order.getSchemeName();
		if(StringUtils.hasText(schemeDesc)){
			schemeDesc = schemeDesc+",美团外卖在线支付"+onlineAmount+"元";
		}else{
			schemeDesc = "美团外卖在线支付"+onlineAmount+"元";
		}
		order.setSchemeName(schemeDesc);
	}

	@Override
	protected void doInternalwork(ParameterValue parameter) {
		Order order = parameter.getOrder();
		BigDecimal freeAmount = parameter.getFreeAmount();
		FilterChain chain = parameter.getFilterChain();
		BigDecimal originAmount = parameter.getOriginalAmount();
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
					Scheme s = isNewCustomer(freeAmount, schemes);
					if(s == null){
						for(Scheme scheme:schemes){
							if(originAmount.compareTo(scheme.getFloorAmount())>=0 && originAmount.compareTo(scheme.getCeilAmount()) < 0){
								//满减活动
								BigDecimal price = scheme.getPrice();
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
							}else{
								logger.info(order.getPayNo()+"--- 【美团外卖活动】："+scheme.getName()+" 不匹配！");
							}
						}
					}else{
						BigDecimal allowanceAmount = s.getPostPrice();
						if(freeMap.get(order.getPayNo()) == null){
							freeMap.put(order.getPayNo(), allowanceAmount);
						}
						schemeName = schemeName+","+s.getName();
						//保存美团外卖补贴金额
						preserveOAR(allowanceAmount,AccountCode.FREE_MTWM,order);
						preserveOAR(s.getSpread(),AccountCode.FREE_ONLINE,order);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
