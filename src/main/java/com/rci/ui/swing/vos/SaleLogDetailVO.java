package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * remark (备注):库存销售明细
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SaleLogDetailVO
 *
 * 包名称：com.rci.ui.swing.vos
 *
 * Create Time: 2015年11月25日 下午3:47:39
 *
 */
public class SaleLogDetailVO {
	private String payno;
	
	private String dishname;
	
	private Date checkoutTime;
	
	private BigDecimal consumeAmount;
	
	public String getPayno() {
		return payno;
	}

	public void setPayno(String payno) {
		this.payno = payno;
	}

	public String getDishname() {
		return dishname;
	}

	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setDishname(String dishname) {
		this.dishname = dishname;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
}
