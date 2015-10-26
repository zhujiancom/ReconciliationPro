/**
 * 
 */
package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.ui.swing.model.SchemeStatusRadioButton;
import com.rci.ui.swing.model.SchemeTable;
import com.rci.ui.swing.model.VendorJCheckBox;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：VendorCheckListener
 *
 * 包名称：com.rci.ui.swing.listeners
 *
 * Create Time: 2015年10月4日 下午4:31:07
 *
 */
public class VendorCheckListener implements ItemListener,ActionListener {
	private JTable table;
	private List<VendorJCheckBox> items = new ArrayList<VendorJCheckBox>();
	private VendorJCheckBox vendor;
	private SchemeStatusRadioButton status;
	
	public VendorCheckListener(JTable table){
		this.table = table;
	}
	/* 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		vendor = (VendorJCheckBox) e.getItemSelectable();
		if(e.getStateChange() == ItemEvent.SELECTED){
			refreshTableData();
		}
	}
	
	public void addCheckBox(VendorJCheckBox checkBox){
		items.add(checkBox);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		status = (SchemeStatusRadioButton) e.getSource();
		SchemeQueryDTO queryDTO = new SchemeQueryDTO();
		queryDTO.setStatus(getStatus().getStatus());
		queryDTO.setVendor(getVendor().getVendor());
		refreshTableData();
	}
	
	/**
	 * 
	 * Describle(描述)： 刷新表格数据
	 *
	 * 方法名称：refreshTableData
	 *
	 * 所在类名：VendorCheckListener
	 *
	 * Create Time:2015年10月22日 上午11:08:19
	 *
	 */
	public void refreshTableData(){
		SchemeQueryDTO queryDTO = new SchemeQueryDTO();
		queryDTO.setStatus(getStatus().getStatus());
		queryDTO.setVendor(getVendor().getVendor());
		((SchemeTable)table).refresh(queryDTO);
	}
	
	public VendorJCheckBox getVendor() {
		if(vendor == null){
			return new VendorJCheckBox(0,null,"全部");
		}
		return vendor;
	}
	public void setVendor(VendorJCheckBox vendor) {
		this.vendor = vendor;
	}
	public SchemeStatusRadioButton getStatus() {
		if(status == null){
			return new SchemeStatusRadioButton(ActivityStatus.ACTIVE, "进行中");
		}
		return status;
	}
	public void setStatus(SchemeStatusRadioButton status) {
		this.status = status;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
}
