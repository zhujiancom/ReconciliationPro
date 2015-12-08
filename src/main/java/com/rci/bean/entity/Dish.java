package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.rci.enums.CommonEnums;
import com.rci.enums.CommonEnums.YOrN;

/**
 * 
 * remark (备注):菜品
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：Dish
 *
 * 包名称：com.rci.bean.entity
 *
 * Create Time: 2015年6月23日 上午8:43:12
 *
 */
@Entity
@Table(name="bus_tb_dish")
public class Dish extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1306827303631905471L;

	private Long did;
	
	/* 菜品编号 */
	private String dishNo;
	
	/* 菜品名称   */
	private String dishName;
	
	/* 菜品单价 */
	private BigDecimal dishPrice;
	
	/* 菜品类型 小类 */
	private DishType dishType;
	
	/* 菜品类型大类 */
	private DishSeries dishSeries;
	
	/* 是否停用 */
	private YOrN stopFlag;
	
	/* 是否受库存管理  */
//	private YOrN stockFlag;
	
	/* 是否是套餐 */
	private YOrN suitFlag;
	
	/* 是否可打折 */
	private CommonEnums.YOrN discountFlag;
	
	/* 菜品成本单价 （原料成本） */
	private BigDecimal cost;
	
	/* 是否参与统计 */
	private YOrN statisticFlag;
	
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

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="dish_type_id")
	public DishType getDishType() {
		return dishType;
	}

	public void setDishType(DishType dishType) {
		this.dishType = dishType;
	}

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="dish_series_id")
	public DishSeries getDishSeries() {
		return dishSeries;
	}

//	@ManyToOne(cascade={CascadeType.ALL})
//	@JoinColumn(name="scheme_type_id")
//	public SchemeType getSchemeType() {
//		return schemeType;
//	}
//
//	public void setSchemeType(SchemeType schemeType) {
//		this.schemeType = schemeType;
//	}

	public void setDishSeries(DishSeries dishSeries) {
		this.dishSeries = dishSeries;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="stop_flag")
	public YOrN getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(YOrN stopFlag) {
		this.stopFlag = stopFlag;
	}

//	@Enumerated(EnumType.STRING)
//	@Column(name="stock_flag")
//	public YOrN getStockFlag() {
//		return stockFlag;
//	}
//
//	public void setStockFlag(YOrN stockFlag) {
//		this.stockFlag = stockFlag;
//	}

	@Enumerated(EnumType.STRING)
	@Column(name="suit_flag")
	public YOrN getSuitFlag() {
		return suitFlag;
	}

	public void setSuitFlag(YOrN suitFlag) {
		this.suitFlag = suitFlag;
	}

	/**
	 * @return the discountFlag
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="discount_flag")
	public CommonEnums.YOrN getDiscountFlag() {
		return discountFlag;
	}

	/**
	 * @param discountFlag the discountFlag to set
	 */
	public void setDiscountFlag(CommonEnums.YOrN discountFlag) {
		this.discountFlag = discountFlag;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="statistic_flag")
	public YOrN getStatisticFlag() {
		return statisticFlag;
	}

	public void setStatisticFlag(YOrN statisticFlag) {
		this.statisticFlag = statisticFlag;
	}

	@Column(name="cost")
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
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
