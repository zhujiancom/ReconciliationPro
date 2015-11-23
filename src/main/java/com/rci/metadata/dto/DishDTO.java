/**
 * 
 */
package com.rci.metadata.dto;

import java.math.BigDecimal;

import com.rci.annotation.ColumnName;
import com.rci.enums.CommonEnums.YOrN;

/**
 * @author zj
 *	
 * 项目名称：BillSystem
 *
 * 类名称：DishDTO
 *
 * 包名称：com.bill.sys.metadata
 *
 * Operate Time: 2015年4月9日 下午11:16:24
 *
 * remark (备注):
 *
 * 文件名称：DishDTO.java
 *
 */
public class DishDTO {
	/* 产品编号  */
	private String dishNo;
	
	/* 产品名称   */
	private String dishName;
	
	/* 产品价格  */
	private BigDecimal dishPrice;
	
	/* 产品类型  */
	private String dishType;
	
	/* 产品大类 */
	private String seriesno;
	
	/* 是否停用 */
	private YOrN stopFlag;
	
	private YOrN suitFlag;
	
	private YOrN discountFlag;

	@ColumnName("ch_dishno")
	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	@ColumnName("vch_dishname")
	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	@ColumnName("num_price1")
	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	@ColumnName("ch_typeno")
	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}

	@ColumnName("ch_stopflag")
	public YOrN getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(YOrN stopFlag) {
		this.stopFlag = stopFlag;
	}

	@ColumnName("ch_suitflag")
	public YOrN getSuitFlag() {
		return suitFlag;
	}

	public void setSuitFlag(YOrN suitFlag) {
		this.suitFlag = suitFlag;
	}

	/**
	 * @return the discountFlag
	 */
	@ColumnName("ch_discount")
	public YOrN getDiscountFlag() {
		return discountFlag;
	}

	/**
	 * @param discountFlag the discountFlag to set
	 */
	public void setDiscountFlag(YOrN discountFlag) {
		this.discountFlag = discountFlag;
	}

	@ColumnName("ch_seriesno")
	public String getSeriesno() {
		return seriesno;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}
}
