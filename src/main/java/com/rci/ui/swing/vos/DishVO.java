package com.rci.ui.swing.vos;

import com.rci.enums.CommonEnums.YOrN;

public class DishVO {
	/* 菜品编号 */
	private String dishNo;
	
	/* 菜品名称   */
	private String dishName;
	
	/* 是否受库存管理  */
	private YOrN stockFlag;

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public YOrN getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(YOrN stockFlag) {
		this.stockFlag = stockFlag;
	}

	@Override
	public String toString() {
		return dishName;
	}
	
}
