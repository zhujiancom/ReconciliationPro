package com.rci.bean.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;

/**
 * 
 * remark (备注): 菜品大类
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：DishSeries
 *
 * 包名称：com.rci.bean.entity
 *
 * Create Time: 2015年11月13日 下午3:46:11
 *
 */
@Entity
@Table(name="bus_tb_dish_series")
public class DishSeries extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2170512959586652289L;
	private Long dsid;
	
	private String seriesno;
	
	private String seriesname;
	
	/* 包含的小类 */
	private List<DishType> types;
	
	/* 包含的菜品 */
	private List<Dish> dishes;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="dsid", nullable=false,updatable=false)
	public Long getDsid() {
		return dsid;
	}

	@Column(name="series_no")
	public String getSeriesno() {
		return seriesno;
	}

	@Column(name="series_name")
	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="dishSeries")
	public List<DishType> getTypes() {
		return types;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="dishSeries")
	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDsid(Long dsid) {
		this.dsid = dsid;
	}

	public void setTypes(List<DishType> types) {
		this.types = types;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	@Override
	@Transient
	public Serializable getId() {
		return dsid;
	}

}
