package com.rci.ui.swing.views.component.slidebar;

import java.awt.event.ActionEvent;

import org.springframework.util.CollectionUtils;

import com.rci.ui.swing.views.component.DishChooserPanel;

public class SeriesChooseSlideBarHandler extends AbstractSlideBarListener {
	private DishChooserPanel contentPanel;
	@Override
	public void actionPerformed(ActionEvent event) {
		selectedElement = (SlideElement) event.getSource();
		fireUIUpdate(selectedElement);
		contentPanel.setSelectedElement(selectedElement);
	}
	public DishChooserPanel getContentPanel() {
		return contentPanel;
	}
	public void setContentPanel(DishChooserPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#moveTo(com.rci.ui.swing.views.component.slidebar.SlideElement)
	 */
	@Override
	public void moveTo(SlideElement currentElement) {
		// TODO Auto-generated method stub
		
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
		contentPanel.setSelectedElement(currentElement);
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
