package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.SaleLogVO;

public class SaleLogTable extends BaseTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6721109476385520283L;
	
	public SaleLogTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	@Override
	protected void setModel() {
		SaleLogTableModel dm = new SaleLogTableModel(columnNum);
		List<SaleLogVO> salelogvos = metaservice.displaySaleLogs();
		dm.setItems(salelogvos);
		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("品种名称");
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setHeaderValue("销售日期");
		cm.getColumn(1).setPreferredWidth(155);
		cm.getColumn(2).setHeaderValue("销售数量");
		cm.getColumn(2).setPreferredWidth(100);
	}
	
	/**
	 * 
	 * Describle(描述)：表数据刷新
	 *
	 * 方法名称：reflushTableData
	 *
	 * 所在类名：SaleLogTable
	 *
	 * Create Time:2015年11月25日 下午4:44:02
	 *  
	 * @param queryDTO
	 */
	public void reflushTableData(SaleLogQueryDTO queryDTO){
		SaleLogTableModel dm = (SaleLogTableModel) this.getModel();
		List<SaleLogVO> salelogvos = metaservice.querySaleLog(queryDTO);
		dm.setItems(salelogvos);
		dm.fireTableDataChanged();
	}
	
	public static class SaleLogTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5751105273231918206L;
		private int columnNum;
		private List<SaleLogVO> items = Collections.emptyList();

		public SaleLogTableModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		@Override
		public int getRowCount() {
			return items.size();
		}

		@Override
		public int getColumnCount() {
			return columnNum;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			SaleLogVO vo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return vo.getIname();
			case 1:
				return DateUtil.date2Str(vo.getSaleDate());
			case 2:
				return vo.getSaleAmount();
			}
			return null;
		}

		public List<SaleLogVO> getItems() {
			return items;
		}

		public void setItems(List<SaleLogVO> items) {
			this.items = items;
		}
		
		public SaleLogVO getItem(int rowIndex){
			if(getRowCount() == 0){
				return null;
			}
			return items.get(rowIndex);
		}
	}

}
