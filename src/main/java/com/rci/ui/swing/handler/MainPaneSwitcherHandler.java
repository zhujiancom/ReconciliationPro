package com.rci.ui.swing.handler;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.rci.ui.swing.CheckedoutOrderPanel;
import com.rci.ui.swing.HangupOrderPanel;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public class MainPaneSwitcherHandler extends AbstractSwitcherHandler {
	protected Container displayPanel;
	
	private JPanel contentPanel;
	
	public MainPaneSwitcherHandler(Container displayPanel){
		super();
		this.displayPanel = displayPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		selectedElement = (SwitcherElement) e.getSource();
		new Thread(new Runnable(){

			@Override
			public void run() {
				refreshUI();
				String actioncommand = selectedElement.getActionCommand();
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						displayPanel.removeAll();
						displayPanel.add(new JLabel("Loading",loadingIcon,SwingConstants.CENTER));
						((JPanel) displayPanel).updateUI();
					}
				});
				if("checked".equals(actioncommand)){
					contentPanel = createCheckedoutOrderPanel();
				}
				if("unchecked".equals(actioncommand)){
					contentPanel = createHangupOrderPanel();
				}
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						displayPanel.removeAll();
						displayPanel.add(contentPanel,BorderLayout.CENTER);
						((JPanel) displayPanel).updateUI();
					}
				});
			}
			
		}).start();
	}

	public Container getDisplayPanel() {
		return displayPanel;
	}

	public void setDisplayPanel(Container displayPanel) {
		this.displayPanel = displayPanel;
	}
	
	private JPanel createCheckedoutOrderPanel(){
		return new CheckedoutOrderPanel(displayPanel.getWidth(),displayPanel.getHeight());
	}
	
	private JPanel createHangupOrderPanel(){
		return new HangupOrderPanel();
	}

}
