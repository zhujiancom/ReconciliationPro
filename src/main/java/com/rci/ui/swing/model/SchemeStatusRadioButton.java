package com.rci.ui.swing.model;

import javax.swing.JRadioButton;

import com.rci.enums.BusinessEnums.ActivityStatus;

public class SchemeStatusRadioButton extends JRadioButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3850874873732181175L;

	private ActivityStatus status;
	
	public SchemeStatusRadioButton(ActivityStatus status,String text,boolean selected){
		super(text,selected);
		this.status = status;
	}
	
	public SchemeStatusRadioButton(ActivityStatus status,String text){
		this(status,text,false);
	}

	public ActivityStatus getStatus() {
		return status;
	}

	public void setStatus(ActivityStatus status) {
		this.status = status;
	}
}
