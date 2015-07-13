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

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums.StockOpType;

/**
 * 
 * remark (备注): 库存操作和消费记录日志
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：StockOpLog
 *
 * 包名称：com.rci.bean.entity
 *
 * Create Time: 2015年7月13日 上午10:39:40
 *
 */
@Entity
@Table(name="bus_tb_stock_op_log")
public class StockOpLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6360187167411976410L;
	
	private Long solid;
	
	private String sno;
	
	private String dishNo;
	
	/* 菜品名称 */
	private String dishName;
	
	/* 菜品消费时间  */
	private Date consumeTime;
	
	/* 库存变更时间 */
	private String day;
	
	/* 菜品消费数量  */
	private BigDecimal consumeAmount;
	
	/* 库存数量操作类型   */
	private StockOpType type;
	
	public StockOpLog(){}
	
	public StockOpLog(String dishNo,BigDecimal amount){
		this.dishNo = dishNo;
		this.consumeAmount = amount;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="solid", nullable=false,updatable=false)
	public Long getSolid() {
		return solid;
	}

	public void setSolid(Long solid) {
		this.solid = solid;
	}

	@Column(name="stock_no")
	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	@Column(name="dish_no")
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="consume_time")
	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	@Column(name="consume_day")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Column(name="consume_amount")
	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	public StockOpType getType() {
		return type;
	}

	public void setType(StockOpType type) {
		this.type = type;
	}

	@Override
	@Transient
	public Serializable getId() {
		return solid;
	}

}
