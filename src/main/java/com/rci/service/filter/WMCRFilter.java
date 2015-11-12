/**
 * 
 */
package com.rci.service.filter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.AccountCode;
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
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"外卖超人在线支付"+onlineAmount+"元";
		}else{
			schemeName = "外卖超人在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			String day = order.getDay();
			try{
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				Scheme scheme = schemeService.getScheme(Vendor.WMCR, freeAmount, orderDate);
				if(scheme != null){
					schemeName = schemeName+","+scheme.getName();
					Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
					if(freeMap.get(order.getPayNo()) == null){
						freeMap.put(order.getPayNo(), freeAmount);
					}
					//保存外卖超人补贴金额
					preserveOAR(scheme.getPostPrice(),AccountCode.FREE_WMCR,order);
					preserveOAR(scheme.getSpread(),AccountCode.FREE_ONLINE,order);
				}else{
					logger.warn(order.getPayNo()+"---[外卖超人 活动补贴] 没有找到匹配的Scheme -----");
					String warningInfo = "[外卖超人活动补贴]--- 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}
			}catch(ParseException e){
				e.printStackTrace();
			}
		}
		
		order.setSchemeName(schemeName);
		//保存外卖超人在线支付金额
		preserveOAR(onlineAmount,AccountCode.WMCR,order);
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
