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
import javax.persistence.Version;

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.SchemeType;

@Entity
@Table(name = "bus_tb_scheme")
public class Scheme extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1768224421633905991L;

	private Integer version;
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

	/* 方案类型 ， 由字典项中获取. Scheme -> CHIT_50,CHIT_100,SUIT_32,SUIT_68,SUIT_98,FREE,CASH*/
	private SchemeType type;
	
	/* 活动开始时间  */
	private Date startDate;
	
	/* 活动结束时间  */
	private Date endDate;
	
	private ActivityStatus activityStatus;
	
	private String vendor;

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


	/**
	 * @return the type
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="s_type")
	public SchemeType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SchemeType type) {
		this.type = type;
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

	@Enumerated(EnumType.STRING)
	@Column(name="activity_status")
	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	@Column(name="vendor")
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	@Transient
	public Serializable getId() {
		return sid;
	}

	@Override
	@Version
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
}
