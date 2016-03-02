package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class TurnoverVO {
	private String displayTitle;
	
	private BigDecimal cashMachineAmount;
	
	private BigDecimal mtAmount;
	
	private BigDecimal mtSuperAmount;
	
	private BigDecimal dptgAmount;
	
	private BigDecimal dpshAmount;
	
	/* 饿了么在线支付金额 */
	private BigDecimal eleAmount;
	
	/* 饿了么补贴 */
	private BigDecimal elebtAmount;
	
	/* 饿了么刷单补贴 */
	private BigDecimal elesdAmount;
	
	/* 饿了么在线优惠 ， 餐厅补贴*/
	private BigDecimal eleOnlineFreeAmount;
	
	private BigDecimal mtwmAmount;
	
	/* 美团补贴 */
	private BigDecimal mtwmbtAmount;
	
	/* 美团外卖在线优惠，餐厅补贴 */
	private BigDecimal mtwmOnlineFreeAmount;
	
	/* 外卖超人 */
	private BigDecimal wmcrAmount;
	
	private BigDecimal wmcrbtAmount;
	
	private BigDecimal aliPayAmount;
	
	private BigDecimal posAmount;
	
	/* 堂食优惠  */
	private BigDecimal tsFreeAmount;
	
	/* 团购,pos机，淘点点订单在线优惠 */
	private BigDecimal onlineFreeAmount;
	
	private BigDecimal totalAmount;
	
	private BigDecimal bdwmAmount;
	
	private BigDecimal bdnmAmount;
	
	private BigDecimal bdnmddfAmount;
	
	public TurnoverVO(){}
	
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


	public BigDecimal getMtwmAmount() {
		return mtwmAmount == null ? BigDecimal.ZERO:mtwmAmount;
	}

	public void setMtwmAmount(BigDecimal mtwmAmount) {
		this.mtwmAmount = mtwmAmount;
	}

	public BigDecimal getMtwmbtAmount() {
		return mtwmbtAmount == null ? BigDecimal.ZERO:mtwmbtAmount;
	}

	public void setMtwmbtAmount(BigDecimal mtwmbtAmount) {
		this.mtwmbtAmount = mtwmbtAmount;
	}

	/**
	 * @return the aliPayAmount
	 */
	public BigDecimal getAliPayAmount() {
		return aliPayAmount == null ? BigDecimal.ZERO:aliPayAmount;
	}

	/**
	 * @param aliPayAmount the aliPayAmount to set
	 */
	public void setAliPayAmount(BigDecimal aliPayAmount) {
		this.aliPayAmount = aliPayAmount;
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

	public BigDecimal getEleOnlineFreeAmount() {
		return eleOnlineFreeAmount == null?BigDecimal.ZERO:eleOnlineFreeAmount;
	}

	public void setEleOnlineFreeAmount(BigDecimal eleOnlineFreeAmount) {
		this.eleOnlineFreeAmount = eleOnlineFreeAmount;
	}

	public BigDecimal getMtwmOnlineFreeAmount() {
		return mtwmOnlineFreeAmount == null ? BigDecimal.ZERO:mtwmOnlineFreeAmount;
	}

	public void setMtwmOnlineFreeAmount(BigDecimal mtwmOnlineFreeAmount) {
		this.mtwmOnlineFreeAmount = mtwmOnlineFreeAmount;
	}

	public BigDecimal getWmcrAmount() {
		return wmcrAmount == null ? BigDecimal.ZERO:wmcrAmount;
	}

	public void setWmcrAmount(BigDecimal wmcrAmount) {
		this.wmcrAmount = wmcrAmount;
	}

	public BigDecimal getWmcrbtAmount() {
		return wmcrbtAmount == null ? BigDecimal.ZERO:wmcrbtAmount;
	}

	public void setWmcrbtAmount(BigDecimal wmcrbtAmount) {
		this.wmcrbtAmount = wmcrbtAmount;
	}

	public BigDecimal getBdwmAmount() {
		return bdwmAmount == null ? BigDecimal.ZERO:bdwmAmount;
	}

	public void setBdwmAmount(BigDecimal bdwmAmount) {
		this.bdwmAmount = bdwmAmount;
	}

	public BigDecimal getBdnmAmount() {
		return bdnmAmount == null ? BigDecimal.ZERO:bdnmAmount;
	}

	public void setBdnmAmount(BigDecimal bdnmAmount) {
		this.bdnmAmount = bdnmAmount;
	}

	public BigDecimal getBdnmddfAmount() {
		return bdnmddfAmount == null ? BigDecimal.ZERO:bdnmddfAmount;
	}

	public void setBdnmddfAmount(BigDecimal bdnmddfAmount) {
		this.bdnmddfAmount = bdnmddfAmount;
	}

}
