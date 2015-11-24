/**
 * 
 */
package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.rci.ui.swing.handler.InventoryActionPolicy;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.InventoryTable;
import com.rci.ui.swing.model.InventoryTable.InventoryTabelModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.tab.Tab;
import com.rci.ui.swing.views.component.tab.TabbedPane;
import com.rci.ui.swing.views.component.tab.TabbedPaneListener;
import com.rci.ui.swing.vos.InventoryVO;

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
		JPanel dishlistPanel = new ProductionPanel();
		JPanel purchasePanel = new PurchasePanel();
		URL iconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/16x16/warning.png");
		ImageIcon icon = new ImageIcon(iconUrl);
		TabbedPane tabbedPane = new TabbedPane();
		tabbedPane.addTabbedPaneListener(new TabbedPaneListener() {
			
			@Override
			public void tabSelected(Tab tab, Component component, int index) {
				if(index == 0){
					ProductionPanel pp =  (ProductionPanel) component;
					pp.refresh();
				}
			}
			
			@Override
			public void tabAdded(Tab tab, Component component, int index) {
			}
		});
		tabbedPane.addTab("菜品在售列表", null, dishlistPanel);
		tabbedPane.addTab("进货记录", null, purchasePanel);
		tabbedPane.addTab("沽清警告", icon, new JLabel("测试三"));
		containerPanel.add(tabbedPane);
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
		
		private JTable table;
		private JScrollPane scrollPane;
		private JPanel actionBar;
		private JButton addBtn;
		private JButton delBtn;
		private JButton relatedDishBtn;
		private JButton purchaseBtn;
		private JTextField keywordInput;
		private JButton searchBtn;
		private JButton refreshBtn;
		private DishSelectWin win;

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
			setLayout(new BorderLayout());
			
			table = new InventoryTable(6);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			scrollPane = new JScrollPane();
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);
			add(createActionBar(),BorderLayout.NORTH);
			add(scrollPane,BorderLayout.CENTER);
		}
		
		private JPanel createActionBar(){
			actionBar = new JPanel();
			actionBar.setBorder(BorderFactory.createEmptyBorder());
			actionBar.setLayout(null);
			actionBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			actionBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			actionBar.setBackground(Color.WHITE);
			InventoryActionPolicy policy = new InventoryActionPolicy(table);
			
			addBtn = ButtonFactory.createImageButton("添加","skin/gray/images/24x24/addBtn_0.png", "skin/gray/images/24x24/addBtn_1.png");
			addBtn.setToolTipText("添加");
			addBtn.setBounds(4, 4, 24, 24);
			addBtn.addActionListener(policy.doAddAction());
			
			delBtn = ButtonFactory.createImageButton("停用","skin/gray/images/24x24/delBtn_0.png", "skin/gray/images/24x24/delBtn_1.png");
			delBtn.setToolTipText("停用");
			delBtn.setBounds(40, 4, 24, 24);
			delBtn.addActionListener(policy.doDisableAction());
			
			refreshBtn = ButtonFactory.createImageButton("刷新","skin/gray/images/24x24/refreshBtn_0.png", "skin/gray/images/24x24/refreshBtn_1.png");
			refreshBtn.setToolTipText("刷新");
			refreshBtn.setBounds(80, 4, 24, 24);
			refreshBtn.addActionListener(policy.doReflushAction());
			
			relatedDishBtn = ButtonFactory.createImageButton("关联菜品","skin/gray/images/btn_orange.png",null);
			relatedDishBtn.setToolTipText("关联菜品");
			relatedDishBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			relatedDishBtn.setBounds(120,4,111,24);
			relatedDishBtn.addActionListener(policy.doRelateDishAction());
			
			purchaseBtn = ButtonFactory.createImageButton("进货","skin/gray/images/24x24/restock.png",null);
			purchaseBtn.setBounds(240,4,24,24);
			purchaseBtn.addActionListener(policy.doPurchaseAction());
			
			JLabel keywordLabel = new JLabel("关键字查询：");
			keywordLabel.setBounds(320, 4, 20, 24);
			keywordInput = new JTextField(25);
			keywordInput.setBounds(340,4,30,24);
			searchBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/search_1.png",null);
			searchBtn.setToolTipText("搜索");
			searchBtn.setBounds(380,4,24,24);
			policy.setKeywordInput(keywordInput);
			searchBtn.addActionListener(policy.doQueryAction());
			
			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent event) {
					if(event.getClickCount() == 2){
						int selectIndex = table.getSelectedRow();
						InventoryTabelModel model = (InventoryTabelModel) table.getModel();
						InventoryVO inventoryvo = model.getInventory(selectIndex);
						new InventoryDetailWin(inventoryvo,300,350);
					}
				}
				
			});
			
			actionBar.add(addBtn);
			actionBar.add(delBtn);
			actionBar.add(refreshBtn);
			actionBar.add(relatedDishBtn);
			actionBar.add(purchaseBtn);
			actionBar.add(keywordLabel);
			actionBar.add(keywordInput);
			actionBar.add(searchBtn);
			return actionBar;
		}
		
		public void refresh(){
			((InventoryTable)table).reflush();
			invalidate();
			validate();
			repaint();
		}

		public JTable getTable() {
			return table;
		}

		public void setTable(JTable table) {
			this.table = table;
		}

		public DishSelectWin getWin() {
			return win;
		}

		public void setWin(DishSelectWin win) {
			this.win = win;
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
