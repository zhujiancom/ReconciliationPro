package com.rci.ui.swing.views.component.slidebar;

import java.awt.event.ActionEvent;

import com.rci.ui.swing.views.component.DishChooserPanel;

public class SeriesChooseSlideBarHandler extends AbstractSlideBarListener {
	private DishChooserPanel contentPanel;
	@Override
	public void actionPerformed(ActionEvent event) {
		selectedElement = (SlideElement) event.getSource();
		contentPanel.setSelectedElement(selectedElement);
	}
	public DishChooserPanel getContentPanel() {
		return contentPanel;
	}
	public void setContentPanel(DishChooserPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

}
