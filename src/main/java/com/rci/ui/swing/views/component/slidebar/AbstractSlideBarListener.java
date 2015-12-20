/**
 * 
 */
package com.rci.ui.swing.views.component.slidebar;

import java.util.ArrayList;
import java.util.List;

import com.rci.ui.swing.views.component.slidebar.SlideElement.State;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AbstarctSlideBarListener
 *
 * 包名称：com.rci.ui.swing.views.component.slidebar
 *
 * Create Time: 2015年12月12日 上午11:16:56
 *
 */
public abstract class AbstractSlideBarListener implements SlideBarListener {
	protected List<SlideElement> elements = new ArrayList<SlideElement>();
	
	protected SlideElement selectedElement;
	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#registerElement(com.rci.ui.swing.views.component.slidebar.SlideElement)
	 */
	@Override
	public  void registerElement(SlideElement element) {
		elements.add(element);
	}

	/* 
	 * @see com.rci.ui.swing.views.component.slidebar.SlideBarListener#fireUIUpdate()
	 */
	@Override
	public void fireUIUpdate(SlideElement currentElement) {
		int curIndex = currentElement.getIndex();
		selectedElement = currentElement;
		for(SlideElement element:elements){
			if(element.getIndex() != curIndex){
				element.setState(State.NORMAL);
				element.setChecked(false);
				element.repaint();
			}
		}
		selectedElement.setState(State.SELECTED);
		selectedElement.setChecked(true);
		selectedElement.repaint();
	}

	public SlideElement getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(SlideElement selectedElement) {
		this.selectedElement = selectedElement;
	}
	
	

}
