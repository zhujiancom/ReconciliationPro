package com.rci.bean.entity.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;

@Entity
@Table(name="bus_tb_inventory_dish_ref")
public class InventoryDishRef extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2768635574915712797L;
	
	private Long idrid;
	
	private String ino;
	
	private String dishno;
	
	private String dishname;
	
	/* 菜品规格 年糕火锅里年糕8个 */
	private BigDecimal standard = BigDecimal.ONE;
	
	public InventoryDishRef(){}
	
	public InventoryDishRef(String ino,String dishno,String dishname){
		this.ino = ino;
		this.dishno = dishno;
		this.dishname = dishname;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrid", nullable = false, updatable = false)
	public Long getIdrid() {
		return idrid;
	}

	@Column(name="ino")
	public String getIno() {
		return ino;
	}

	@Column(name="dish_no")
	public String getDishno() {
		return dishno;
	}

	@Column(name="dish_name")
	public String getDishname() {
		return dishname;
	}

	public void setIdrid(Long idrid) {
		this.idrid = idrid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setDishno(String dishno) {
		this.dishno = dishno;
	}

	public void setDishname(String dishname) {
		this.dishname = dishname;
	}

	@Column(name="standard")
	public BigDecimal getStandard() {
		return standard;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	@Override
	@Transient
	public Serializable getId() {
		return idrid;
	}

}
