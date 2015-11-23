package com.rci.ui.swing.views.component.tab;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JPanel;

public class Tab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1532453671950240025L;
	private TabbedPane pane = null;
	private Component component = null;
	
	public Tab(TabbedPane pane, Component comp) {
		this.pane = pane;
		this.component = comp;
		this.setLayout(new BorderLayout());
		add(comp);
	}
	
	public void setTabTitle(String title) {
		pane.setTitleAt(pane.getTabPosition(this), title);
	}
	
	public void setIcon(Icon icon) {
		pane.setIconAt(pane.getTabPosition(this), icon);
	}
	
	public Component getComponent() {
		return component;
	}
	
	public void validateTab() {
		invalidate();
		validate();
		repaint();
	}
}
