package com.rci.bean.entity.inventory;

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
import com.rci.enums.BusinessEnums.State;

/**
 * 
 * remark (备注):沽清警告记录
 * 
 * @author zj
 * 
 *         项目名称：ReconciliationPro
 * 
 *         类名称：SellOff
 * 
 *         包名称：com.rci.bean.entity.inventory
 * 
 *         Create Time: 2015年11月20日 下午2:48:01
 * 
 */
@Entity
@Table(name = "bus_tb_selloff_warning")
public class SellOffWarning extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2349286395320327517L;

	private Long swid;

	private String ino;

	private String iname;

	private Date soDate;

	private State state;
	
	private BigDecimal balanceAmount;
	
	/* 最近进货时间 */
	private Date pDate;
	
	/* 最近进货数量 */
	private BigDecimal purchaseAmount;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "swid", nullable = false, updatable = false)
	public Long getSwid() {
		return swid;
	}

	@Column(name = "ino")
	public String getIno() {
		return ino;
	}

	@Column(name = "iname")
	public String getIname() {
		return iname;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	public State getState() {
		return state;
	}

	public void setSwid(Long swid) {
		this.swid = swid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "so_date")
	public Date getSoDate() {
		return soDate;
	}

	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(name="balance_amount")
	public BigDecimal getBalanceAmount() {
		return balanceAmount == null ? BigDecimal.ZERO:balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="p_date")
	public Date getpDate() {
		return pDate;
	}

	@Column(name="p_amount")
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Override
	@Transient
	public Serializable getId() {
		// TODO Auto-generated method stub
		return swid;
	}

}
