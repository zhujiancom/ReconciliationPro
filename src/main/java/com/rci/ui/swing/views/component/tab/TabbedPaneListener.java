package com.rci.ui.swing.views.component.tab;

import java.awt.Component;

public interface TabbedPaneListener {
	 /**
     * Called when the tab is selected by the user.
     *
     * @param tab       the Tab selected.
     * @param component the child component of the tab.
     * @param index     the index of the tab.
     */
	void tabSelected(Tab tab, Component component, int index);
	
	/**
     * Called when a new <code>Tab</code> has been added.
     *
     * @param tab       the new Tab added.
     * @param component the child component of the tab.
     * @param index     the index of the tab.
     */
	void tabAdded(Tab tab, Component component, int index);
}
