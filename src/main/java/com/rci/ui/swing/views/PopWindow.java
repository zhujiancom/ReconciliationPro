package com.rci.ui.swing.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PopWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9214246678265914986L;
	private final static int WIN_WIDTH = 800;
	private final static int WIN_HEIGHT = 600;
	private JPanel containerPanel;
	
	public PopWindow(){
		this(WIN_WIDTH,WIN_HEIGHT);
	}
	
	public PopWindow(String title){
		this();
		this.setTitle(title);
	}
	
	public PopWindow(int width,int height){
		containerPanel = new JPanel(new BorderLayout(0, 10));
		containerPanel.setName("base panel");
		this.setContentPane(containerPanel);
		this.setPreferredSize(new Dimension(width,height));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
		this.pack();
		this.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		this.setVisible(true);
	}
	
	public PopWindow(int width,int height,String title){
		this(width,height);
		this.setTitle(title);
	}
	
	public void close(){
		this.dispose();
	}

	public JPanel getContainerPanel() {
		return containerPanel;
	}

	public void setContainerPanel(JPanel containerPanel) {
		this.containerPanel = containerPanel;
	}
}
