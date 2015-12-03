package com.rci.ui.swing.vos;

import java.math.BigDecimal;

public class InventoryDishRefVO {
	private Long idrid;
	
	private String dishno;
	
	private String ino;
	
	private BigDecimal standard;
	
	private String iname;

	public Long getIdrid() {
		return idrid;
	}

	public void setIdrid(Long idrid) {
		this.idrid = idrid;
	}

	public String getDishno() {
		return dishno;
	}

	public String getIno() {
		return ino;
	}

	public BigDecimal getStandard() {
		return standard;
	}

	public String getIname() {
		return iname;
	}

	public void setDishno(String dishno) {
		this.dishno = dishno;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}
	
	
}
