package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;

public class WindowBuilderFactory {
	public static void createExpressReateWindow(){
		ExpressRateWinBuilder.getInstance().retrieveWindow();
	}
	
	
	/**
	 * 
	 *
	 * Describle(描述)：创建营业额统计窗口
	 *
	 * 方法名称：createTurnoverWinBuilder
	 *
	 * 所在类名：WindowBuilderFactory
	 *
	 * Create Time:2015年8月18日 下午10:49:25
	 *  
	 * @return
	 */
	public static void createTurnoverWindow(){
		TurnoverWinBuilder.getInstance().retrieveWindow();
	}
	
	public static void createSaleStatisticWindow(){
		SaleStatisticWinBuilder.getInstance().retrieveWindow();
	}
	
	public static ProgressWinBuilder createProgressWinBuilder(){
		return new ProgressWinBuilder();
	}
	
	public static PopWindow createSchemeManagementWindow(){
		return SchemeManagementWinBuilder.getInstance().retrieveWindow();
	}
	
	public static PopWindow createSchemeTypeMainWindow(){
		return SchemeTypeMainWinBuilder.getInstance().retrieveWindow();
	}
	
	public static PopWindow createInventoryManagementWindow(){
		return InventoryManagementWinBuilder.getInstance().retrieveWindow();
	}
	
	public static PopWindow createDishManagementWindow(){
		return DishManagementWinBuilder.getInstance().retrieveWindow();
	}
	
}
