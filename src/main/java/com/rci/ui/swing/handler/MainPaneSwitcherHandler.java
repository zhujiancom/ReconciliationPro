package com.rci.ui.swing.handler;

import java.awt.event.ActionEvent;

import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public class MainPaneSwitcherHandler extends AbstractSwitcherHandler {

	@Override
	public void actionPerformed(ActionEvent e) {
		selectedElement = (SwitcherElement) e.getSource();
		new Thread(new Runnable(){

			@Override
			public void run() {
				refreshUI();
				String actioncommand = selectedElement.getActionCommand();
			}
			
		}).start();
	}

}
