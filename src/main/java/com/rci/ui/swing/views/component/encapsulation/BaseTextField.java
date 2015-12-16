package com.rci.ui.swing.views.component.encapsulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.rci.tools.StringUtils;

public class BaseTextField extends JTextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5850422168087702710L;
	private static final int DEFAULT_COLUMNS=20;
	private static final int DEFAULT_HEIGHT=20;
	
	private String placeholder;
	private int columns;
	private int height;
	
	public BaseTextField(){
		this("");
	}
	
	public BaseTextField(String placeholder){
		this(placeholder,DEFAULT_COLUMNS,DEFAULT_HEIGHT);
	}
	
	public BaseTextField(String placeholder,int columns,int height){
		super(placeholder,columns);
		this.placeholder = placeholder;
		this.columns = columns;
		this.height = height;
		initComponent();
	}
	
	private void initComponent(){
		setForeground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		setPreferredSize(new Dimension(columns,height));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				doMousePressedAction();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				doMouseExitedAction();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				doMouseEnterAction();
			}
			
		});
	}
	
	protected void doMousePressedAction(){
		setForeground(Color.BLACK);
		if(placeholder.equals(getText())){
			setText(null);
		}
	}
	
	protected void doMouseExitedAction(){
		if(!StringUtils.hasText(getText())){
			setText(placeholder);
			setForeground(Color.LIGHT_GRAY);
		}
	}
	
	protected void doMouseEnterAction(){}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public int getColumns() {
		return columns;
	}

	public int getHeight() {
		return height;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
