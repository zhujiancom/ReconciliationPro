package com.rci.ui.swing.model;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;

public abstract class BaseTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7637179254128694436L;
	
	protected int columnNum;
	
	protected static final IMetadataService metaservice;
	
	static{
		metaservice = (IMetadataService) SpringUtils.getBean("MetadataService");
	}

	public BaseTable(){
		this.setRowHeight(30);
	}
	
	public BaseTable(int columnNum){
		this();
		this.columnNum = columnNum;
		setModel();
		setHeaderLabel();
	}
	
	public BaseTable(TableModel dataModel){
		super(dataModel);
		setHeaderLabel();
		this.setRowHeight(30);
	}
	
	
	
	/**
	 * 
	 * Describle(描述)： 装载data Model
	 *
	 * 方法名称：setModel
	 *
	 * 所在类名：BaseTable
	 *
	 * Create Time:2015年7月31日 下午1:17:27
	 *
	 */
	protected abstract void setModel();
	
	/**
	 * 
	 * Describle(描述)：设置 table header
	 *
	 * 方法名称：setHeaderLabel
	 *
	 * 所在类名：BaseTable
	 *
	 * Create Time:2015年7月31日 下午1:17:54
	 *
	 */
	protected abstract void setHeaderLabel();
}
