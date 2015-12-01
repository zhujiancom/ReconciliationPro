package com.rci.ui.swing.views.builder;

import com.rci.contants.BusinessConstant;
import com.rci.ui.swing.views.PopWindow;

public class WindowBuilderFactory {
	public static void createExpressReateWindow(){
		ExpressRateWinBuilder.getInstance().retrieveWindow();
	}
	
	/**
	 * 
	 * Describle(描述)：创建库存查看窗口建造者
	 *
	 * 方法名称：createViewStockWinBuilder
	 *
	 * 所在类名：WindowBuilderFactory
	 *
	 * Create Time:2015年7月31日 上午11:00:10
	 *  
	 * @return
	 */
	public static void createViewStockWindow(){
		new StockWinBuilder(BusinessConstant.VIEW_ACTION).retrieveWindow();
	}
	
	/**
	 * 
	 * Describle(描述)：创建库存设置窗口建造者
	 *
	 * 方法名称：createReStockWinBuilder
	 *
	 * 所在类名：WindowBuilderFactory
	 *
	 * Create Time:2015年7月31日 上午11:00:41
	 *  
	 * @return
	 */
	public static void createReStockWindow(){
		new StockWinBuilder(BusinessConstant.RESTOCK_ACTION).retrieveWindow();
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
	
	/**
	 * 
	 *
	 * Describle(描述)： 创建库存管理窗口
	 *
	 * 方法名称：createStockManagementWinBuilder
	 *
	 * 所在类名：WindowBuilderFactory
	 *
	 * Create Time:2015年8月18日 下午10:50:15
	 *  
	 * @return
	 */
	public static void createStockManagementWindow(){
		StockManagementWinBuilder.getInstance().retrieveWindow();
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
