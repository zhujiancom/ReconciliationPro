package com.rci.ui.swing.views.builder;

import com.rci.contants.BusinessConstant;

public class WindowBuilderFactory {
	public static ExpressRateWinBuilder createExpressReateWinBuilder(){
		return new ExpressRateWinBuilder();
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
	public static StockWinBuilder createViewStockWinBuilder(){
		return new StockWinBuilder(BusinessConstant.VIEW_ACTION);
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
	public static StockWinBuilder createReStockWinBuilder(){
		return new StockWinBuilder(BusinessConstant.RESTOCK_ACTION);
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
	public static TurnoverWinBuilder createTurnoverWinBuilder(){
		return new TurnoverWinBuilder();
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
	public static StockManagementWinBuilder createStockManagementWinBuilder(){
		return new StockManagementWinBuilder();
	}
}
