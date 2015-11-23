/**
 * 
 */
package com.rci.ui.swing.views.component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.rci.ui.swing.views.PopWindow;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：InventoryManagementWin
 *
 * 包名称：com.rci.ui.swing.views.component
 *
 * Create Time: 2015年11月21日 下午3:52:43
 *
 */
public class InventoryManagementWin extends PopWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2157594066336833436L;

	public InventoryManagementWin(int width,int height,String title){
		super(width,height,title);
		createContent();
	}

	/**
	 *
	 * Describle(描述)：
	 *
	 * 方法名称：createContentPanel
	 *
	 * 所在类名：InventoryManagementWin
	 *
	 * Create Time:2015年11月21日 下午3:54:29
	 *   
	 */
	private void createContent() {
		JPanel containerPanel = this.getContainerPanel();
		JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP);
		JPanel dishlistPanel = new ProductionPanel();
		JPanel purchasePanel = new PurchasePanel();
		tabPanel.add(dishlistPanel,"菜品在售列表");
		tabPanel.add(purchasePanel,"进货记录");
		containerPanel.add(tabPanel);
	}
	
	/**
	 * 
	 * remark (备注): 产品在售列表面板
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：ProductionPanel
	 *
	 * 包名称：com.rci.ui.swing.views.component
	 *
	 * Create Time: 2015年11月21日 下午4:35:46
	 *
	 */
	public class ProductionPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ProductionPanel(){
			initComponent();
		}

		/**
		 *
		 * Describle(描述)：初始化面板
		 *
		 * 方法名称：initComponent
		 *
		 * 所在类名：ProductionPanel
		 *
		 * Create Time:2015年11月21日 下午4:07:27
		 *   
		 */
		private void initComponent() {
			this.add(new JLabel("菜品在售列表"));
		}
	}
	
	/**
	 * 
	 * remark (备注):进货面板
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：PurchasePanel
	 *
	 * 包名称：com.rci.ui.swing.views.component
	 *
	 * Create Time: 2015年11月21日 下午4:37:04
	 *
	 */
	public class PurchasePanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PurchasePanel(){
			initComponent();
		}

		/**
		 *
		 * Describle(描述)：
		 *
		 * 方法名称：initComponent
		 *
		 * 所在类名：PurchasePanel
		 *
		 * Create Time:2015年11月22日 下午3:13:44
		 *   
		 */
		private void initComponent() {
			this.add(new JLabel("进货记录"));			
		}
	}
}
