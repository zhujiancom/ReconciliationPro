package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

public class ExpressRateVO implements Comparable<ExpressRateVO>{
	private Date date;
	
	private Integer expressNum;
	
	private Integer orderNum;
	
	private BigDecimal expressRate;
	
	public ExpressRateVO(){}
	
	public ExpressRateVO(Date date,Integer expressNum,Integer orderNum){
		this.date = date;
		this.expressNum = expressNum;
		this.orderNum = orderNum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(Integer expressNum) {
		this.expressNum = expressNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getExpressRate() {
		return expressRate;
	}

	public void setExpressRate(BigDecimal expressRate) {
		this.expressRate = expressRate;
	}

	@Override
	public int compareTo(ExpressRateVO o) {
		if(o.getDate().before(this.getDate())){
			return -1;
		}
		return 0;
	}
}
