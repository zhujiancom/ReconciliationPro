package com.rci.ui.swing.vos;

import java.math.BigDecimal;

import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.tools.EnumUtils;

public class TurnoverStatisticVO {
	private String accountName;
	
	private BigDecimal amount;
	
	private OrderFramework framework;
	
	public String getAccountName() {
		if(framework != null){
			accountName += "-"+EnumUtils.getEnumMessage(framework);
		}
		return accountName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public OrderFramework getFramework() {
		return framework;
	}

	public void setFramework(OrderFramework framework) {
		this.framework = framework;
	}
	
	
}
