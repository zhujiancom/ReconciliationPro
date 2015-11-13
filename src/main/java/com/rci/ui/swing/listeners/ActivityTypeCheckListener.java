package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTable;

import com.rci.bean.dto.SchemeTypeQueryDTO;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.ui.swing.model.ActivityTypeJCheckBox;
import com.rci.ui.swing.model.SchemeStatusRadioButton;
import com.rci.ui.swing.model.SchemeTypeTable;

public class ActivityTypeCheckListener implements ItemListener, ActionListener {
	private JTable table;
//	private List<ActivityTypeJCheckBox> items = new ArrayList<ActivityTypeJCheckBox>();
	private ActivityTypeJCheckBox selectedItem;
	private SchemeStatusRadioButton status;
	
	public ActivityTypeCheckListener(JTable table){
		this.table = table;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		status = (SchemeStatusRadioButton) e.getSource();
		refreshTableData();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		selectedItem = (ActivityTypeJCheckBox) e.getItemSelectable();
		if(e.getStateChange() == ItemEvent.SELECTED){
			refreshTableData();
		}
	}
	
	/**
	 * 
	 * Describle(描述)： 刷新表格数据
	 *
	 * 方法名称：refreshTableData
	 *
	 * 所在类名：ActivityTypeCheckListener
	 *
	 * Create Time:2015年11月12日 下午5:21:48
	 *
	 */
	public void refreshTableData(){
		SchemeTypeQueryDTO queryDTO = new SchemeTypeQueryDTO(getSelectedItem().getActivityType(),getStatus().getStatus());
		((SchemeTypeTable)table).refresh(queryDTO);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public ActivityTypeJCheckBox getSelectedItem() {
		if(selectedItem == null){
			return new ActivityTypeJCheckBox(0,null,"全部");
		}
		return selectedItem;
	}

	public SchemeStatusRadioButton getStatus() {
		if(status == null){
			return new SchemeStatusRadioButton(ActivityStatus.ACTIVE, "进行中");
		}
		return status;
	}

	public void setSelectedItem(ActivityTypeJCheckBox selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setStatus(SchemeStatusRadioButton status) {
		this.status = status;
	}
}
