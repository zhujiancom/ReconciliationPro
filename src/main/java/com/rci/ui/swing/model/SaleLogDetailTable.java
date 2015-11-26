package com.rci.ui.swing.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.SaleLogDetailVO;

public class SaleLogDetailTable extends BaseTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3404506166273564845L;

	public SaleLogDetailTable(SaleLogDetailTableModel model){
		super(model);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	@Override
	protected void setModel() {
//		SaleLogDetailTableModel dm = new SaleLogDetailTableModel(columnNum);
//		List<SaleLogDetailVO> salelogdetailvos = metaservice.displaySaleLogDetails(ino);
//		dm.setItems(salelogdetailvos);
//		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("付款编号");
		cm.getColumn(0).setPreferredWidth(160);
		cm.getColumn(1).setHeaderValue("菜品名称");
		cm.getColumn(1).setPreferredWidth(100);
		cm.getColumn(2).setHeaderValue("消费日期");
		cm.getColumn(2).setPreferredWidth(150);
		cm.getColumn(3).setHeaderValue("销售数量");
		cm.getColumn(3).setPreferredWidth(100);
	}
	
	public void reflushTableData(SaleLogQueryDTO queryDTO){
		SaleLogDetailTableModel dm = (SaleLogDetailTableModel) this.getModel();
		List<SaleLogDetailVO> salelogdetailvos = metaservice.querySaleLogDetail(queryDTO);
		dm.setItems(salelogdetailvos);
		dm.fireTableDataChanged();
	}

	public static class SaleLogDetailTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6640460905676731061L;
		private int columnNum;
		private List<SaleLogDetailVO> items = new ArrayList<SaleLogDetailVO>();

		public SaleLogDetailTableModel(int columnNum){
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
			SaleLogDetailVO vo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return vo.getPayno();
			case 1:
				return vo.getDishname();
			case 2:
				return DateUtil.time2Str(vo.getCheckoutTime());
			case 3:
				return vo.getConsumeAmount();
			}
			return null;
		}

		public List<SaleLogDetailVO> getItems() {
			return items;
		}

		public void setItems(List<SaleLogDetailVO> items) {
			this.items = items;
		}
		
	}
}
