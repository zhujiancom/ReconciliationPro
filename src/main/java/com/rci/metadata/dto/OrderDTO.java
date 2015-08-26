/**
 * 
 */
package com.rci.metadata.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;
import com.rci.annotation.ExcelColumn;

/**
 * @author zj
 *	
 * 项目名称：BillSystem
 *
 * 类名称：OrderDTO
 *
 * 包名称：com.bill.sys.metadata
 *
 * Operate Time: 2015年4月18日 上午12:33:40
 *
 * remark (备注):
 *
 * 文件名称：OrderDTO.java
 *
 */
public class OrderDTO {
	/* 订单编号 */
	private String orderNo;
	
	/* 付款编号 */
	private String payNo;
	
	/* 桌号 */
	private String tableNo;
	
	/* 开桌时间 */
	private Timestamp openDeskTime;
	
	/* 结账时间 */
	private Timestamp checkoutTime;
	
	/* 原价*/
	private BigDecimal originAmount;
	
	/* 实收金额   */
	private BigDecimal realAmount;
	
	/* 支付方式*/
	private String paymode;

	@ColumnName("billno")
	@ExcelColumn(value="订单号",index=0)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ColumnName("payno")
	@ExcelColumn(value="付款编号",index=1)
	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	@ColumnName("opendesktime")
	@ExcelColumn(value="开桌时间",index=6)
	public Timestamp getOpenDeskTime() {
		return openDeskTime;
	}

	public void setOpenDeskTime(Timestamp openDeskTime) {
		this.openDeskTime = openDeskTime;
	}

	@ColumnName("checkouttime")
	@ExcelColumn(value="结账时间",index=7)
	public Timestamp getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Timestamp checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	@ColumnName("originamount")
	@ExcelColumn(value="原价",index=2)
	public BigDecimal getOriginAmount() {
		return originAmount;
	}

	public void setOriginAmount(BigDecimal originAmount) {
		this.originAmount = originAmount;
	}

	@ColumnName("realamount")
	@ExcelColumn(value="实收金额",index=3)
	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	@ColumnName("paymode")
	@ExcelColumn(value="支付方式",index=4)
	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	@ColumnName("tableno")
	@ExcelColumn(value="桌号",index=5)
	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}
	
	
}
