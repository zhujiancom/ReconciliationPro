package com.rci.service.calculatecenter;

import java.math.BigDecimal;
import java.util.Map;

import com.rci.bean.entity.Order;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.tools.StringUtils;

public class DefaultOrderParameterValue implements ParameterValue {
	private Order order;
	
	private Map<PaymodeCode,BigDecimal> paymodeMapping;
	
	private String schemeName;
	
	private String warningInfo;
	
	public DefaultOrderParameterValue(Order order){
		this.order = order;
	}

	@Override
	public Object getSourceData() {
		return order;
	}

	@Override
	public String getPayNo() {
		return order.getPayNo();
	}

	@Override
	public Map<PaymodeCode, BigDecimal> getPayInfo() {
		paymodeMapping = order.getPaymodeMapping();
		return paymodeMapping;
	}

	@Override
	public void addPayInfo(PaymodeCode code, BigDecimal amount) {
		if(paymodeMapping == null){
			paymodeMapping = order.getPaymodeMapping();
		}
		paymodeMapping.put(code, amount);
	}

	@Override
	public BigDecimal getAmount(PaymodeCode code) {
		return paymodeMapping.get(code);
	}

	@Override
	public String joinSchemeName(String... schemeNames) {
		if(schemeNames == null){
			return StringUtils.trimToEmpty(this.schemeName);
		}
		if(!StringUtils.hasText(this.schemeName)){
			this.schemeName = StringUtils.join(BusinessConstant.COMMA,schemeNames);
		}else{
			String[] schemeArray = StringUtils.split(this.schemeName, BusinessConstant.COMMA);
			this.schemeName = StringUtils.join(schemeArray, BusinessConstant.COMMA,schemeNames);
		}
		return this.schemeName;
	}

	@Override
	public String joinWarningInfo(String... warningInfos) {
		if(warningInfo == null){
			return StringUtils.trimToEmpty(this.warningInfo);
		}
		if(!StringUtils.hasText(this.warningInfo)){
			this.warningInfo = StringUtils.join(BusinessConstant.COMMA,warningInfos);
		}else{
			String[] schemeArray = StringUtils.split(this.warningInfo, BusinessConstant.COMMA);
			this.warningInfo = StringUtils.join(schemeArray, BusinessConstant.COMMA,warningInfos);
		}
		return this.warningInfo;
	}
}
