package com.rci.bean;

import java.io.Serializable;

public class LabelValueBean<T> implements Comparable<LabelValueBean<T>>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1460103000440079908L;
	
	private String label;
	
	private T value;
	
	public LabelValueBean(){
		super();
	}

	public LabelValueBean(String label,T value){
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public int compareTo(LabelValueBean<T> o) {
		return this.getLabel().compareTo(o.getLabel());
	}

	@Override
	public int hashCode() {
		int result = 17;
    	result = result + (value == null ? 0 : value.hashCode());
    	return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
		    return false;
		}
		if(!(obj instanceof LabelValueBean<?>)) {
		    return false;
		}
		LabelValueBean<?> other = (LabelValueBean<?>) obj;
		if (getValue() == null && other.getValue() == null) {
		    return true;
		}
		if (getValue() == null || other.getValue() == null) {
		    return false;
		}
		return this.getValue().equals(other.getValue());
	}

	@Override
	public String toString() {
		return label;
	}

}
