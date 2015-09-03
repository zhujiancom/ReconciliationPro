package com.rci.ui.swing.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class AbstractLineColorMarkRenderer<T extends AbstractTableModel> extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3415358644047523322L;
	
	private Color color;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		@SuppressWarnings("unchecked")
		T tm = (T) table.getModel();
		boolean markRedFlag = markColor(tm,row); 
		if(markRedFlag){
			if(color == null){
				setBackground(Color.RED);
			}else{
				setBackground(color);
			}
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

	public abstract boolean markColor(T tm,int rowIndex);

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
