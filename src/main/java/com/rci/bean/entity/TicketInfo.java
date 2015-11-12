package com.rci.bean.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums.Vendor;

@Entity
@Table(name = "bus_tb_ticket_Information")
public class TicketInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9075054823739538964L;

	private Long tid;
	
	/* 代金券类型 */
	private SchemeType schemeType;
	
	/* 验证数量 */
	private Integer valifyCount;
	
	/* 代金券名称 */
	private String name;
	
	/* 代金券平台 */
	private Vendor vendor;
	
	/* 日期 */
	private Date date;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// MYSQL ID generator
	@Column(name = "tid", nullable = false, updatable = false)
	public Long getTid() {
		return tid;
	}

	@OneToOne
	@JoinColumn(name="stid")
	public SchemeType getSchemeType() {
		return schemeType;
	}

	@Column(name="valify_count")
	public Integer getValifyCount() {
		return valifyCount;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="vendor")
	@Enumerated(EnumType.STRING)
	public Vendor getVendor() {
		return vendor;
	}

	@Column(name="date")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}

	public void setValifyCount(Integer valifyCount) {
		this.valifyCount = valifyCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	@Transient
	public Serializable getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
