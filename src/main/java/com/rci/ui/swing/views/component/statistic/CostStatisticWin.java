package com.rci.ui.swing.views.component.statistic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.rci.exceptions.ExceptionManage;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.handler.switcher.CostTimeSwitcherHandler;
import com.rci.ui.swing.handler.switcher.TimeSwitcherHandler;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.encapsulation.DateTextField;
import com.rci.ui.swing.views.component.switcher.SwitcherBar;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public class CostStatisticWin extends PopWindow implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403553491811645787L;
	private TimeSwitcherHandler handler;
	private DateTextField startDate;
	private DateTextField endDate;
	
	public CostStatisticWin(String title){
		super(title);
		initComponent();
	}
	
	private void initComponent() {
		JPanel containerPanel = this.getContainerPanel();
		BorderLayout layout = (BorderLayout) containerPanel.getLayout();
		layout.setVgap(0);
		handler = new CostTimeSwitcherHandler();
		JPanel queryBar = createQueryBar();
		JPanel mainPanel = createMainPanel();
		handler.setDisplayPanel(mainPanel);
		containerPanel.add(queryBar,BorderLayout.NORTH);
		containerPanel.add(mainPanel,BorderLayout.CENTER);
	}
	
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
		startDate = new DateTextField("开始时间");
		JLabel l = new JLabel("到");
		endDate = new DateTextField("结束时间");
		JButton queryBtn = ButtonFactory.createImageButton("查询", "skin/gray/images/24x24/search.png", null);
		
		panel.add(time);
		panel.add(timeSwitcherBar);
		panel.add(startDate);
		panel.add(l);
		panel.add(endDate);
		panel.add(queryBtn);
		
		queryBtn.addActionListener(this);
		queryBtn.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		return panel;
	}
	
	private JPanel createMainPanel(){
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		mainPanel.setBackground(Color.WHITE);
		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					Date sdate = startDate.getDate();
					Date edate = endDate.getDate();
					if(sdate == null){
						if(StringUtils.hasText(startDate.getErrorMsg())){
							ExceptionManage.throwServiceException(startDate.getErrorMsg());
						}else{
							ExceptionManage.throwServiceException("开始时间必须填写");
						}
					}
					if(edate == null && !StringUtils.hasText(endDate.getErrorMsg())){
						edate = DateUtil.getStartTimeOfDay(DateUtil.getCurrentDate());
					}else{
						ExceptionManage.throwServiceException(endDate.getErrorMsg());
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
}
