package com.rci.ui.swing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.tools.StringUtils;
import com.rci.ui.swing.renderers.InventoryCostCellRender;
import com.rci.ui.swing.renderers.InventoryWarningLineCellRender;
import com.rci.ui.swing.vos.InventoryVO;

public class InventoryTable extends BaseTable<InventoryVO> {
	
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
		cm.getColumn(6).setHeaderValue("成本价");
		cm.getColumn(6).setPreferredWidth(80);
		InventoryCostCellRender render = new InventoryCostCellRender(new JTextField());
		cm.getColumn(6).setCellEditor(render);
		cm.getColumn(7).setHeaderValue("警戒线");
		cm.getColumn(7).setPreferredWidth(80);
		InventoryWarningLineCellRender warningLineRender = new InventoryWarningLineCellRender(new JTextField());
		cm.getColumn(7).setCellEditor(warningLineRender);
		cm.getColumn(8).setHeaderValue("已关联菜品");
		cm.getColumn(8).setPreferredWidth(350);
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
				return vo.getTotalAmount().divide(vo.getCardinal(), 1, RoundingMode.HALF_EVEN);
			case 3:
				BigDecimal balance = vo.getBalanceAmount().divide(vo.getCardinal(), 1, RoundingMode.HALF_EVEN);
				if(balance.compareTo(BigDecimal.ZERO) <= 0){
					return "<html><font color='red'>"+balance+"</font></html>";
				}
				if(vo.getWarningLine() != null){
					BigDecimal warningLine = vo.getWarningLine().divide(vo.getCardinal(), 1, RoundingMode.HALF_EVEN);
					if(balance.compareTo(warningLine) <= 0){
						return "<html><font color='red'>"+balance+"</font></html>";
					}
				}
				return balance;
			case 4:
				return "<html><font color='blue'>"+vo.getConsumeAmount().divide(vo.getCardinal(), 1, RoundingMode.HALF_EVEN)+"</font></html>";
			case 5:
				return vo.getCardinal();
			case 6:
				return vo.getCost();
			case 7:
				if(vo.getWarningLine() == null){
					return null;
				}
				return vo.getWarningLine().divide(vo.getCardinal(), 1, RoundingMode.HALF_EVEN);
			case 8:
				return vo.getRelatedDishNames();
			}
			return null;
		}

		@Override
		public void setValueAt(Object paramObject, int rowIndex, int columnIndex) {
			InventoryVO vo = items.get(rowIndex);
			if(StringUtils.hasText(paramObject.toString())){
				try{
					if(columnIndex == 6){
						vo.setCost(new BigDecimal(paramObject.toString()));
					}
					if(columnIndex == 7){
						BigDecimal warningLine = new BigDecimal(paramObject.toString());
						vo.setWarningLine(warningLine.multiply(vo.getCardinal()));
					}
					this.fireTableCellUpdated(rowIndex, columnIndex);
				}
				catch(Exception ex){
					logger.warn("数据格式错误", ex);
				}
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 6 || columnIndex == 7){
				return true;
			}
			return super.isCellEditable(rowIndex, columnIndex);
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
