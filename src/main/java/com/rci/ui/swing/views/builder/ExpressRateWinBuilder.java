package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.ExpressRateWin;

public class ExpressRateWinBuilder extends AbstractWinBuilder {
	private ExpressRateWinBuilder(){}
	
	private static class ExpressRateWinBuilderHolder{
		private static ExpressRateWinBuilder instance = new ExpressRateWinBuilder();
	}
	
	public static ExpressRateWinBuilder getInstance(){
		return ExpressRateWinBuilderHolder.instance;
	}
	
	@Override
	protected PopWindow createWindow() {
		return new ExpressRateWin("外送率统计");
	}
}
