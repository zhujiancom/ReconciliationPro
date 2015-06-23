package com.rci.bean.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.CommonEnums;

@Entity
@Table(name="bus_tb_dish_type")
public class DishType extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6506342777961564482L;
	
	private Long dtid;
	
	/* ���ͱ��  */
	private String dtNo;
	
	/* �������  */
	private String dtName;
	
	private List<Dish> dishes;
	
	private CommonEnums.YOrN notDiscount;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="dtid", nullable=false,updatable=false)
	public Long getDtid() {
		return dtid;
	}

	public void setDtid(Long dtid) {
		this.dtid = dtid;
	}

	@Column(name="dish_type_no")
	public String getDtNo() {
		return dtNo;
	}

	public void setDtNo(String dtNo) {
		this.dtNo = dtNo;
	}

	@Column(name="dish_type_name")
	public String getDtName() {
		return dtName;
	}

	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="dishType")
	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="not_discount")
	public CommonEnums.YOrN getNotDiscount() {
		return notDiscount;
	}

	public void setNotDiscount(CommonEnums.YOrN notDiscount) {
		this.notDiscount = notDiscount;
	}

	@Override
	@Transient
	public Serializable getId() {
		return dtid;
	}

}
