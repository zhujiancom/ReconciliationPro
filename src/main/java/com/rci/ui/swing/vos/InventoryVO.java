package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class InventoryVO {
	private Long iid;
	
	private String ino;
	
	private String iname;
	
	private BigDecimal totalAmount;
	
	private BigDecimal balanceAmount;
	
	private BigDecimal consumeAmount;
	
	private String relatedDishes;

	public Long getIid() {
		return iid;
	}

	public String getIno() {
		return ino;
	}

	public String getIname() {
		return iname;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public String getRelatedDishes() {
		return relatedDishes;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public void setRelatedDishes(String relatedDishes) {
		this.relatedDishes = relatedDishes;
	}
}
