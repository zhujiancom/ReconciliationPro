package com.rci.bean.entity.inventory;

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

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.CommonEnums.YOrN;

/**
 * 
 * remark (备注): 库存列表
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：Inventory
 *
 * 包名称：com.rci.bean.entity.inventory
 *
 * Create Time: 2015年11月20日 下午2:46:45
 *
 */
@Entity
@Table(name="bus_tb_inventory")
public class Inventory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8896348296084061367L;
	
	private Long iid;
	
	private String ino;
	
	private String iname;
	
	private BigDecimal totalAmount;
	
	private BigDecimal balanceAmount;
	
	private BigDecimal consumeAmount;
	
	/* 是否在库存管理 */
	private YOrN status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iid", nullable = false, updatable = false)
	public Long getIid() {
		return iid;
	}

	@Column(name="ino")
	public String getIno() {
		return ino;
	}

	@Column(name="iname")
	public String getIname() {
		return iname;
	}

	@Column(name="total_amount")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	@Column(name="balance_amount")
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	@Column(name="consume_amount")
	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="status")
	public YOrN getStatus() {
		return status;
	}

	public void setStatus(YOrN status) {
		this.status = status;
	}

	@Override
	@Transient
	public Serializable getId() {
		return iid;
	}

}
