package com.rci.ui.swing.handler;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.rci.service.core.StatisticCenterFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public abstract class TimeSwitcherHandler extends AbstractSwitcherHandler {
	protected JPanel displayPanel;
	protected StatisticCenterFacade statisticService;
//	protected ChartPanel chartPanel;
//	protected JScrollPane scrollPanel;
	
	public TimeSwitcherHandler(){
		super();
		statisticService = (StatisticCenterFacade) SpringUtils.getBean("StatisticCenterFacade");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		selectedElement = (SwitcherElement) event.getSource();
		new Thread(new Runnable(){

			@Override
			public void run() {
				refreshUI();
				String actioncommand = selectedElement.getActionCommand();
				int amount = Integer.valueOf(actioncommand);
				Date today = DateUtil.getStartTimeOfDay(DateUtil.getCurrentDate());
				Date sdate = DateUtil.addDays(today, amount);
				Date edate = DateUtil.addDays(today, 1);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						displayPanel.removeAll();
						displayPanel.add(new JLabel("Loading",loadingIcon,SwingConstants.CENTER));
						displayPanel.updateUI();
					}
				});
				doQueryAction(sdate,edate);
			}
			
		}).start();
	}

	public JPanel getDisplayPanel() {
		return displayPanel;
	}

	public void setDisplayPanel(JPanel displayPanel) {
		this.displayPanel = displayPanel;
	}
	
	public abstract void doQueryAction(Date sdate,Date edate);
	
}
