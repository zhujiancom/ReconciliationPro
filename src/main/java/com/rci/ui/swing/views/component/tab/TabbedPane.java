package com.rci.ui.swing.views.component.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 871686191220726691L;
	private static final String NAME="TabbedPane";
	
	private List<TabbedPaneListener> listeners = new ArrayList<TabbedPaneListener>();
	
	private JTabbedPane pane = null;

	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	/**
	 * The default Text Cursor.
	 */
	public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public TabbedPane(){
		this(JTabbedPane.TOP);
	}
	
	public TabbedPane(final int type){
		pane = new JTabbedPane(type);
		pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		setLayout(new BorderLayout());
		add(pane);
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if (index >= 0) {
					fireTabSelected(getTabAt(index), getTabAt(index).getComponent(), index);
				}
			}
		};
		pane.addChangeListener(changeListener);
	}
	
	public Tab getTabContainingComponent(Component component) {
		for (Component comp : pane.getComponents()) {
			if (comp instanceof Tab) {
				Tab tab = (Tab) comp;
				if (tab.getComponent() == component)
					return tab;
			}
		}
		return null;
	}
	
	public void fireTabSelected(Tab tab, Component component, int index) {
		for (TabbedPaneListener listener : listeners) {
			listener.tabSelected(tab, component, index);
		}
	}
	
	public void fireTabAdded(Tab tab, Component component, int index) {
		for (TabbedPaneListener listener : listeners) {
			listener.tabAdded(tab, component, index);
		}
	}
	
	public void addTabbedPaneListener(TabbedPaneListener listener) {
		listeners.add(listener);
	}

	public void removeTabbedPaneListener(TabbedPaneListener listener) {
		listeners.remove(listener);
	}
	
	public Tab getTabAt(int index) {
		return ((Tab) pane.getComponentAt(index));
	}
	
	/**
	 * 
	 * Describle(描述)：获取所选tab的index
	 *
	 * 方法名称：getTabPosition
	 *
	 * 所在类名：TabbedPane
	 *
	 * Create Time:2015年11月23日 上午10:27:50
	 *  
	 * @param tab
	 * @return
	 */
	public int getTabPosition(Tab tab) {
		return pane.indexOfComponent(tab);
	}
	
	public Tab addTab(String title, Icon icon, final Component component) {
		return addTab(title, icon, component, null);
	}
	
	public Tab addTab(String title, Icon icon, final Component component,
			String tip) {
		final Tab tab = new Tab(this, component);

		TabPanel tabpanel = new TabPanel(tab, title, icon);
		pane.addTab(null, null, tab, tip);
		
		pane.setTabComponentAt(pane.getTabCount() - 1, tabpanel);
		fireTabAdded(tab, component, getTabPosition(tab));
		return tab;
	}
	
	public void setTitleAt(int index, String title) {
		if (index > 0) {
			Component com = pane.getTabComponentAt(index);
			if (com instanceof TabPanel) {
				TabPanel panel = (TabPanel) com;
				panel.setTitle(title);
			}
		}
	}
	
	public void setIconAt(int index, Icon icon) {
		Component com = pane.getTabComponentAt(index);
		if (com instanceof TabPanel) {
			TabPanel panel = (TabPanel) com;
			panel.setIcon(icon);
		}
	}
	
	private class TabPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = -2236246381068771702L;
		private final BorderLayout layout = new BorderLayout();
		private final Font defaultFont = new Font("微软雅黑", Font.PLAIN, 11);
		private JLabel iconLabel;
		private JLabel titleLabel;
		
		public TabPanel(final Tab tab, String title, Icon icon) {
			setPreferredSize(new Dimension(100,20));
			setOpaque(false);		
			this.setLayout(layout);
			titleLabel = new JLabel(title);
			titleLabel.setFont(defaultFont);
			if (icon != null)
			{
				iconLabel = new JLabel(icon);
				add(iconLabel, BorderLayout.EAST);
			}
			add(titleLabel, BorderLayout.CENTER);
//			if (closeEnabled) {
//				final JLabel tabCloseButton = new JLabel(
//						closeInactiveButtonIcon);
//				tabCloseButton.addMouseListener(new MouseAdapter() {
//					public void mouseEntered(MouseEvent mouseEvent) {
//						tabCloseButton.setIcon(closeActiveButtonIcon);
//						setCursor(HAND_CURSOR);
//					}
//
//					public void mouseExited(MouseEvent mouseEvent) {
//						tabCloseButton.setIcon(closeInactiveButtonIcon);
//						setCursor(DEFAULT_CURSOR);
//					}
//
//					public void mousePressed(MouseEvent mouseEvent) {
//						new Thread(){
//							@Override
//							public void run() {
//								try {
//									Thread.sleep(100);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//								close(tab);
//							}
//						}.start();
//					}
//				});
//				add(tabCloseButton, BorderLayout.EAST);
//			}	
		}
		public Font getDefaultFont() {
			return defaultFont;
		}

		public void setIcon(Icon icon) {
			iconLabel.setIcon(icon);
		}

		public void setTitle(String title) {
			titleLabel.setText(title);
		}
		public void setTitleColor(Color color) {
			titleLabel.setForeground(color);
			titleLabel.validate();
			titleLabel.repaint();
		}
	}
}
