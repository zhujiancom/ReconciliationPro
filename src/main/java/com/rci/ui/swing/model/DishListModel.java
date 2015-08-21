package com.rci.ui.swing.model;

import java.util.List;

import javax.swing.AbstractListModel;

import com.rci.ui.swing.vos.DishVO;

public class DishListModel extends AbstractListModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3349613290184970954L;
	private List<DishVO> dishes;
	
	public DishListModel(){}
	
	public DishListModel(List<DishVO> dishes){
		this.dishes = dishes;
	}

	@Override
	public int getSize() {
		return dishes.size();
	}

	@Override
	public Object getElementAt(int index) {
		DishVO dish = getDishAt(index);
		return dish.getDishName();
	}
	
	public DishVO getDishAt(int index){
		return dishes.get(index);
	}

	public List<DishVO> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishVO> dishes) {
		this.dishes = dishes;
	}

	public void addElement(DishVO dish){
		int index = dishes.size();
		dishes.add(dish);
		fireIntervalAdded(this,index,index);
	}
	
	public void removeElementAt(int index){
		dishes.remove(index);
		fireIntervalRemoved(this, index, index);
	}
}
