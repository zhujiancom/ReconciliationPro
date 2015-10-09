/**
 * 
 */
package com.rci.ui.swing.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JTable;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.model.SchemeTable.SchemeTabelModel;
import com.rci.ui.swing.model.VendorJCheckBox;
import com.rci.ui.swing.vos.SchemeVO;

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
public class VendorCheckListener implements ItemListener {
	private JTable table;
	private IMetadataService metaservice;
	
	public VendorCheckListener(JTable table){
		this.table = table;
		metaservice = (IMetadataService) SpringUtils.getBean("MetadataService");
	}
	/* 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		VendorJCheckBox source = (VendorJCheckBox) e.getItemSelectable();
		if(e.getStateChange() == ItemEvent.SELECTED){
			SchemeQueryDTO queryDTO = new SchemeQueryDTO();
			queryDTO.setStatus(ActivityStatus.ACTIVE);
//			if(source.getVendor() == null){
//			}
			queryDTO.setVendor(source.getVendor());
			List<SchemeVO> schemes = metaservice.dishplaySchemes(queryDTO);
			SchemeTabelModel dm = (SchemeTabelModel) table.getModel();
			dm.setItems(schemes);
			dm.fireTableDataChanged();
		}
	}

}
