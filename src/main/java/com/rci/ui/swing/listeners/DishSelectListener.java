package com.rci.ui.swing.listeners;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import com.rci.ui.swing.model.DishJCheckBox;

public abstract class DishSelectListener implements ItemListener,ActionListener {
	protected List<DishJCheckBox> selectDishes = new ArrayList<DishJCheckBox>();
//	private SelectedDishPanel displayPanel;
	
	public DishSelectListener(){}
	
//	public DishSelectListener(SelectedDishPanel displayPanel){
//		this.displayPanel = displayPanel;
//	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		DishJCheckBox source = (DishJCheckBox) e.getItemSelectable();
		if(e.getStateChange() == ItemEvent.SELECTED){
			selectDishes.add(source);
		}else{
			selectDishes.remove(source);
		}
	}

//	@Override
//	public void actionPerformed(ActionEvent paramActionEvent) {
//		List<DishVO> dishes = new ArrayList<DishVO>();
//		for(DishJCheckBox selectDish:selectDishes){
//			dishes.add(selectDish.getDish());
//		}
//		displayPanel.setSelectedDishes(dishes);
//		displayPanel.reflushView();
//	}
	
	public List<DishJCheckBox> getSelectDishes() {
		return selectDishes;
	}
//
//	public SelectedDishPanel getDisplayPanel() {
//		return displayPanel;
//	}

	public void setSelectDishes(List<DishJCheckBox> selectDishes) {
		this.selectDishes = selectDishes;
	}

//	public void setDisplayPanel(SelectedDishPanel displayPanel) {
//		this.displayPanel = displayPanel;
//	}

}
