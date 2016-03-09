package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class TurnoverStatisticVO {
	private String accountName;
	
	private BigDecimal amount;
	
	private String accno;
	
	private String parentAccountno;
	
	public String getAccountName() {
//		if(framework != null){
//			accountName += "-"+EnumUtils.getEnumMessage(framework);
//		}
		return accountName;
	}

	public BigDecimal getAmount() {
		return amount == null ? BigDecimal.ZERO:amount;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getParentAccountno() {
		return parentAccountno;
	}

	public void setParentAccountno(String parentAccountno) {
		this.parentAccountno = parentAccountno;
	}
	
	public void addAmount(BigDecimal amt){
		setAmount(getAmount().add(amt));
	}
}
