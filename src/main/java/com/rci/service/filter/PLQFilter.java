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
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

/**
 * 
 * remark (备注):派乐趣平台
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：PLQFilter
 *
 * 包名称：com.rci.service.filter
 *
 * Create Time: 2015年12月3日 下午2:46:21
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PLQFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(PLQFilter.class);
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.PLQ);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
		order.setFramework(OrderFramework.PLQ);
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.PLQ);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		BigDecimal originAmount = order.getOriginPrice();
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"派乐趣在线支付"+onlineAmount+"元";
		}else{
			schemeName = "派乐趣在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
			String day = order.getDay();
			try{
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				/* 查找派乐趣符合条件的活动 */
				SchemeQueryDTO queryDTO = new SchemeQueryDTO();
				queryDTO.setStatus(ActivityStatus.ACTIVE);
				queryDTO.setEndDate(orderDate);
				queryDTO.setStartDate(orderDate);
				queryDTO.setVendor(Vendor.PLQ);
				List<Scheme> schemes = schemeService.getSchemes(queryDTO);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[派乐趣 ] 没有找到匹配的Scheme -----");
					String warningInfo = "[派乐趣活动 ] 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}else{
					for(Scheme scheme:schemes){
						if(originAmount.compareTo(scheme.getFloorAmount())>=0 && originAmount.compareTo(scheme.getCeilAmount()) < 0){
							BigDecimal price = scheme.getPrice();
							BigDecimal redundant = freeAmount.subtract(price); //红包支付金额
							BigDecimal allowanceAmount = redundant.add(scheme.getPostPrice());
							if(freeMap.get(order.getPayNo()) == null){
								freeMap.put(order.getPayNo(), allowanceAmount);
							}
							schemeName = schemeName+","+scheme.getName();
							//派乐趣补贴金额
							preserveOAR(allowanceAmount,BusinessConstant.AccountCode_FREE_PLQ,order);
							//在线优惠金额
							preserveOAR(scheme.getSpread(),BusinessConstant.AccountCode_FREE_ONLINE,order);
						}else{
							logger.info(order.getPayNo()+"--- 【派乐趣活动】："+scheme.getName()+" 不匹配！");
						}
					}
				}
			}catch(Exception ex){
				logger.warn("派乐趣解析订单出错", ex);
			}
		}
		order.setSchemeName(schemeName);
		//保存饿了么在线支付金额
		preserveOAR(onlineAmount,BusinessConstant.AccountCode_PLQ,order);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void validation(Order order) {
		// TODO Auto-generated method stub

	}
}
