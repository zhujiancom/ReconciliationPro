package com.rci.ui.swing.views.component;

import com.rci.ui.swing.views.PopWindow;

public class DishSelectWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2248904392389671255L;
	
	private SelectedDishPanel parentPanel;
	
	public DishSelectWin(int width,int height,String title){
		super(width,height,title);
		createContentPane();
	}

	private void createContentPane() {
		
	}

	public SelectedDishPanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(SelectedDishPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

}
