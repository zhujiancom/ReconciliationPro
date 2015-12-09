package com.rci.ui.swing.views.component.statistic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rci.exceptions.ExceptionManage;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.handler.TimeSwitcherHandler;
import com.rci.ui.swing.handler.TurnoverTimeSwitcherHandler;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.switcher.SwitcherBar;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public class TurnoverStatisticWin extends PopWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4248430815448148456L;
	private TimeSwitcherHandler handler;
	
	public TurnoverStatisticWin(String title){
		super(title);
		initComponent();
	}
	
	private void initComponent() {
		JPanel containerPanel = this.getContainerPanel();
		BorderLayout layout = (BorderLayout) containerPanel.getLayout();
		layout.setVgap(0);
		handler = new TurnoverTimeSwitcherHandler();
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
		lastWeek.setActionCommand("-6");
		SwitcherElement lastMonth = new SwitcherElement("近30天");
		lastMonth.setActionCommand("-29");
		timeSwitcherBar.addElement(today);
		timeSwitcherBar.addElement(lastWeek);
		timeSwitcherBar.addElement(lastMonth);
		final JTextField startDate = new JTextField(20);
		JLabel l = new JLabel("到");
		final JTextField endDate = new JTextField(20);
		JButton queryBtn = ButtonFactory.createImageButton("查询", "skin/gray/images/24x24/search.png", null);
		
		panel.add(time);
		panel.add(timeSwitcherBar);
		panel.add(startDate);
		panel.add(l);
		panel.add(endDate);
		panel.add(queryBtn);
		
		queryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						try{
							String sdateStr = startDate.getText();
							String edateStr = endDate.getText();
							Date sdate = null;
							Date edate = null;
							if(!StringUtils.hasText(sdateStr)){
								ExceptionManage.throwServiceException("开始时间必须填写");
							}else{
								sdate = DateUtil.parseDate(sdateStr.trim(), "yyyyMMdd");
							}
							if(StringUtils.hasText(edateStr)){
								edate = DateUtil.parseDate(edateStr.trim(), "yyyyMMdd");
							}else{
								edate = DateUtil.getStartTimeOfDay(DateUtil.getCurrentDate());
							}
							if(sdate.after(edate)){
								ExceptionManage.throwServiceException("开始时间不能晚于结束时间或今天");
							}
							handler.doQueryAction(sdate, edate);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				}).start();
			}
		});
		return panel;
	}
	
	private JPanel createMainPanel(){
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		mainPanel.setBackground(Color.WHITE);
		return mainPanel;
	}
}
