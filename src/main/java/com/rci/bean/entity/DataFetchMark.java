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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums;

@Entity
@Table(name="bus_tb_datafetch_mark")
public class DataFetchMark extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115082810211557433L;
	
	private Long markId;
	
	private BusinessEnums.MarkType type;
	
	/* 订单数据加载日期 */
	private String rciDate;
	
	/* ��� */
//	private CommonEnums.YOrN markFlag;
	
	/* 记录每次查询的时间 */
	private Date savepoint;
	
	public DataFetchMark(){}
	
	public DataFetchMark(BusinessEnums.MarkType type){
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="markid", nullable=false,updatable=false)
	public Long getMarkId() {
		return markId;
	}

	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	public BusinessEnums.MarkType getType() {
		return type;
	}

	public void setType(BusinessEnums.MarkType type) {
		this.type = type;
	}

	@Column(name="rci_date")
	public String getRciDate() {
		return rciDate;
	}

	public void setRciDate(String rciDate) {
		this.rciDate = rciDate;
	}

//	@Enumerated(EnumType.STRING)
//	@Column(name="mark_flag")
//	public CommonEnums.YOrN getMarkFlag() {
//		return markFlag;
//	}
//
//	public void setMarkFlag(CommonEnums.YOrN markFlag) {
//		this.markFlag = markFlag;
//	}

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
		// TODO Auto-generated method stub
		return markId;
	}

}
