package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;

/**
 * 
 * remark (备注): 饿了么刷单信息统计
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：EleSDStatistic
 *
 * 包名称：com.rci.bean.entity
 *
 * Create Time: 2015年7月9日 上午11:30:20
 *
 */
@Entity
@Table(name="bus_tb_ele_sdtj")
public class EleSDStatistic extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -53403540733618187L;

	private Long eid;
	
	/* 刷单支付金额  */
	private BigDecimal payAmount;
	
	/* 刷单数量 */
	private BigDecimal sdCount;
	
	/* 每单补贴金额  */
	private BigDecimal perAllowanceAmount;
	
	/* 当日补贴总额 */
	private BigDecimal allowanceAmount;
	
	/* 刷单日期 */
	private Date sdDate;
	
	@Transient
	@Override
	public Serializable getId() {
		return eid;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="eid", nullable=false,updatable=false)
	public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	@Column(name="online_pay_amount")
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name="sd_count")
	public BigDecimal getSdCount() {
		return sdCount;
	}

	public void setSdCount(BigDecimal sdCount) {
		this.sdCount = sdCount;
	}

	@Column(name="per_allowance_amount")
	public BigDecimal getPerAllowanceAmount() {
		return perAllowanceAmount;
	}

	public void setPerAllowanceAmount(BigDecimal perAllowanceAmount) {
		this.perAllowanceAmount = perAllowanceAmount;
	}

	@Column(name="allowance_amount")
	public BigDecimal getAllowanceAmount() {
		return allowanceAmount;
	}

	public void setAllowanceAmount(BigDecimal allowanceAmount) {
		this.allowanceAmount = allowanceAmount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="sd_date")
	public Date getSdDate() {
		return sdDate;
	}

	public void setSdDate(Date sdDate) {
		this.sdDate = sdDate;
	}

}
