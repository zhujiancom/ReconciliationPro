package com.rci.ui.swing.model;

import java.awt.Color;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.ui.swing.listeners.ActivityTypeCheckListener;

public class ActivityTypeJCheckBox extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1267587135362216194L;
	private int index;
	private ActivityType activityType;
	
	public ActivityTypeJCheckBox(int index,ActivityType activityType,String paramString){
		super(paramString);
		this.index = index;
		this.activityType = activityType;
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public void addItemListener(ItemListener l) {
		super.addItemListener(l);
		((ActivityTypeCheckListener)l).addCheckBox(this);
	}

	public int getIndex() {
		return index;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
}
