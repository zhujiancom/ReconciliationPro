package com.rci.ui.swing.vos;

public class DishSeriesVO {
	private Long dsid;
	
	private String seriesno;
	
	private String seriesname;

	public Long getDsid() {
		return dsid;
	}

	public String getSeriesno() {
		return seriesno;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setDsid(Long dsid) {
		this.dsid = dsid;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}
}
