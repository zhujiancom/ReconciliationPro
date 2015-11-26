package com.rci.bean.entity.inventory;

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
 * remark (备注): 库存销售日志
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：InventorySellLog
 *
 * 包名称：com.rci.bean.entity.inventory
 *
 * Create Time: 2015年11月25日 下午3:21:53
 *
 */
@Entity
@Table(name="bus_tb_inventory_sell_log")
public class InventorySellLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3911026463626498915L;

	private Long logid;
	
	/* 库存编号 */
	private String ino;
	
	private String iname;
	
	/* 付款编号 */
	private String payno;
	/* 菜品编号   */
	private String dishno;
	
	/* 菜品消费时间  */
	private Date checkoutTime;
	
	/* 库存变更时间 */
	private String day;
	
	/* 菜品消费数量  */
	private BigDecimal consumeAmount;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="logid", nullable=false,updatable=false)
	public Long getLogid() {
		return logid;
	}

	@Column(name="ino")
	public String getIno() {
		return ino;
	}

	@Column(name="dishno")
	public String getDishno() {
		return dishno;
	}

	@Column(name="consume_day")
	public String getDay() {
		return day;
	}

	@Column(name="consume_amount")
	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	@Column(name="iname")
	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	@Column(name="payno")
	public String getPayno() {
		return payno;
	}

	public void setPayno(String payno) {
		this.payno = payno;
	}

	public void setDishno(String dishno) {
		this.dishno = dishno;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="checkout_time")
	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	@Override
	@Transient
	public Serializable getId() {
		return logid;
	}

}
