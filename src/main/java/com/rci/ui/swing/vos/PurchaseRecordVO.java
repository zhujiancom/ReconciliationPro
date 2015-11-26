package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * remark (备注):进货记录VO
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：PurchaseRecordVO
 *
 * 包名称：com.rci.ui.swing.vos
 *
 * Create Time: 2015年11月25日 下午2:15:47
 *
 */
public class PurchaseRecordVO {
	private String iname;
	
	private Date purDate;
	
	private BigDecimal purAmount;
	
	//进货前
	private BigDecimal preBalanceAmount;
	
	//进货后
	private BigDecimal afterBalanceAmount;

	public String getIname() {
		return iname;
	}

	public Date getPurDate() {
		return purDate;
	}

	public BigDecimal getPurAmount() {
		return purAmount;
	}

	public BigDecimal getPreBalanceAmount() {
		return preBalanceAmount;
	}

	public BigDecimal getAfterBalanceAmount() {
		return afterBalanceAmount;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setPurDate(Date purDate) {
		this.purDate = purDate;
	}

	public void setPurAmount(BigDecimal purAmount) {
		this.purAmount = purAmount;
	}

	public void setPreBalanceAmount(BigDecimal preBalanceAmount) {
		this.preBalanceAmount = preBalanceAmount;
	}

	public void setAfterBalanceAmount(BigDecimal afterBalanceAmount) {
		this.afterBalanceAmount = afterBalanceAmount;
	}
	
	
}
