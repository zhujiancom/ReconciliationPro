package com.rci.ui.swing.model;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.rci.bean.dto.PurchaseRecordQueryDTO;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.PurchaseRecordVO;

public class PurchaseRecordTable extends BaseTable<PurchaseRecordVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3207453265865313936L;

	public PurchaseRecordTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	@Override
	protected void setModel() {
		PurchaseRecordTableModel dm = new PurchaseRecordTableModel(columnNum);
		List<PurchaseRecordVO> records = metaservice.displayAllPurchaseRecord();
		dm.setItems(records);
		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("品种名称");
		cm.getColumn(0).setPreferredWidth(250);
		cm.getColumn(1).setHeaderValue("进货日期");
		cm.getColumn(1).setPreferredWidth(150);
		cm.getColumn(2).setHeaderValue("本次进货数量");
		cm.getColumn(2).setPreferredWidth(150);
		cm.getColumn(3).setHeaderValue("进货前数量");
		cm.getColumn(3).setPreferredWidth(150);
		cm.getColumn(4).setHeaderValue("进货后数量");
		cm.getColumn(4).setPreferredWidth(150);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Color.WHITE);
	}
	
	public void query(PurchaseRecordQueryDTO queryDTO){
		PurchaseRecordTableModel dm = (PurchaseRecordTableModel) this.getModel();
		List<PurchaseRecordVO> records = metaservice.queryRecords(queryDTO);
		dm.setItems(records);
		dm.fireTableDataChanged();
	}
	
	public void refresh(){
		PurchaseRecordTableModel dm = (PurchaseRecordTableModel) this.getModel();
		List<PurchaseRecordVO> records = metaservice.displayAllPurchaseRecord();
		dm.setItems(records);
		dm.fireTableDataChanged();
	}
	
	public static class PurchaseRecordTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8057942702796620097L;
		private int columnNum;
		private List<PurchaseRecordVO> items = Collections.emptyList();
		
		public PurchaseRecordTableModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		@Override
		public int getColumnCount() {
			return columnNum;
		}

		@Override
		public int getRowCount() {
			return items.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			PurchaseRecordVO vo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return vo.getIname();
			case 1:
				return DateUtil.date2Str(vo.getPurDate());
			case 2:
				return vo.getPurAmount();
			case 3:
				return vo.getPreBalanceAmount();
			case 4:
				return vo.getAfterBalanceAmount();
			}
			return null;
		}

		public List<PurchaseRecordVO> getItems() {
			return items;
		}

		public void setItems(List<PurchaseRecordVO> items) {
			this.items = items;
		}
		
	}

}
