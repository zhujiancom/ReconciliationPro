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

@Entity
@Table(name="bus_tb_statistic_record")
public class StatisticRecord extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8970946358947904969L;

	private Long srid;
	
	/* 日期 */
	private Date date;
	
	/* 原料成本 */
	private BigDecimal costAmount = BigDecimal.ZERO;
	
	/* 营业额 */
	private BigDecimal turnoverAmount = BigDecimal.ZERO;
	
	/* 原料占比 */
	private BigDecimal costRate = BigDecimal.ZERO;
	
	/* 外送单数 */
	private Integer expressOrders = 0;
	
	/* 当日总订单数 */
	private Integer totalOrders = 0;
	
	/* 外送率 */
	private BigDecimal expressRate = BigDecimal.ZERO;
	
	private Date savepoint;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "srid", nullable = false, updatable = false)
	public Long getSrid() {
		return srid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="sr_date")
	public Date getDate() {
		return date;
	}

	@Column(name="cost_amount")
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	@Column(name="turnover_amount")
	public BigDecimal getTurnoverAmount() {
		return turnoverAmount;
	}

	@Column(name="cost_rate")
	public BigDecimal getCostRate() {
		return costRate;
	}

	@Column(name="express_orders_count")
	public Integer getExpressOrders() {
		return expressOrders;
	}

	@Column(name="total_orders_count")
	public Integer getTotalOrders() {
		return totalOrders;
	}

	@Column(name="express_rate")
	public BigDecimal getExpressRate() {
		return expressRate;
	}

	public void setSrid(Long srid) {
		this.srid = srid;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public void setTurnoverAmount(BigDecimal turnoverAmount) {
		this.turnoverAmount = turnoverAmount;
	}

	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}

	public void setExpressOrders(Integer expressOrders) {
		this.expressOrders = expressOrders;
	}

	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}

	public void setExpressRate(BigDecimal expressRate) {
		this.expressRate = expressRate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="savepoint")
	public Date getSavepoint() {
		return savepoint;
	}

	public void setSavepoint(Date savepoint) {
		this.savepoint = savepoint;
	}

	@Override
	@Transient
	public Serializable getId() {
		return srid;
	}

}
