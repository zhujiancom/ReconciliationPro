package com.rci.ui.swing.listeners;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class FrameListener extends MouseAdapter {
	private Point lastPoint;
	private JFrame frame;
	
	public FrameListener(JFrame frame){
		this.frame = frame;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastPoint = e.getLocationOnScreen();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = e.getLocationOnScreen();
		int offsetx = point.x-lastPoint.x;
		int offsety = point.y-lastPoint.y;
		
		Rectangle bounds = frame.getBounds();
		bounds.x += offsetx;
		bounds.y += offsety;
		frame.setBounds(bounds);
		lastPoint = point;
	}

}
