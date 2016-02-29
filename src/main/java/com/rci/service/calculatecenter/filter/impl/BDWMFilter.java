package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;
import com.rci.service.filter.algorithm.AlgorithmCallback;
import com.rci.tools.DateUtil;
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
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.BDWM);
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		BigDecimal freeAmount = value.getAmount(PaymodeCode.FREE);
		BigDecimal originalAmount = order.getOriginPrice();
		if(freeAmount != null){
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day, "yyyyMMdd");
				List<Scheme> schemes = getAppropriteSchemes(orderDate, Vendor.BDWM);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[百度外卖] 没有找到匹配的Scheme -----");
					String warningInfo = "[百度外卖活动] 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}else{
					Scheme s = getSchemeForNewCustomer(freeAmount,schemes);
					if(s == null){ //非新用户
						for(Scheme scheme:schemes){
							if(originalAmount.compareTo(scheme.getFloorAmount()) >= 0 && originalAmount.compareTo(scheme.getCeilAmount()) < 0){
								//享受满减活动
								
							}
						}
					}
				}
			} catch (ParseException pe) {
				logger.warn("日期["+day+"]转换错误", pe);
			}
		}
	}
}
