package com.rci.ui.swing.model;

import javax.swing.JCheckBox;

import com.rci.ui.swing.vos.DishVO;

public class DishJCheckBox extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2315659654325289291L;
	
	private DishVO dish;
	
	public DishJCheckBox(String paramString,DishVO dish){
		super(paramString);
		this.dish = dish;
	}
	
	@Override
	protected String paramString() {
		return dish.getDishName()+"-"+dish.getDishPrice();
	}
}
