package com.rci.bean.entity.base;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8132124731461162085L;
	
	private Integer version;

	@Transient
	public abstract Serializable getId();

	@Version
	public Integer getVersion(){
		return version;
	};

	public void setVersion(Integer version){
		this.version = version;
	};

	@Override
	public Object clone() throws CloneNotSupportedException{
		// TODO Auto-generated method stub
		return super.clone();
	}
}