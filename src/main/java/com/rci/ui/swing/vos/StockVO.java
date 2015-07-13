package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class StockVO {
	private String dishName;

	/* 库存总量 */
	private BigDecimal gross;

	/* 消费数量 */
	private BigDecimal consumeAmount;

	/* 余量 */
	private BigDecimal balanceAmount;

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public BigDecimal getGross() {
		return gross;
	}

	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
}
