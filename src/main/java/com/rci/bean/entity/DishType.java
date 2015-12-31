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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rci.bean.entity.base.BaseEntity;

/**
 * 
 * remark (备注): 菜品小类
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：DishType
 *
 * 包名称：com.rci.bean.entity
 *
 * Create Time: 2015年11月13日 下午3:45:41
 *
 */
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
	
//	private CommonEnums.YOrN notDiscount;
	
	private DishSeries dishSeries;
	
	/* 菜品大类 */
	private String seriesno;
	
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

	@ManyToOne(cascade=CascadeType.ALL) //使用ALL,表示DishType不能够单独更新，必须绑定DishSeries一起更新
	@JoinColumn(name="dish_series_id")
	public DishSeries getDishSeries() {
		return dishSeries;
	}

	public void setDishSeries(DishSeries dishSeries) {
		this.dishSeries = dishSeries;
	}

//	@Enumerated(EnumType.STRING)
//	@Column(name="not_discount")
//	public CommonEnums.YOrN getNotDiscount() {
//		return notDiscount;
//	}
//
//	public void setNotDiscount(CommonEnums.YOrN notDiscount) {
//		this.notDiscount = notDiscount;
//	}

	@Column(name="series_no")
	public String getSeriesno() {
		return seriesno;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}

	@Override
	@Transient
	public Serializable getId() {
		return dtid;
	}

}
