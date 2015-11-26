package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

public class SaleLogVO {
	/* 库存品种名称 */
	private String iname;
	
	private String ino;
	
	/* 销售数量 */
	private BigDecimal saleAmount;
	
	/* 销售日期 */
	private Date saleDate;
	
	public SaleLogVO(String ino,String iname,BigDecimal saleAmount){
		this.ino = ino;
		this.iname = iname;
		this.saleAmount = saleAmount;
	}

	public String getIname() {
		return iname;
	}

	public String getIno() {
		return ino;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

}
