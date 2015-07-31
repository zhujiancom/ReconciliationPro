package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

public class TurnoverVO {
	private Date date;
	
	private BigDecimal cashMachineAmount;
	
	private BigDecimal mtAmount;
	
	private BigDecimal mtSuperAmount;
	
	private BigDecimal dptgAmount;
	
	private BigDecimal dpshAmount;
	
	private BigDecimal eleAmount;
	
	private BigDecimal elebtAmount;
	
	private BigDecimal tddAmount;
	
	private BigDecimal posAmount;
	
	/* 堂食优惠  */
	private BigDecimal tsFreeAmount;
	
	/* 在线订单优惠 */
	private BigDecimal onlineFreeAmount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getCashMachineAmount() {
		return cashMachineAmount;
	}

	public void setCashMachineAmount(BigDecimal cashMachineAmount) {
		this.cashMachineAmount = cashMachineAmount;
	}

	public BigDecimal getMtAmount() {
		return mtAmount;
	}

	public void setMtAmount(BigDecimal mtAmount) {
		this.mtAmount = mtAmount;
	}

	public BigDecimal getMtSuperAmount() {
		return mtSuperAmount;
	}

	public void setMtSuperAmount(BigDecimal mtSuperAmount) {
		this.mtSuperAmount = mtSuperAmount;
	}

	public BigDecimal getDptgAmount() {
		return dptgAmount;
	}

	public void setDptgAmount(BigDecimal dptgAmount) {
		this.dptgAmount = dptgAmount;
	}

	public BigDecimal getDpshAmount() {
		return dpshAmount;
	}

	public void setDpshAmount(BigDecimal dpshAmount) {
		this.dpshAmount = dpshAmount;
	}

	public BigDecimal getEleAmount() {
		return eleAmount;
	}

	public void setEleAmount(BigDecimal eleAmount) {
		this.eleAmount = eleAmount;
	}

	public BigDecimal getElebtAmount() {
		return elebtAmount;
	}

	public void setElebtAmount(BigDecimal elebtAmount) {
		this.elebtAmount = elebtAmount;
	}

	public BigDecimal getTddAmount() {
		return tddAmount;
	}

	public void setTddAmount(BigDecimal tddAmount) {
		this.tddAmount = tddAmount;
	}

	public BigDecimal getPosAmount() {
		return posAmount;
	}

	public void setPosAmount(BigDecimal posAmount) {
		this.posAmount = posAmount;
	}

	public BigDecimal getTsFreeAmount() {
		return tsFreeAmount;
	}

	public void setTsFreeAmount(BigDecimal tsFreeAmount) {
		this.tsFreeAmount = tsFreeAmount;
	}

	public BigDecimal getOnlineFreeAmount() {
		return onlineFreeAmount;
	}

	public void setOnlineFreeAmount(BigDecimal onlineFreeAmount) {
		this.onlineFreeAmount = onlineFreeAmount;
	}

}
