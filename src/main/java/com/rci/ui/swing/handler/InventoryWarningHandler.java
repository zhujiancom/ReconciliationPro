package com.rci.ui.swing.handler;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.QueryFormPanel;

public class InventoryWarningHandler {
	private static IMetadataService metaService;
	static{
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}
	private InventoryWarningHandler(){}
	
	private static class InventoryWarningHandlerHolder{
		private static final InventoryWarningHandler instance = new InventoryWarningHandler();
	}
	
	public static InventoryWarningHandler getInstance(){
		return InventoryWarningHandlerHolder.instance;
	}
	
	private QueryFormPanel displayPanel;

	public QueryFormPanel getDisplayPanel() {
		return displayPanel;
	}

	public void setDisplayPanel(QueryFormPanel displayPanel) {
		this.displayPanel = displayPanel;
	}
	
	public void displayWarningInfo(){
		if(displayPanel == null){
			return;
		}
		if(metaService.hasSellOffWarningInfo()){
			InventoryWarningHandler.getInstance().showWarningInfo("有库存将被沽清，请尽快进货！");
		}else{
			InventoryWarningHandler.getInstance().hideWarningInfo();
		}
	}
	
	public void showWarningInfo(String warningInfo){
		if(displayPanel.isWarningShowing()){
			return;
		}
		displayPanel.displayWarningInfo(warningInfo);
	}
	
	public void hideWarningInfo(){
		if(!displayPanel.isWarningShowing()){
			return;
		}
		displayPanel.removeWarningInfo();
	}
}
