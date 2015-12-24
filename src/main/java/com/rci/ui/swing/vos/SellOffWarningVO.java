package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.enums.BusinessEnums.State;

public class SellOffWarningVO {
	private String ino;

	private String iname;

	private Date soDate;

	private State state;
	
	private BigDecimal balanceAmount;
	
	/* 最近进货时间 */
	private Date pDate;
	
	/* 最近进货数量 */
	private BigDecimal purchaseAmount;

	public String getIno() {
		return ino;
	}

	public String getIname() {
		return iname;
	}

	public Date getSoDate() {
		return soDate;
	}

	public State getState() {
		return state;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	public void setState(State state) {
		this.state = state;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Date getpDate() {
		return pDate;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
}
