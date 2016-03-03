/**
 * 
 */
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
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.OrderFramework;

/**
 * @author zj
 *	
 * 项目名称：BillSystem
 *
 * 类名称：PostOrderAccount
 *
 * 包名称：com.bill.sys.bean.entity
 *
 * Create Time: 2015�?�?2�?下午11:46:38
 *
 * remark (备注):
 *
 */
@Entity
@Table(name="bus_tb_order_account_ref")
public class OrderAccountRef extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8158259938553678864L;
	
	
	private Long refId;
	
	private Long accId;
	
//	private AccountCode accNo;
	
	private String accNo;
	
	private BigDecimal realAmount;
	
	private String orderNo;
	
	private Date postTime;
	
	private OrderFramework framework;
	
	//主账户
	private AccountCode mainAccount;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="refid", nullable=false,updatable=false)
	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	@Column(name="accid")
	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

//	@Enumerated(EnumType.STRING)
//	@Column(name="accno")
//	public AccountCode getAccNo() {
//		return accNo;
//	}
//
//	public void setAccNo(AccountCode accNo) {
//		this.accNo = accNo;
//	}
	
	@Column(name="accno")
	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
	

	@Column(name="real_amount")
	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	@Column(name="order_no")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="post_time")
	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="framework")
	public OrderFramework getFramework() {
		return framework;
	}

	public void setFramework(OrderFramework framework) {
		this.framework = framework;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="main_account")
	public AccountCode getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(AccountCode mainAccount) {
		this.mainAccount = mainAccount;
	}

	/* 
	 * @see org.zj.framework.core.entity.BaseEntity#getId()
	 */
	@Override
	@Transient
	public Serializable getId() {
		return refId;
	}

	/* 
	 * @see org.zj.framework.core.entity.BaseEntity#getVersion()
	 */

}
