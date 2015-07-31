package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.rci.ui.swing.views.builder.PopWindowBuilder;

/**
 * 
 * remark (备注): 监听器需要创建窗口的基类
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：BaseWinListener
 *
 * 包名称：com.rci.ui.swing.listeners
 *
 * Create Time: 2015年7月31日 上午11:27:07
 *
 */
public abstract class BaseWinListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		getBuilder().retrieveWindow();
	}
	
	/**
	 * 
	 * Describle(描述)： 获取窗口创建
	 *
	 * 方法名称：getBuilder
	 *
	 * 所在类名：BaseListener
	 *
	 * Create Time:2015年7月31日 上午11:26:24
	 *  
	 * @return
	 */
	protected abstract PopWindowBuilder getBuilder();

}
