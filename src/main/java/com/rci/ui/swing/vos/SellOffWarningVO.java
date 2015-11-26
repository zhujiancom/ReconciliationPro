package com.rci.ui.swing.vos;

import java.util.Date;

import com.rci.enums.BusinessEnums.State;

public class SellOffWarningVO {
	private String ino;

	private String iname;

	private Date soDate;

	private State state;

	public String getIno() {
		return ino;
	}

	public String getIname() {
		return iname;
	}

	public Date getSoDate() {
		return soDate;
	}

	public State getState() {
		return state;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	public void setState(State state) {
		this.state = state;
	}
}
