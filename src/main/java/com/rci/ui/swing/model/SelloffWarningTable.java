package com.rci.ui.swing.model;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.rci.enums.BusinessEnums.State;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.ui.swing.vos.SellOffWarningVO;

public class SelloffWarningTable extends BaseTable<SellOffWarningVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1457952416943066236L;
	
	public SelloffWarningTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	@Override
	protected void setModel() {
		SelloffWarningTableModel dm = new SelloffWarningTableModel(columnNum);
		List<SellOffWarningVO> datalist = metaservice.displayAllSellOffWarning();
		dm.setItems(datalist);
		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("品种名称");
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setHeaderValue("沽清日期");
		cm.getColumn(1).setPreferredWidth(155);
		cm.getColumn(2).setHeaderValue("库存数量");
		cm.getColumn(2).setPreferredWidth(100);
		cm.getColumn(3).setHeaderValue("最近进货日期");
		cm.getColumn(3).setPreferredWidth(150);
		cm.getColumn(4).setHeaderValue("进货数量");
		cm.getColumn(4).setPreferredWidth(100);
		cm.getColumn(5).setHeaderValue("沽清状态");
		cm.getColumn(5).setPreferredWidth(100);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Color.WHITE);
	}
	
	public void reflushTableData(){
		SelloffWarningTableModel dm  = (SelloffWarningTableModel) this.getModel();
		List<SellOffWarningVO> datalist = metaservice.displayAllSellOffWarning();
		dm.setItems(datalist);
		dm.fireTableDataChanged();
	}
	
	public static class SelloffWarningTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1393667194346947412L;

		private int columnNum;
		
		private List<SellOffWarningVO> items = Collections.emptyList();
		
		public SelloffWarningTableModel(int columnNum){
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
			SellOffWarningVO vo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return vo.getIname();
			case 1:
				return DateUtil.date2Str(vo.getSoDate());
			case 2:
				return vo.getBalanceAmount();
			case 3:
				return DateUtil.date2Str(vo.getpDate());
			case 4:
				return vo.getPurchaseAmount();
			case 5:
				if(State.VALID.equals(vo.getState())){
					return "<html><font color='red'>"+EnumUtils.getEnumMessage(vo.getState())+"</font></html>";
				}else{
					return "<html><font color='#DCDCDC'>"+EnumUtils.getEnumMessage(vo.getState())+"</font></html>";
				}
			}
			return null;
		}

		public List<SellOffWarningVO> getItems() {
			return items;
		}

		public void setItems(List<SellOffWarningVO> items) {
			this.items = items;
		}
	}
}
