package com.rci.ui.swing.vos;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.rci.enums.CommonEnums.YOrN;

public class DishVO {
	private Long did;
	/* 菜品编号 */
	private String dishNo;
	
	/* 菜品名称   */
	private String dishName;
	
	private BigDecimal dishPrice;
	
	/* 是否受库存管理  */
	private YOrN stockFlag;
	
	public DishVO(){}
	
	public DishVO(String dishNo,String dishName){
		this.dishNo = dishNo;
		this.dishName = dishName;
	}

	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public YOrN getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(YOrN stockFlag) {
		this.stockFlag = stockFlag;
	}

	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	@Override
	public boolean equals(Object paramObject) {
		boolean isEqual = false;
		if(paramObject != null && DishVO.class.isAssignableFrom(paramObject.getClass())){
			DishVO obj = (DishVO) paramObject;
			isEqual = new EqualsBuilder().append(this.dishNo, obj.dishNo).isEquals();
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37).append(this.dishNo).toHashCode();
	}

	@Override
	public String toString() {
		return dishNo+"-"+dishName;
	}
	
}
