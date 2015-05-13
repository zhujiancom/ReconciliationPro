package com.rci.ui.swing.model;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

public class DZDPSchemeTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1397324428014908211L;
	
	public DZDPSchemeTable(){
//		setHeaderLabel();
	}
	
	/**
	 * 
	 * Describle(描述)： 设置表格标题烂
	 *
	 * 方法名称：setHeaderLabel
	 *
	 * 所在类名：DZDPSchemeTable
	 *
	 * Create Time:2015年5月13日 下午2:42:10
	 *
	 */
	public void setHeaderLabel(){
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("方案名称");
		cm.getColumn(0).setPreferredWidth(150);
		cm.getColumn(1).setHeaderValue("使用平台");
		cm.getColumn(1).setPreferredWidth(75);
		cm.getColumn(2).setHeaderValue("优惠金额");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("入账金额");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("商家承受金额");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(5).setHeaderValue("方案类型");
		cm.getColumn(5).setPreferredWidth(75);
		cm.getColumn(6).setHeaderValue("开始时间");
		cm.getColumn(6).setPreferredWidth(140);
		cm.getColumn(7).setHeaderValue("结束时间");
		cm.getColumn(7).setPreferredWidth(140);
		cm.getColumn(8).setHeaderValue("状态");
		cm.getColumn(8).setPreferredWidth(70);
	}
}
