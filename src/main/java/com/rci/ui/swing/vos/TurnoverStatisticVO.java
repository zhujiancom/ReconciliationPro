package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class TurnoverStatisticVO {
	private String accountName;
	
	private BigDecimal amount;

	public String getAccountName() {
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
	
	
}
