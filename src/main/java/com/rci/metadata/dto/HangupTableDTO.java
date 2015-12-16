package com.rci.metadata.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;

/**
 * 
 * remark (备注): 未结账订单桌号信息
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：HangupTableDTO
 *
 * 包名称：com.rci.metadata.dto
 *
 * Create Time: 2015年12月10日 下午5:11:58
 *
 */
public class HangupTableDTO {
	private String billno;
	
	private String tableName;
	
	private BigDecimal consumAmount;
	
	private Timestamp opendeskTime;

	@ColumnName("ch_billno")
	public String getBillno() {
		return billno;
	}

	@ColumnName("table_name")
	public String getTableName() {
		return tableName;
	}

	@ColumnName("num_cost")
	public BigDecimal getConsumAmount() {
		return consumAmount == null ? BigDecimal.ZERO:consumAmount;
	}

	@ColumnName("opendesk_time")
	public Timestamp getOpendeskTime() {
		return opendeskTime;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setConsumAmount(BigDecimal consumAmount) {
		this.consumAmount = consumAmount;
	}

	public void setOpendeskTime(Timestamp opendeskTime) {
		this.opendeskTime = opendeskTime;
	}

}
