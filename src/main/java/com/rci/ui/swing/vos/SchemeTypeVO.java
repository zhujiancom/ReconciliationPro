package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;

public class SchemeTypeVO {
	private Long stid;
	
	private String typeName;
	
	private String typeNo;
	
	private String dishNo;
	
	private String dishName;
	
	private BigDecimal discountAmount;
	
	private BigDecimal realAmount;
	
	private BigDecimal floorAmount;
	
	private BigDecimal ceilAmount;
	
	private ActivityStatus status;
	
	private ActivityType activity;
	
	private Date createTime;

	private Date modifyTime;

	public Long getStid() {
		return stid;
	}

	public void setStid(Long stid) {
		this.stid = stid;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getTypeNo() {
		return typeNo;
	}

	public String getDishName() {
		return dishName;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public BigDecimal getFloorAmount() {
		return floorAmount;
	}

	public BigDecimal getCeilAmount() {
		return ceilAmount;
	}

	public ActivityStatus getStatus() {
		return status;
	}

	public ActivityType getActivity() {
		return activity;
	}

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public void setFloorAmount(BigDecimal floorAmount) {
		this.floorAmount = floorAmount;
	}

	public void setCeilAmount(BigDecimal ceilAmount) {
		this.ceilAmount = ceilAmount;
	}

	public void setStatus(ActivityStatus status) {
		this.status = status;
	}

	public void setActivity(ActivityType activity) {
		this.activity = activity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
