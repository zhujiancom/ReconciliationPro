package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class DishStatisticVO {
	private String dishNo;
	
	private String dishName;
	
	private BigDecimal dishPrice;
	
	private BigDecimal saleAmount;
	
	/* 销售额 */
	private BigDecimal saleroom;

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getDishName() {
		return dishName;
	}

	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public BigDecimal getSaleroom() {
		return saleroom;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public void setSaleroom(BigDecimal saleroom) {
		this.saleroom = saleroom;
	}
}
