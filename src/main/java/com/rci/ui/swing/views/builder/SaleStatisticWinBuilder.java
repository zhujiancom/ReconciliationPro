package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.statistic.SaleStatisticWin;

public class SaleStatisticWinBuilder extends AbstractWinBuilder {
	private SaleStatisticWinBuilder(){};
	private static class SaleStatisticWinBuilderHolder{
		private static SaleStatisticWinBuilder instance = new SaleStatisticWinBuilder();
	}
	
	public static SaleStatisticWinBuilder getInstance(){
		return SaleStatisticWinBuilderHolder.instance;
	}
	
	@Override
	protected PopWindow createWindow() {
		return new SaleStatisticWin("菜品销售统计");
	}

}
