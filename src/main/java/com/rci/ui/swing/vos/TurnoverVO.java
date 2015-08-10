package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class TurnoverVO {
	private String displayTitle;
	
	private BigDecimal cashMachineAmount;
	
	private BigDecimal mtAmount;
	
	private BigDecimal mtSuperAmount;
	
	private BigDecimal dptgAmount;
	
	private BigDecimal dpshAmount;
	
	private BigDecimal eleAmount;
	
	private BigDecimal elebtAmount;
	
	/* 饿了么刷单补贴 */
	private BigDecimal elesdAmount;
	
	private BigDecimal tddAmount;
	
	private BigDecimal posAmount;
	
	/* 堂食优惠  */
	private BigDecimal tsFreeAmount;
	
	/* 在线订单优惠 */
	private BigDecimal onlineFreeAmount;
	
	private BigDecimal totalAmount;
	
	public TurnoverVO(String displayTitle){
		this.displayTitle = displayTitle;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public BigDecimal getCashMachineAmount() {
		return cashMachineAmount == null ? BigDecimal.ZERO:cashMachineAmount;
	}

	public void setCashMachineAmount(BigDecimal cashMachineAmount) {
		this.cashMachineAmount = cashMachineAmount;
	}

	public BigDecimal getMtAmount() {
		return mtAmount == null ? BigDecimal.ZERO:mtAmount;
	}

	public void setMtAmount(BigDecimal mtAmount) {
		this.mtAmount = mtAmount;
	}

	public BigDecimal getMtSuperAmount() {
		return mtSuperAmount == null ? BigDecimal.ZERO:mtSuperAmount;
	}

	public void setMtSuperAmount(BigDecimal mtSuperAmount) {
		this.mtSuperAmount = mtSuperAmount;
	}

	public BigDecimal getDptgAmount() {
		return dptgAmount == null ? BigDecimal.ZERO:dptgAmount;
	}

	public void setDptgAmount(BigDecimal dptgAmount) {
		this.dptgAmount = dptgAmount;
	}

	public BigDecimal getDpshAmount() {
		return dpshAmount == null ? BigDecimal.ZERO:dpshAmount;
	}

	public void setDpshAmount(BigDecimal dpshAmount) {
		this.dpshAmount = dpshAmount;
	}

	public BigDecimal getEleAmount() {
		return eleAmount == null ? BigDecimal.ZERO:eleAmount;
	}

	public void setEleAmount(BigDecimal eleAmount) {
		this.eleAmount = eleAmount;
	}

	public BigDecimal getElebtAmount() {
		return elebtAmount == null ? BigDecimal.ZERO:elebtAmount;
	}

	public void setElebtAmount(BigDecimal elebtAmount) {
		this.elebtAmount = elebtAmount;
	}

	public BigDecimal getTddAmount() {
		return tddAmount == null ? BigDecimal.ZERO:tddAmount;
	}

	public void setTddAmount(BigDecimal tddAmount) {
		this.tddAmount = tddAmount;
	}

	public BigDecimal getPosAmount() {
		return posAmount == null ? BigDecimal.ZERO:posAmount;
	}

	public void setPosAmount(BigDecimal posAmount) {
		this.posAmount = posAmount;
	}

	public BigDecimal getTsFreeAmount() {
		return tsFreeAmount == null ? BigDecimal.ZERO:tsFreeAmount;
	}

	public void setTsFreeAmount(BigDecimal tsFreeAmount) {
		this.tsFreeAmount = tsFreeAmount;
	}

	public BigDecimal getOnlineFreeAmount() {
		return onlineFreeAmount == null ? BigDecimal.ZERO:onlineFreeAmount;
	}

	public void setOnlineFreeAmount(BigDecimal onlineFreeAmount) {
		this.onlineFreeAmount = onlineFreeAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount == null ? BigDecimal.ZERO:totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getElesdAmount() {
		return elesdAmount == null ? BigDecimal.ZERO:elesdAmount;
	}

	public void setElesdAmount(BigDecimal elesdAmount) {
		this.elesdAmount = elesdAmount;
	}

}
