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
 * remark (备注): 进货记录
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：Purchase
 *
 * 包名称：com.rci.bean.entity.inventory
 *
 * Create Time: 2015年11月20日 下午2:50:03
 *
 */
@Entity
@Table(name="bus_tb_purchase_record")
public class PurchaseRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2822173899001432323L;
	
	private Long prid;
	
	private String ino;
	
	private String iname;
	
	private Date purDate;
	
	private BigDecimal purAmount;
	
	//进货前
	private BigDecimal preBalanceAmount;
	
	//进货后
	private BigDecimal afterBalanceAmount;
	
	public PurchaseRecord(){}
	
	public PurchaseRecord(String ino){
		this.ino = ino;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prid", nullable = false, updatable = false)
	public Long getPrid() {
		return prid;
	}

	@Column(name="ino")
	public String getIno() {
		return ino;
	}

	@Column(name="iname")
	public String getIname() {
		return iname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="pur_date")
	public Date getPurDate() {
		return purDate;
	}

	@Column(name="pur_amount")
	public BigDecimal getPurAmount() {
		return purAmount;
	}

	public void setPrid(Long prid) {
		this.prid = prid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setPurDate(Date purDate) {
		this.purDate = purDate;
	}

	public void setPurAmount(BigDecimal purAmount) {
		this.purAmount = purAmount;
	}

	@Column(name="pre_balace_amount")
	public BigDecimal getPreBalanceAmount() {
		return preBalanceAmount;
	}

	@Column(name="after_balace_amount")
	public BigDecimal getAfterBalanceAmount() {
		return afterBalanceAmount;
	}

	public void setPreBalanceAmount(BigDecimal preBalanceAmount) {
		this.preBalanceAmount = preBalanceAmount;
	}

	public void setAfterBalanceAmount(BigDecimal afterBalanceAmount) {
		this.afterBalanceAmount = afterBalanceAmount;
	}

	@Override
	@Transient
	public Serializable getId() {
		return prid;
	}

}
