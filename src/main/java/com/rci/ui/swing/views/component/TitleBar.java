/**
 * 
 */
package com.rci.ui.swing.views.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：TitleBar
 *
 * 包名称：com.rci.ui.swing.views.component
 *
 * Create Time: 2015年12月12日 下午2:59:38
 *
 */
public class TitleBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2175146768211452163L;
	private JLabel title;

	private int width;
	
	private int height;
	
	private int marginLeft;
	
	public TitleBar(JLabel title,int width,int height){
		this.title = title;
		this.width = width;
		this.height = height;
		initComponent();
	}

	/**
	 * @return the title
	 */
	public JLabel getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(JLabel title) {
		this.title = title;
	}

	/**
	 *
	 * Describle(描述)：
	 *
	 * 方法名称：initComponent
	 *
	 * 所在类名：TitleBar
	 *
	 * Create Time:2015年12月12日 下午3:00:17
	 *   
	 */
	private void initComponent() {
		setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
		setPreferredSize(new Dimension(width,height));
		add(title);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}

	/**
	 * @return the marginLeft
	 */
	public int getMarginLeft() {
		return marginLeft;
	}

	/**
	 * @param marginLeft the marginLeft to set
	 */
	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}
}
