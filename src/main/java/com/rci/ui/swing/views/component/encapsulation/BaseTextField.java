package com.rci.ui.swing.views.component.encapsulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.rci.tools.StringUtils;

public class BaseTextField extends JTextField implements FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5850422168087702710L;
	private static final int DEFAULT_COLUMNS=20;
	private static final int DEFAULT_HEIGHT=20;
	
	private String placeholder;
	private int columns;
	private int height;
	
	private String itext;
	
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
		this.addFocusListener(this);
	}
	
	protected void doMousePressedAction(){
		setForeground(Color.BLACK);
		if(placeholder.equals(getText())){
			setText(null);
			setItext(null);
		}
	}
	
	protected void doMouseExitedAction(){
		String preText = getText();
		if(!StringUtils.hasText(preText)){
			setText(placeholder);
			setItext(null);
			setForeground(Color.LIGHT_GRAY);
		}else if(placeholder.equals(preText)){
			setItext(null);
		}else{
			setItext(preText);
		}
	}
	
	protected void doMouseEnterAction(){}

	@Override
	public void setText(String paramString) {
		super.setText(paramString);
		setForeground(Color.BLACK);
	}
	
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

	public String getItext() {
		return itext;
	}

	public void setItext(String itext) {
		this.itext = itext;
	}

	@Override
	public void focusGained(FocusEvent paramFocusEvent) {
		if(!StringUtils.hasText(getItext())){
			setText("");
			setItext(null);
			setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent paramFocusEvent) {
		String preText = getText();
		if(!StringUtils.hasText(preText)){
			setText(placeholder);
			setItext(null);
			setForeground(Color.LIGHT_GRAY);
		}else if(placeholder.equals(preText)){
			setItext(null);
		}else{
			setItext(preText);
			setText(preText);
		}
	}
}
