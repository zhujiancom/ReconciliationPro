package com.rci.ui.swing.model;

import javax.swing.JCheckBox;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	public DishVO getDish() {
		return dish;
	}

	public void setDish(DishVO dish) {
		this.dish = dish;
	}
	
	@Override
	public boolean equals(Object paramObject) {
		boolean isEqual = false;
		if(paramObject != null && DishJCheckBox.class.isAssignableFrom(paramObject.getClass())){
			DishJCheckBox obj = (DishJCheckBox) paramObject;
			isEqual = new EqualsBuilder().append(this.getDish(), obj.getDish()).isEquals();
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37).append(this.dish).toHashCode();
	}

	@Override
	public String toString() {
		return "DishJCheckBox [dish=" + dish + "]";
	}
	
	
}
