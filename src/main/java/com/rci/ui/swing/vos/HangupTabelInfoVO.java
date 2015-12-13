package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class HangupTabelInfoVO {
	private String billno;
	
	private String tableName;
	
	private BigDecimal consumAmount;
	
	private BigDecimal nodiscountAmount;
	
	private BigDecimal discountAmount;
	
	private Timestamp opendeskTime;
	
	private List<OrderItemVO> orderItems; 

	public String getBillno() {
		return billno;
	}

	public String getTableName() {
		return tableName;
	}

	public BigDecimal getConsumAmount() {
		return consumAmount;
	}

	public BigDecimal getNodiscountAmount() {
		return nodiscountAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public Timestamp getOpendeskTime() {
		return opendeskTime;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setConsumAmount(BigDecimal consumAmount) {
		this.consumAmount = consumAmount;
	}

	public void setNodiscountAmount(BigDecimal nodiscountAmount) {
		this.nodiscountAmount = nodiscountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public void setOpendeskTime(Timestamp opendeskTime) {
		this.opendeskTime = opendeskTime;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItemVO> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<OrderItemVO> orderItems) {
		this.orderItems = orderItems;
	}
}
