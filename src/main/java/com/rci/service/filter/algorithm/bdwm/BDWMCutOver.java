package com.rci.service.filter.algorithm.bdwm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.filter.BDWMFilter;
import com.rci.service.filter.FilterChain;
import com.rci.service.filter.algorithm.AbstractAlgorithmCallback;
import com.rci.service.filter.algorithm.ParameterValue;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

@Component("bdwmCutOver")
public class BDWMCutOver extends AbstractAlgorithmCallback {
	private static final Log logger = LogFactory.getLog(BDWMCutOver.class);

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
		}
	}
}
