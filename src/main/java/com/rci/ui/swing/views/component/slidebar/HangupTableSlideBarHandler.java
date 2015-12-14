/**
 * 
 */
package com.rci.ui.swing.views.component.slidebar;

import java.awt.event.ActionEvent;

import com.rci.ui.swing.HangupOrderPanel.HangupOrderItemInfoPanel;
import com.rci.ui.swing.HangupOrderPanel.HangupTableDetailInfoPanel;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：HangupTableSlideBarListener
 *
 * 包名称：com.rci.ui.swing.views.component.slidebar
 *
 * Create Time: 2015年12月12日 上午11:25:13
 *
 */
public class HangupTableSlideBarHandler extends AbstractSlideBarListener{
	private HangupTableDetailInfoPanel detailInfoPanel;
	
	private HangupOrderItemInfoPanel itemInfoPanel;
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		selectedElement = (SlideElement) event.getSource();
		detailInfoPanel.setSelectedElement(selectedElement);
		itemInfoPanel.setSelectedElement(selectedElement);
		
	}
	public HangupTableDetailInfoPanel getDetailInfoPanel() {
		return detailInfoPanel;
	}
	public HangupOrderItemInfoPanel getItemInfoPanel() {
		return itemInfoPanel;
	}
	public void setDetailInfoPanel(HangupTableDetailInfoPanel detailInfoPanel) {
		this.detailInfoPanel = detailInfoPanel;
	}
	public void setItemInfoPanel(HangupOrderItemInfoPanel itemInfoPanel) {
		this.itemInfoPanel = itemInfoPanel;
	}

}
