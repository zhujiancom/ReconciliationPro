package com.rci.metadata.dto;

import com.rci.annotation.ColumnName;

public class DishTypeDTO {
	/* 菜品类型编号 */
	private String typeno;
	
	/* 菜品类型名称 */
	private String typename;
	
	/* 菜品大类 */
	private String seriesno;
	
	@ColumnName("ch_typeno")
	public String getTypeno() {
		return typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	@ColumnName("vch_typename")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@ColumnName("ch_seriesno")
	public String getSeriesno() {
		return seriesno;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}
}
