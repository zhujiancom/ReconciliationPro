package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ExpressRateListener implements ActionListener {
	private JFrame frame;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		createFrame();
	}
	
	public void createFrame(){
		frame = new JFrame("外送率统计信息");
		frame.setSize(800, 600);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
		frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		frame.setVisible(true);
	}

}
