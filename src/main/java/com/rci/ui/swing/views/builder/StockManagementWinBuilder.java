/**
 * 
 */
package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.StockManagementWin;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：StockManagementWinBuilder
 *
 * 包名称：com.rci.ui.swing.views.builder
 *
 * Create Time: 2015年8月18日 下午10:49:04
 *
 */
public class StockManagementWinBuilder extends AbstractWinBuilder{
	private StockManagementWinBuilder(){}
	
	private static class StockManagementWinBuilderHolder{
		private static StockManagementWinBuilder instance = new StockManagementWinBuilder();
	}
	
	public static StockManagementWinBuilder getInstance(){
		return StockManagementWinBuilderHolder.instance;
	}
	@Override
	protected PopWindow createWindow() {
		return new StockManagementWin("库存管理设置");
	}
}
