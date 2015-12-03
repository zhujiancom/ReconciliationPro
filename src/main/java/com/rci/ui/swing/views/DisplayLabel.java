package com.rci.ui.swing.views;

import javax.swing.JLabel;

import com.rci.tools.StringUtils;

public class DisplayLabel<K,V> extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2557758374519902448L;
	private K key;
	private V value;
	private String unit;
	
	public DisplayLabel(K key){
		this.key = key;
	}
	
	public DisplayLabel(K key,String unit){
		this(key);
		this.unit = unit;
	}
	
	public DisplayLabel(K key,V value){
		this(key);
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setText(V value){
		this.value = value;
		if(value != null){
			super.setText(toString());
		}else{
			super.setText("");
		}
	}
	
	public void displayToolTips(boolean display){
		if(display){
			this.setToolTipText(toString());
		}
	}
	
	@Override
	public String toString() {
		return key + ":" + value + " "+StringUtils.trimToEmpty(unit);
	}
	
}
