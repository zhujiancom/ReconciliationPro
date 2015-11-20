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
@Table(name = "bus_tb_scheme_type_dish_ref")
public class SchemeTypeDishRef extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6433739395527125273L;

	private Long refId;

	private String typeno;

	private String dishno;
	
	private String dishname;

	public SchemeTypeDishRef(){}
	
	public SchemeTypeDishRef(String typeno,String dishno,String dishname){
		this.typeno = typeno;
		this.dishno = dishno;
		this.dishname = dishname;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refid", nullable = false, updatable = false)
	public Long getRefId() {
		return refId;
	}

	@Column(name = "type_no")
	public String getTypeno() {
		return typeno;
	}

	@Column(name = "dish_no")
	public String getDishno() {
		return dishno;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	public void setDishno(String dishno) {
		this.dishno = dishno;
	}

	@Column(name = "dish_name")
	public String getDishname() {
		return dishname;
	}

	public void setDishname(String dishname) {
		this.dishname = dishname;
	}

	@Override
	@Transient
	public Serializable getId() {
		return refId;
	}

}
