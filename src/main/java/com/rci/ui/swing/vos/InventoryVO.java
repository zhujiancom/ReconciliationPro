package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.List;

public class InventoryVO {
	private Long iid;
	
	private String ino;
	
	private String iname;
	
	private BigDecimal totalAmount;
	
	private BigDecimal balanceAmount;
	
	private BigDecimal consumeAmount;
	
	private String relatedDishNames;
	
	private List<DishVO> relatedDishes;
	
	/* 基数， 芝士年糕基数是40 */
	private BigDecimal cardinal;
	
	private BigDecimal cost;
	
	private BigDecimal warningLine;

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
		return totalAmount == null ? BigDecimal.ZERO:totalAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount == null ? BigDecimal.ZERO:balanceAmount;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount == null ? BigDecimal.ZERO:consumeAmount;
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

	public String getRelatedDishNames() {
		return relatedDishNames;
	}

	public List<DishVO> getRelatedDishes() {
		return relatedDishes;
	}

	public void setRelatedDishNames(String relatedDishNames) {
		this.relatedDishNames = relatedDishNames;
	}

	public void setRelatedDishes(List<DishVO> relatedDishes) {
		this.relatedDishes = relatedDishes;
	}

	public BigDecimal getCardinal() {
		return cardinal == null?BigDecimal.ONE:cardinal;
	}

	public void setCardinal(BigDecimal cardinal) {
		this.cardinal = cardinal;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getWarningLine() {
		return warningLine;
	}

	public void setWarningLine(BigDecimal warningLine) {
		this.warningLine = warningLine;
	}

	@Override
	public String toString() {
		return "InventoryVO ["+iname+" - "+ino+"]";
	}
}
