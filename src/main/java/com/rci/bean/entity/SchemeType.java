package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.rci.bean.entity.base.AccessoryEntity;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;

@Entity
@Table(name = "bus_tb_scheme_type")
public class SchemeType extends AccessoryEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2887200891765194832L;
	
	private Long stid;
	
	private String typeNo;
	
	private String typeName;
	
	/* 对应的菜品 */
	private String dishNo;
	
	/* 包含饮料金额 */
	private BigDecimal beverageAmount;
	
	/* 面值金额下限 */
	private BigDecimal floorAmount;
	
	/* 面值金额上限 */
	private BigDecimal ceilAmount;
	
	/*  活动类型 （代金券，外卖活动） */
	private ActivityType activity;
	
	/* 有效/ 无效 */
	private ActivityStatus status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// MYSQL ID generator
	@Column(name = "stid", nullable = false, updatable = false)
	public Long getStid() {
		return stid;
	}

	@Column(name="typeno")
	public String getTypeNo() {
		return typeNo;
	}

	@Column(name="typename")
	public String getTypeName() {
		return typeName;
	}

	@Column(name="dishno")
	public String getDishNo() {
		return dishNo;
	}


	public void setStid(Long stid) {
		this.stid = stid;
	}


	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	@Column(name="beverage_amount")
	public BigDecimal getBeverageAmount() {
		return beverageAmount;
	}

	public void setBeverageAmount(BigDecimal beverageAmount) {
		this.beverageAmount = beverageAmount;
	}

	@Column(name="floor_amount")
	public BigDecimal getFloorAmount() {
		return floorAmount;
	}

	@Column(name="ceil_amount")
	public BigDecimal getCeilAmount() {
		return ceilAmount;
	}

	public void setFloorAmount(BigDecimal floorAmount) {
		this.floorAmount = floorAmount;
	}

	public void setCeilAmount(BigDecimal ceilAmount) {
		this.ceilAmount = ceilAmount;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="activity")
	public ActivityType getActivity() {
		return activity;
	}

	public void setActivity(ActivityType activity) {
		this.activity = activity;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="status")
	public ActivityStatus getStatus() {
		return status;
	}

	public void setStatus(ActivityStatus status) {
		this.status = status;
	}

	@Override
	@Transient
	public Serializable getId() {
		return stid;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37).append(this.typeNo).toHashCode();
	}

	@Override
	public boolean equals(Object param) {
		SchemeType obj = (SchemeType) param;
		return new EqualsBuilder().append(this.typeNo,obj.typeNo).isEquals();
	}

	@Override
	public String toString() {
		return typeName;
	}

}
