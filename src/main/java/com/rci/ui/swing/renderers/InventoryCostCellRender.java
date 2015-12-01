package com.rci.ui.swing.renderers;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.InventoryTable.InventoryTabelModel;
import com.rci.ui.swing.vos.InventoryVO;

public class InventoryCostCellRender extends DefaultCellEditor implements FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2195188539660680534L;
	
	private static final Log logger = LogFactory.getLog(InventoryCostCellRender.class);
	
	private JTextField field;
	
	private String ino;
	
	private IMetadataService metaservice;
	
	public InventoryCostCellRender(JTextField field) {
		super(field);
		this.field = field;
		this.field.addFocusListener(this);
		metaservice = (IMetadataService) SpringUtils.getBean("MetadataService");
	}


	@Override
	public void focusGained(FocusEvent paramFocusEvent) {
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row,
			int column) {
		InventoryTabelModel model = (InventoryTabelModel) table.getModel();
		InventoryVO inventory = model.getInventory(row);
		setIno(inventory.getIno());
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}


	@Override
	public void focusLost(FocusEvent event) {
		JTextField field = (JTextField) event.getSource();
		if(StringUtils.hasText(field.getText())){
			try{
				metaservice.costSetting(getIno(),new BigDecimal(field.getText()));
			}catch(Exception ex){
				logger.warn("inventory cost cell render error", ex);
			}
		}
	}

	public String getIno() {
		return ino;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}
}
