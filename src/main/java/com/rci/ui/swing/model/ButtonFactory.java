package com.rci.ui.swing.model;

import java.awt.Cursor;
import java.awt.Insets;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import com.rci.tools.StringUtils;

/**
 * 
 * remark (备注): 系统按钮创建管理器
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：ButtonFactory
 *
 * 包名称：com.rci.ui.swing.model
 *
 * Create Time: 2015年10月22日 下午2:33:20
 *
 */
public class ButtonFactory {
	public static JButton createImageButton(String icon, String pressedIcon){
		return createImageButton(null,icon,pressedIcon);
	}
	/**
	 * 
	 * Describle(描述)： 创建带图标的按钮
	 *
	 * 方法名称：createImageButton
	 *
	 * 所在类名：ButtonFactory
	 *
	 * Create Time:2015年10月22日 下午2:40:46
	 *  
	 * @param text
	 * @param icon
	 * @param pressedIcon
	 * @return
	 */
	public static JButton createImageButton(String text, String icon, String pressedIcon){
		URL btnUrl = ButtonFactory.class.getClassLoader().getResource(icon);
		JButton btn = new JButton(text,new ImageIcon(btnUrl));
		btn.setUI(new BasicButtonUI());
		btn.setContentAreaFilled(false);
		btn.setMargin(new Insets(0,0,0,0));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBorder(BorderFactory.createEmptyBorder());
		if(StringUtils.hasText(pressedIcon)){
			URL pressedBtnUrl = ButtonFactory.class.getClassLoader().getResource(pressedIcon);
			btn.setPressedIcon(new ImageIcon(pressedBtnUrl));
		}
		return btn;
	}
	
	/**
	 * 
	 * Describle(描述)： 创建普通按钮
	 *
	 * 方法名称：createButton
	 *
	 * 所在类名：ButtonFactory
	 *
	 * Create Time:2015年10月22日 下午2:41:57
	 *  
	 * @param text
	 * @return
	 */
	public static JButton createButton(String text){
		JButton btn = new JButton(text);
		return btn;
	}

	public static JMenuItem createMenuItem(String text){
		return createMenuItem(text,null);
	}
	
	public static JMenuItem createMenuItem(String text,String icon){
		if(!StringUtils.hasText(icon)){
			return new JMenuItem(text);
		}
		URL statisticIconUrl = ButtonFactory.class.getClassLoader().getResource(icon);
		JMenuItem item = new JMenuItem(text,new ImageIcon(statisticIconUrl));
		return item;
	}
}
