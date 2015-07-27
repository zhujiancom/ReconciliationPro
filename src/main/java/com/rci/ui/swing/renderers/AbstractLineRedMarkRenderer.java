package com.rci.ui.swing.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class AbstractLineRedMarkRenderer<T extends AbstractTableModel> extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3415358644047523322L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		@SuppressWarnings("unchecked")
		T tm = (T) table.getModel();
		boolean markRedFlag = markRed(tm,row); 
		if(markRedFlag){
			setBackground(Color.RED);
			setForeground(Color.WHITE);
		}else{
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
		}
		if(markRedFlag && isSelected){
			table.setSelectionBackground(Color.ORANGE);
			table.setSelectionForeground(Color.WHITE);
		}else{
			table.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
			table.setSelectionForeground(UIManager.getColor("Table.selectionForeground"));
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
	}

	public abstract boolean markRed(T tm,int rowIndex);
}
