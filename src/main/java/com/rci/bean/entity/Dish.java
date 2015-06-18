package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.rci.bean.entity.base.BaseEntity;

@Entity
@Table(name="bus_tb_dish")
public class Dish extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1306827303631905471L;

	private Long did;
	
	/* ��Ʒ���  */
	private String dishNo;
	
	/* ��Ʒ���   */
	private String dishName;
	
	/* ��Ʒ�۸�  */
	private BigDecimal dishPrice;
	
	/* ��Ʒ����  */
	private DishType dishType;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="did", nullable=false,updatable=false)
	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	@Column(name="dish_no")
	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	@Column(name="dish_name")
	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	@Column(name="dish_price")
	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="dish_type_id")
	public DishType getDishType() {
		return dishType;
	}

	public void setDishType(DishType dishType) {
		this.dishType = dishType;
	}

	@Override
	@Transient
	public Serializable getId() {
		return did;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
