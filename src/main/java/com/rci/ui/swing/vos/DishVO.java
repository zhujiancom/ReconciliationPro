package com.rci.ui.swing.vos;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.rci.enums.CommonEnums;
import com.rci.enums.CommonEnums.YOrN;

public class DishVO {
	private Long did;
	/* 菜品编号 */
	private String dishNo;
	
	/* 菜品名称   */
	private String dishName;
	
	private BigDecimal dishPrice;
	
	private YOrN stopFlag;
	
	private YOrN suitFlag;
	
	private YOrN discountFlag;
	
	private YOrN statisticFlag;
	
	/* 是否是餐盒费 等 */
	private YOrN boxFeeFlag;
	
	/* 是否是外送费 */
	private YOrN takeoutFeeFlag;
	
	private BigDecimal cost;
	
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

	public YOrN getStopFlag() {
		return stopFlag;
	}

	public YOrN getSuitFlag() {
		return suitFlag;
	}

	public CommonEnums.YOrN getDiscountFlag() {
		return discountFlag;
	}

	public void setStopFlag(YOrN stopFlag) {
		this.stopFlag = stopFlag;
	}

	public void setSuitFlag(YOrN suitFlag) {
		this.suitFlag = suitFlag;
	}

	public void setDiscountFlag(CommonEnums.YOrN discountFlag) {
		this.discountFlag = discountFlag;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public YOrN getStatisticFlag() {
		return statisticFlag;
	}

	public void setStatisticFlag(YOrN statisticFlag) {
		this.statisticFlag = statisticFlag;
	}

	public YOrN getBoxFeeFlag() {
		return boxFeeFlag;
	}

	public YOrN getTakeoutFeeFlag() {
		return takeoutFeeFlag;
	}

	public void setBoxFeeFlag(YOrN boxFeeFlag) {
		this.boxFeeFlag = boxFeeFlag;
	}

	public void setTakeoutFeeFlag(YOrN takeoutFeeFlag) {
		this.takeoutFeeFlag = takeoutFeeFlag;
	}

	@Override
	public String toString() {
		return dishNo+"-"+dishName;
	}
	
}
