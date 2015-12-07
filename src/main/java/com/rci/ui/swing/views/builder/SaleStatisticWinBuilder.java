package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.SaleStatisticWin;

public class SaleStatisticWinBuilder implements PopWindowBuilder {
	private SaleStatisticWinBuilder(){};
	private static class SaleStatisticWinBuilderHolder{
		private static SaleStatisticWinBuilder instance = new SaleStatisticWinBuilder();
	}
	
	public static SaleStatisticWinBuilder getInstance(){
		return SaleStatisticWinBuilderHolder.instance;
	}
	
	@Override
	public PopWindow retrieveWindow() {
		return new SaleStatisticWin("菜品销售统计");
	}

}
