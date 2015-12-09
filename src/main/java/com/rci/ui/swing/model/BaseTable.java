package com.rci.ui.swing.model;

import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;

public abstract class BaseTable<T> extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7637179254128694436L;
	
	protected static final Log logger = LogFactory.getLog(BaseTable.class);
	
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

	/**
	 * Describle(描述)：自适应表格列宽度
	 *
	 * 方法名称：adaptiveColumns
	 *
	 * 所在类名：BaseTable
	 *
	 * Create Time:2015年12月9日 上午11:07:17
	 *  
	 * @param table
	 */
	protected void adaptiveColumns(JTable table){
		JTableHeader header = table.getTableHeader();
		int rowcount = table.getRowCount();
		Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		while(columns.hasMoreElements()){
			TableColumn column = columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) header.getDefaultRenderer()
						.getTableCellRendererComponent(table, column.getIdentifier(), false, false, -1, col)
						.getPreferredSize()
						.getWidth();
			for(int row=0;row < rowcount;row++){
				int preferedWidth = (int) table.getCellRenderer(row, col)
									.getTableCellRendererComponent(table, table.getValueAt(row, col), false, false, row, col)
									.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column);
			column.setWidth(width+table.getIntercellSpacing().width);
		}
	}
}
