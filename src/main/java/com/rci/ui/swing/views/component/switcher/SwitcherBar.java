/**
 * 
 */
package com.rci.ui.swing.views.component.switcher;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.rci.ui.swing.handler.AbstractSwitcherHandler;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SwitcherBar
 *
 * 包名称：com.rci.ui.swing.views.component.switcher
 *
 * Create Time: 2015年12月8日 上午12:03:47
 *
 */
public class SwitcherBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7074881270541561933L;
	private AbstractSwitcherHandler elementActionHandler; 
	
	public SwitcherBar(AbstractSwitcherHandler elementActionHandler){
		this.elementActionHandler = elementActionHandler;
		initComponent();
	}
	
	private void initComponent() {
		setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		setBackground(Color.WHITE);
	}

	public void addElement(SwitcherElement element){
		elementActionHandler.addElement(element);
		add(element);
	}
}
