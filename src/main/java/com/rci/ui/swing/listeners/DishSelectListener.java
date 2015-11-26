package com.rci.ui.swing.listeners;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rci.ui.swing.model.DishJCheckBox;
import com.rci.ui.swing.vos.DishVO;

public abstract class DishSelectListener implements ItemListener,ActionListener {
	protected Set<DishJCheckBox> selectDishes = new HashSet<DishJCheckBox>();
	protected List<DishVO> oldDishes = new ArrayList<DishVO>();
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
	
//	public List<DishJCheckBox> getSelectDishes() {
//		return selectDishes;
//	}
//
//	public SelectedDishPanel getDisplayPanel() {
//		return displayPanel;
//	}

//	public void setSelectDishes(List<DishJCheckBox> selectDishes) {
//		this.selectDishes = selectDishes;
//	}

	public List<DishVO> getOldDishes() {
		return oldDishes;
	}

	public void setOldDishes(List<DishVO> oldDishes) {
		this.oldDishes = oldDishes;
	}

	public Set<DishJCheckBox> getSelectDishes() {
		return selectDishes;
	}

	public void setSelectDishes(Set<DishJCheckBox> selectDishes) {
		this.selectDishes = selectDishes;
	}

//	public void setDisplayPanel(SelectedDishPanel displayPanel) {
//		this.displayPanel = displayPanel;
//	}

}
