package com.rci.ui.swing.model;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;

public abstract class BaseTable<T> extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7637179254128694436L;
	
	protected int columnNum;
	
	protected List<T> dataList;
	
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
	
	@Override
	public String getToolTipText(MouseEvent event) {
		int row = this.rowAtPoint(event.getPoint());
		int col = this.columnAtPoint(event.getPoint());
		if(row > -1 && col > -1){
			Object value = this.getValueAt(row, col);
			if(null != value && !"".equals(value)){
				this.setToolTipText(value.toString());
			}else{
				this.setToolTipText(null);
			}
		}
		return super.getToolTipText(event);
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
