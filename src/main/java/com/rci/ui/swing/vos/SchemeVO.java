package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.SchemeType;

public class SchemeVO {
	private String name;
	
	private String paymode;
	
	private BigDecimal postPrice;
	
	private BigDecimal price;
	
	private BigDecimal spread;
	
	private SchemeType type;
	
	private Date startDate;
	
	private Date endDate;
	
	/* 方案状态  */
	private ActivityStatus status;
	
	private String vendor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public BigDecimal getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(BigDecimal postPrice) {
		this.postPrice = postPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	public SchemeType getType() {
		return type;
	}

	public void setType(SchemeType type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ActivityStatus getStatus() {
		return status;
	}

	public void setStatus(ActivityStatus status) {
		this.status = status;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
}
