package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.TurnOverWin;
import com.rci.ui.swing.views.component.statistic.TurnoverStatisticWin;

public class TurnoverWinBuilder extends AbstractWinBuilder {
	private TurnoverWinBuilder(){}
	
	private static class TurnoverWinBuilderHolder{
		private static TurnoverWinBuilder instance = new TurnoverWinBuilder();
	}
	
	public static TurnoverWinBuilder getInstance(){
		return TurnoverWinBuilderHolder.instance;
	}
	
	@Override
	protected PopWindow createWindow() {
//		return new TurnOverWin("营业额统计");
		return new TurnoverStatisticWin("营业额统计");
	}
}
