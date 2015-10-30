package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;

public abstract class AbstractWinBuilder implements PopWindowBuilder {
	protected PopWindow win;
	@Override
	public PopWindow retrieveWindow() {
		if(win == null){
			win = createWindow();
		}else{
			win.setVisible(true);
		}
		return win;
	}
	
	protected abstract PopWindow createWindow();
	
}
