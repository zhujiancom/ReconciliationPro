/**
 * 
 */
package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;

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
public class StockManagementWinBuilder implements PopWindowBuilder {

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#retrieveWindow()
	 */
	@Override
	public PopWindow retrieveWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#createQueryPane()
	 */
	@Override
	public void createQueryPane() {
		throw new UnsupportedOperationException("库存管理窗口没有query面板");
	}

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#createContentPane()
	 */
	@Override
	public void createContentPane() {
		
	}

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#createBottomPane()
	 */
	@Override
	public void createBottomPane() {
		// TODO Auto-generated method stub

	}

}
