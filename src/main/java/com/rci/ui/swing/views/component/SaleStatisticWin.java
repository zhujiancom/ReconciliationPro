package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.rci.ui.swing.handler.TimeSwitcherHandler;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.switcher.SwitcherBar;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public class SaleStatisticWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5270961465345282597L;
	private TimeSwitcherHandler handler;

	public SaleStatisticWin(String title){
		super(title);
		initComponent();
	}

	private void initComponent() {
		JPanel containerPanel = this.getContainerPanel();
		handler = new TimeSwitcherHandler();
		JPanel queryBar = createQueryBar();
		JPanel mainPanel = createMainPanel();
		handler.setDisplayPanel(mainPanel);
		containerPanel.add(queryBar,BorderLayout.NORTH);
		containerPanel.add(mainPanel,BorderLayout.CENTER);
	}

	/**
	 * 
	 * Describle(描述)：创建查询工具栏
	 *
	 * 方法名称：createQueryBar
	 *
	 * 所在类名：SaleStatisticWin
	 *
	 * Create Time:2015年12月7日 下午4:38:07
	 *  
	 * @return
	 */
	private JPanel createQueryBar() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JLabel time = new JLabel("统计时间：");
		SwitcherBar timeSwitcherBar = new SwitcherBar(handler);
		SwitcherElement today = new SwitcherElement("今天", true);
		today.setActionCommand("0");
		SwitcherElement lastWeek = new SwitcherElement("近7天");
		lastWeek.setActionCommand("-7");
		SwitcherElement lastMonth = new SwitcherElement("近30天");
		lastMonth.setActionCommand("-30");
		timeSwitcherBar.addElement(today);
		timeSwitcherBar.addElement(lastWeek);
		timeSwitcherBar.addElement(lastMonth);
		JTextField startDate = new JTextField(20);
		JLabel l = new JLabel("到");
		JTextField endDate = new JTextField(20);
		JButton btn = ButtonFactory.createButton("确定");
		
		panel.add(time);
		panel.add(timeSwitcherBar);
		panel.add(startDate);
		panel.add(l);
		panel.add(endDate);
		panel.add(btn);
		return panel;
	}
	
	private JPanel createMainPanel(){
		JPanel mainPanel = new JPanel();
		JScrollPane spane = new JScrollPane();
		return mainPanel;
	}
}
