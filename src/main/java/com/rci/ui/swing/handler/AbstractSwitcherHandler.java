package com.rci.ui.swing.handler;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public abstract class AbstractSwitcherHandler implements ActionListener {
	protected List<SwitcherElement> elements = new ArrayList<SwitcherElement>();
	
	protected SwitcherElement selectedElement;
	
	public void addElement(SwitcherElement element){
		int len = elements.size();
		element.setIndex(len);
		element.setListener(this);
		elements.add(element);
	}
	
	public void refreshUI(){
		int curIndex = selectedElement.getIndex();
		selectedElement.setSelected(true);
		for(SwitcherElement element:elements){
			if(element.getIndex() != curIndex){
				element.setSelected(false);
				element.repaint();
			}
			selectedElement.repaint();
		}
	}
}
