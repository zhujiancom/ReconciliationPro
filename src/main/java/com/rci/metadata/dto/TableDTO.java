package com.rci.metadata.dto;

import com.rci.annotation.ColumnName;

/**
 * 
 * remark (备注): 桌号	
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：TableDTO
 *
 * 包名称：com.rci.metadata.dto
 *
 * Create Time: 2015年6月18日 下午2:13:41
 *
 */
public class TableDTO {
	private String tbNo;
	
	private String tbName;
	
	private String tbType;
	
	private String tbTypeName;

	@ColumnName("table_no")
	public String getTbNo() {
		return tbNo;
	}

	public void setTbNo(String tbNo) {
		this.tbNo = tbNo;
	}

	@ColumnName("table_name")
	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	@ColumnName("table_type")
	public String getTbType() {
		return tbType;
	}

	public void setTbType(String tbType) {
		this.tbType = tbType;
	}

	@ColumnName("table_type_name")
	public String getTbTypeName() {
		return tbTypeName;
	}

	public void setTbTypeName(String tbTypeName) {
		this.tbTypeName = tbTypeName;
	}
}
