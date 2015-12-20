/**
 * 
 */
package com.rci.ui.swing.views.component.slidebar;

import java.awt.event.ActionEvent;

import org.springframework.util.CollectionUtils;

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
		fireUIUpdate(selectedElement);
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
	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#moveTo(com.rci.ui.swing.views.component.slidebar.SlideElement)
	 */
	@Override
	public void moveTo(SlideElement currentElement) {
		fireUIUpdate(currentElement);
		detailInfoPanel.setSelectedElement(currentElement);
		itemInfoPanel.setSelectedElement(currentElement);
	}
	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#getElementCount()
	 */
	@Override
	public Integer getElementCount() {
		if(CollectionUtils.isEmpty(elements)){
			return 0;
		}
		return elements.size();
	}
	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#moveTo(int)
	 */
	@Override
	public void moveTo(int index) {
		SlideElement currentElement = elements.get(index);
		fireUIUpdate(currentElement);
		detailInfoPanel.setSelectedElement(currentElement);
		itemInfoPanel.setSelectedElement(currentElement);
		currentElement.requestFocus();
	}
	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#cleanAllElements()
	 */
	@Override
	public void cleanAllElements() {
		elements.clear();
	}
}
