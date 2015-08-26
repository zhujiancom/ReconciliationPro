package com.rci.metadata.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;
import com.rci.annotation.ExcelColumn;

public class OrderItemDTO {
	/* 订单编号 */
	private String billNo;
	
	/* 付费编号*/
	private String payNo;
	
	/* 菜品编号 */
	private String dishNo;
	
	/* 套餐编号 */
	private String suitNo;
	
	/* 是否套餐 */
	private String suitFlag;
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	/* 点菜数量  */
	private BigDecimal count;
	
	/* 退菜数量 */
	private BigDecimal countback;
	
	/* 菜品单价 */
	private BigDecimal price;
	
	/* 点菜时间  */
	private Timestamp consumeTime;

	@ColumnName("billno")
	@ExcelColumn(value="订单号",index=0)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@ColumnName("payno")
	@ExcelColumn(value="付款编号",index=1)
	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	@ColumnName("dishno")
	@ExcelColumn(value="菜品编号",index=2)
	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	@ColumnName("suitno")
	@ExcelColumn(value="套餐编号",index=3)
	public String getSuitNo() {
		return suitNo;
	}

	public void setSuitNo(String suitNo) {
		this.suitNo = suitNo;
	}

	@ColumnName("suitflag")
	@ExcelColumn(value="是否套餐",index=4)
	public String getSuitFlag() {
		return suitFlag;
	}

	public void setSuitFlag(String suitFlag) {
		this.suitFlag = suitFlag;
	}

	@ColumnName("discount")
	@ExcelColumn(value="折扣信息",index=5)
	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@ColumnName("count")
	@ExcelColumn(value="销售数量",index=6)
	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	@ColumnName("countback")
	@ExcelColumn(value="退菜数量",index=7)
	public BigDecimal getCountback() {
		return countback;
	}

	public void setCountback(BigDecimal countback) {
		this.countback = countback;
	}

	@ColumnName("price")
	@ExcelColumn(value="单价",index=8)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@ColumnName("consumeTime")
	@ExcelColumn(value="消费时间",index=9)
	public Timestamp getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Timestamp consumeTime) {
		this.consumeTime = consumeTime;
	}
}
