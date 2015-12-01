/**
 * 
 */
package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.InventoryManagementWin;


/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：InventoryManagementWinBuilder
 *
 * 包名称：com.rci.ui.swing.views.builder
 *
 * Create Time: 2015年11月21日 下午3:45:01
 *
 */
public class InventoryManagementWinBuilder extends AbstractWinBuilder {
	private InventoryManagementWinBuilder(){}
	
	private static class InventoryManagementWinBuilderHolder{
		private static InventoryManagementWinBuilder instance = new InventoryManagementWinBuilder();
	}
	
	public static InventoryManagementWinBuilder getInstance(){
		return InventoryManagementWinBuilderHolder.instance;
	}

	@Override
	protected PopWindow createWindow() {
		return new InventoryManagementWin(900,600,"库存管理");
	}

}
