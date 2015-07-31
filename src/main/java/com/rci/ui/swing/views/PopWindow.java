package com.rci.ui.swing.views;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PopWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9214246678265914986L;
	
	public PopWindow(){
		JPanel containerPanel = new JPanel(new BorderLayout(0, 10));
		containerPanel.setName("base panel");
		this.setContentPane(containerPanel);
		this.setSize(800, 600);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
		this.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		this.setVisible(true);
	}
	
	public PopWindow(String title){
		this();
		this.setTitle(title);
	}
}