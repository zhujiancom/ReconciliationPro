package com.rci.bean.dto;

import java.util.Date;

public class PurchaseRecordQueryDTO {
	private Date purDate;
	
	private String keyword;

	public Date getPurDate() {
		return purDate;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setPurDate(Date purDate) {
		this.purDate = purDate;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
