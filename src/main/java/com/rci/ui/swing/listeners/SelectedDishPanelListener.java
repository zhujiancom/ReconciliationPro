package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.rci.ui.swing.model.DishJCheckBox;
import com.rci.ui.swing.views.component.SelectedDishPanel;
import com.rci.ui.swing.vos.DishVO;

public class SelectedDishPanelListener extends DishSelectListener {
	private SelectedDishPanel displayPanel;
	
	public SelectedDishPanelListener(SelectedDishPanel displayPanel){
		this.displayPanel = displayPanel;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		List<DishVO> dishes = new ArrayList<DishVO>();
		for(DishJCheckBox selectDish:selectDishes){
			dishes.add(selectDish.getDish());
		}
		displayPanel.setSelectedDishes(dishes);
		displayPanel.reflushView();
	}

}
