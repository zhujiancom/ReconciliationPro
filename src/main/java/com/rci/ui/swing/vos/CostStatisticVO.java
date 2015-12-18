package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

public class CostStatisticVO {
	private Date date;
	
	/* 成本 */
	private BigDecimal costAmount;
	
	/* 营业额 */
	private BigDecimal turnoverAmount;
	
	private BigDecimal costRate;

	public Date getDate() {
		return date;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public BigDecimal getTurnoverAmount() {
		return turnoverAmount;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public void setTurnoverAmount(BigDecimal turnoverAmount) {
		this.turnoverAmount = turnoverAmount;
	}

	public BigDecimal getCostRate() {
		return costRate;
	}

	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}
}
