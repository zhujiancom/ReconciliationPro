package com.rci.bean.entity.base;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8132124731461162085L;

	public abstract Serializable getId();

	public abstract Integer getVersion();

	public abstract void setVersion(Integer version);

	@Override
	public Object clone() throws CloneNotSupportedException{
		// TODO Auto-generated method stub
		return super.clone();
	}
}