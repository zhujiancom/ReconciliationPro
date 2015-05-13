package com.rci.mapping.converter;

import javax.annotation.Resource;

import org.dozer.DozerConverter;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Paymode;
import com.rci.service.IPayModeService;

@Component("PaymodeConverter")
public class PaymodeConverter extends DozerConverter<String, String> {
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	
	public PaymodeConverter() {
		super(String.class, String.class);
	}

	@Override
	public String convertTo(String source, String destination) {
		return null;
	}

	@Override
	public String convertFrom(String source, String destination) {
		Paymode paymode = paymodeService.getPayModeByNo(source);
		return paymode.getPmName();
	}

}
