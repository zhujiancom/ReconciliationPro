package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.SchemeCreateWin;

public class SchemeCreateWinBuilder extends AbstractWinBuilder{
	
	private SchemeCreateWinBuilder(){}
	
	private static class SchemeCreateWinBuilderHolder{
		private static SchemeCreateWinBuilder instance = new SchemeCreateWinBuilder();
	}
	
	public static SchemeCreateWinBuilder getInstance(){
		return SchemeCreateWinBuilderHolder.instance;
	}

	@Override
	protected PopWindow createWindow() {
		return new SchemeCreateWin(350, 500);
	}
}
