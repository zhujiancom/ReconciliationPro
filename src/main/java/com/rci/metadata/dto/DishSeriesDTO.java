package com.rci.metadata.dto;

import com.rci.annotation.ColumnName;

public class DishSeriesDTO {
	private String seriesno;
	
	private String seriesname;

	@ColumnName("ch_seriesno")
	public String getSeriesno() {
		return seriesno;
	}

	@ColumnName("vch_seriesname")
	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}
	
	
}
