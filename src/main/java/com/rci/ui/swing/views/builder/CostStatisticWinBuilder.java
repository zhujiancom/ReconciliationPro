package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.statistic.CostStatisticWin;

public class CostStatisticWinBuilder extends AbstractWinBuilder {
	private CostStatisticWinBuilder(){}
	
	private static class CostStatisticWinBuilderHolder{
		private static CostStatisticWinBuilder instance = new CostStatisticWinBuilder();
	}
	
	public static CostStatisticWinBuilder getInstance(){
		return CostStatisticWinBuilderHolder.instance;
	}

	@Override
	protected PopWindow createWindow() {
		return new CostStatisticWin("成本控制");
	}

}
