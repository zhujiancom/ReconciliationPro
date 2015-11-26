package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.ui.swing.vos.InventoryVO;

public class InventoryTable extends BaseTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3691238529428574569L;
	public InventoryTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	@Override
	protected void setModel() {
		InventoryTabelModel dm = new InventoryTabelModel(columnNum);
		List<InventoryVO> inventoryvos = metaservice.displayAllInventory();
		dm.setItems(inventoryvos);
		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("品种名称");
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setHeaderValue("库存编号");
		cm.getColumn(1).setPreferredWidth(80);
		cm.getColumn(2).setHeaderValue("库存总量");
		cm.getColumn(2).setPreferredWidth(80);
		cm.getColumn(3).setHeaderValue("库存余量");
		cm.getColumn(3).setPreferredWidth(80);
		cm.getColumn(4).setHeaderValue("已消费数量");
		cm.getColumn(4).setPreferredWidth(80);
		cm.getColumn(5).setHeaderValue("基数");
		cm.getColumn(5).setPreferredWidth(80);
		cm.getColumn(6).setHeaderValue("已关联菜品");
		cm.getColumn(6).setPreferredWidth(350);
	}
	
	public void reflush(){
		InventoryTabelModel dm = (InventoryTabelModel) this.getModel();
		List<InventoryVO> inventoryvos = metaservice.displayAllInventory();
		dm.setItems(inventoryvos);
		dm.fireTableDataChanged();
	}
	
	public static class InventoryTabelModel extends AbstractTableModel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 742149386495155337L;

		
		private List<InventoryVO> items = Collections.emptyList();
		private int columnNum;
		
		public InventoryTabelModel(){}
		
		public InventoryTabelModel(int columnNum){
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
			InventoryVO vo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return vo.getIname();
			case 1:
				return vo.getIno();
			case 2:
				return vo.getTotalAmount();
			case 3:
				return vo.getBalanceAmount();
			case 4:
				return vo.getConsumeAmount();
			case 5:
				return vo.getCardinal();
			case 6:
				return vo.getRelatedDishNames();
			}
			return null;
		}

		public List<InventoryVO> getItems() {
			return items;
		}

		public void setItems(List<InventoryVO> items) {
			this.items = items;
		}
		
		public InventoryVO getInventory(int rowIndex){
			return items.get(rowIndex);
		}
		
		public void removeRow(int row) {
			items.remove(row);
	        fireTableRowsDeleted(row, row);
	    }
	}

}
