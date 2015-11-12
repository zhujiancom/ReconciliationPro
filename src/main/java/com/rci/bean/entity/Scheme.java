package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.rci.bean.entity.base.AccessoryEntity;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.tools.DateUtil;

@Entity
@Table(name = "bus_tb_scheme")
public class Scheme extends AccessoryEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1768224421633905991L;

	private Long sid;

	/* 活动或代金券名称 */
	private String name;

	/* 实际买入价格 */
	private BigDecimal postPrice;

	/* 抵用价格 */
	private BigDecimal price;

	/* 佣金抽成 */
	private BigDecimal commission;

	/* 手动差价 */
	private BigDecimal spread;

	/* 支付方式 */
	private String paymodeno;

	/* 方案单位， 代金券： 张， 套餐： 份 */
	private String unitCode;

	/* 方案类型 */
	private String typeno;
	
	/* 活动开始时间  */
	private Date startDate;
	
	/* 活动结束时间  */
	private Date endDate;
	
	/* 满减活动下限金额  */
	private BigDecimal floorAmount;
	
	/* 满减活动上限金额 */
	private BigDecimal ceilAmount;
	
	private ActivityStatus activityStatus;
	
	private Vendor vendor;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// MYSQL ID generator
	@Column(name = "sid", nullable = false, updatable = false)
	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	@Column(name = "scheme_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "post_price")
	public BigDecimal getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(BigDecimal postPrice) {
		this.postPrice = postPrice;
	}

	@Column(name = "price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "commission")
	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	@Column(name = "spread")
	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	@Column(name = "paymode_no")
	public String getPaymodeno() {
		return paymodeno;
	}

	public void setPaymodeno(String paymodeno) {
		this.paymodeno = paymodeno;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	@Column(name="typeno")
	public String getTypeno() {
		return typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the floorAmount
	 */
	@Column(name="floor_amount")
	public BigDecimal getFloorAmount() {
		return floorAmount;
	}

	/**
	 * @param floorAmount the floorAmount to set
	 */
	public void setFloorAmount(BigDecimal floorAmount) {
		this.floorAmount = floorAmount;
	}

	@Column(name="ceil_amount")
	public BigDecimal getCeilAmount() {
		return ceilAmount;
	}

	public void setCeilAmount(BigDecimal ceilAmount) {
		this.ceilAmount = ceilAmount;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="activity_status")
	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	@Column(name="vendor")
	@Enumerated(EnumType.STRING)
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@Override
	@Transient
	public Serializable getId() {
		return sid;
	}

	@Override
	public String toString() {
		return this.name+" - "+DateUtil.date2Str(this.startDate);
	}

}
