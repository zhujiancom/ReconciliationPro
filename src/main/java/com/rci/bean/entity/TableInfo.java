package com.rci.bean.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;

@Entity
@Table(name="bus_tb_table")
public class TableInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4282051547723749513L;
	
	private Long tbId;
	
	private String tbNo;
	
	private String tbName;
	
	private String tbType;
	
	private String tbTypeName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tbid", nullable = false, updatable = false)
	public Long getTbId() {
		return tbId;
	}

	public void setTbId(Long tbId) {
		this.tbId = tbId;
	}

	@Column(name = "table_no")
	public String getTbNo() {
		return tbNo;
	}

	public void setTbNo(String tbNo) {
		this.tbNo = tbNo;
	}

	@Column(name = "table_name")
	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	@Column(name = "table_type")
	public String getTbType() {
		return tbType;
	}

	public void setTbType(String tbType) {
		this.tbType = tbType;
	}

	@Column(name = "table_type_name")
	public String getTbTypeName() {
		return tbTypeName;
	}

	public void setTbTypeName(String tbTypeName) {
		this.tbTypeName = tbTypeName;
	}

	@Override
	@Transient
	public Serializable getId() {
		return tbId;
	}
}
