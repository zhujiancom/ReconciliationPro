package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+","+"派乐趣在线支付"+onlineAmount+"元";
		}else{
			schemeName = "派乐趣在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			String day = order.getDay();
			try{
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				Scheme scheme = schemeService.getScheme(Vendor.PLQ, freeAmount, orderDate);
				if(scheme != null){
					schemeName = schemeName+","+scheme.getName();
					Map<String,BigDecimal> freeMap = chain.getFreeOnlineMap();
					if(freeMap.get(order.getPayNo()) == null){
						freeMap.put(order.getPayNo(), freeAmount);
					}
					//保存派乐趣补贴金额
					preserveOAR(scheme.getPostPrice(),AccountCode.FREE_PLQ,order);
					preserveOAR(scheme.getSpread(),AccountCode.FREE_ONLINE,order);
				}else{
					logger.warn(order.getPayNo()+"---[派乐趣 活动补贴] 没有找到匹配的Scheme -----");
					String warningInfo = "[派乐趣活动补贴]--- 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}
			}catch(Exception ex){
				logger.warn("派乐趣解析订单出错", ex);
			}
		}
		order.setSchemeName(schemeName);
		//保存饿了么在线支付金额
		preserveOAR(onlineAmount,AccountCode.PLQ,order);
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
