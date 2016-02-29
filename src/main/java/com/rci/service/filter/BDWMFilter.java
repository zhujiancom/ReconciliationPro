package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.filter.algorithm.AlgorithmCallback;
import com.rci.service.filter.algorithm.AlgorithmCallbackBeanFactory;
import com.rci.service.filter.algorithm.DefaultParameterValue;
import com.rci.service.filter.algorithm.ParameterCreator;
import com.rci.service.filter.algorithm.ParameterValue;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BDWMFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(BDWMFilter.class);
	
	@Autowired
	private AlgorithmCallbackBeanFactory algorithmCallbackFactory;

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.BDWM);
	}

	@Override
	protected void generateScheme(Order order, FilterChain chain) {
//		final Order orderToUse  = order;
//		execute(new ParameterCreator() {
//			
//			@Override
//			public ParameterValue doCreateParamterFromOrder() {
//				DefaultParameterValue parameter = new DefaultParameterValue(orderToUse);
//				applyParameterValueSetting(parameter);
//				return parameter;
//			}
//		},algorithmCallbackFactory.getAlgorithmBean("bdwmCutOver"));
	}
	
	private void applyParameterValueSetting(ParameterValue parameter){
		//设置一些硬编码数据信息
		parameter.setPaymode(PaymodeCode.BDWM);
		parameter.setVendor(Vendor.BDWM);
		parameter.setAppropriateSchemes(queryAppropriateSchemes(parameter.getOrderDate(), Vendor.BDWM));
	}
	
	private List<Scheme> queryAppropriateSchemes(Date date,Vendor vendor){
		/* 查找美团外卖符合条件的活动 */
		SchemeQueryDTO queryDTO = new SchemeQueryDTO();
		queryDTO.setStatus(ActivityStatus.ACTIVE);
		queryDTO.setEndDate(date);
		queryDTO.setStartDate(date);
		queryDTO.setVendor(Vendor.MTWM);
		return schemeService.getSchemes(queryDTO);
	}
	
	protected void execute(ParameterCreator pcreator,AlgorithmCallback callback){
		ParameterValue parameter = pcreator.doCreateParamterFromOrder();
		callback.dowork(parameter);
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
