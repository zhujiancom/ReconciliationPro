package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;

public abstract class AbstractWinBuilder implements PopWindowBuilder {
	protected PopWindow win;
	@Override
	public PopWindow retrieveWindow() {
		if(win == null){
			win = createWindow();
		}else if(win.isDisplayable()){
			win.setVisible(true);
		}
		return win;
	}
	
	protected abstract PopWindow createWindow();
	
//	@Override
//	public void createQueryPane() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void createContentPane() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void createBottomPane() {
//		// TODO Auto-generated method stub
//		
//	}

}
