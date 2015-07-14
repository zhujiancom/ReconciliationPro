package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.rci.bean.entity.base.BaseEntity;

/**
 * 
 * remark (备注): 库存管理bean
 * 
 * @author zj
 * 
 *         项目名称：ReconciliationPro
 * 
 *         类名称：Stock
 * 
 *         包名称：com.rci.bean.entity
 * 
 *         Create Time: 2015年7月13日 上午10:20:24
 * 
 */
@Entity
@Table(name = "bus_tb_stock",uniqueConstraints={@UniqueConstraint(columnNames={"sno"})})
public class Stock extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7508421118725354523L;

	private Long sid;
	
	private Long psid;
	
	private Integer level;
	
	/* 库存编号 */
	private String sno;

	/* 库存管理材料编号 */
	private String dishNo;

	/* 库存管理材料名称 */
	private String dishName;

	/* 库存总量 */
	private BigDecimal gross;

	/* 消费数量 */
	private BigDecimal consumeAmount;

	/* 余量 */
	private BigDecimal balanceAmount;
	
	public Stock(){}
	
	public Stock(String dishNo,BigDecimal gross,BigDecimal balanceAmount){
		this.dishNo = dishNo;
		this.gross = gross;
		this.balanceAmount = balanceAmount;
	}

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

	@Column(name="psid")
	public Long getPsid() {
		return psid;
	}

	public void setPsid(Long psid) {
		this.psid = psid;
	}

	@Column(name="level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name="sno",unique=true)
	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	@Column(name = "dish_no")
	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	@Column(name="dish_name")
	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	@Column(name="gross")
	public BigDecimal getGross() {
		return gross;
	}

	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}

	@Column(name="consume_amount")
	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	@Column(name="balance_amount")
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	@Override
	@Transient
	public Serializable getId() {
		return sid;
	}

}
