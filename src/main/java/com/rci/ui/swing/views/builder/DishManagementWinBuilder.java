package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.DishManagementWin;

public class DishManagementWinBuilder extends AbstractWinBuilder {
	private DishManagementWinBuilder(){}
	
	private static class DishManagementWinBuilderHolder{
		private static final DishManagementWinBuilder instance = new DishManagementWinBuilder();
	}
	
	public static DishManagementWinBuilder getInstance(){
		return DishManagementWinBuilderHolder.instance;
	}

	@Override
	protected PopWindow createWindow() {
		return new DishManagementWin(900,600,"菜品管理");
	}
}
