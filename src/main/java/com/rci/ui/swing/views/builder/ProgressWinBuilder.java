package com.rci.ui.swing.views.builder;

import java.awt.Container;

import javax.swing.JProgressBar;

import com.rci.ui.swing.views.PopWindow;

public class ProgressWinBuilder implements PopWindowBuilder {
	private JProgressBar bar;
	
	@Override
	public PopWindow retrieveWindow() {
		PopWindow progressWindow = new PopWindow(400,60);
		progressWindow.setResizable(false);
		createContentPane();
		Container containerPanel = progressWindow.getContentPane();
		containerPanel.add(bar);
		return progressWindow;
	}

	public void createContentPane() {
		bar = new JProgressBar(JProgressBar.HORIZONTAL);
		bar.setStringPainted(true);
		bar.setBorderPainted(true);
		bar.setVisible(true);
	}

	public JProgressBar getBar() {
		return bar;
	}

	public void setBar(JProgressBar bar) {
		this.bar = bar;
	}

}
