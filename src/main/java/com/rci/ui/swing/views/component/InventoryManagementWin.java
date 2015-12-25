/**
 * 
 */
package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.PurchaseRecordQueryDTO;
import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.handler.InventoryActionPolicy;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.InventoryTable;
import com.rci.ui.swing.model.PurchaseRecordTable;
import com.rci.ui.swing.model.SaleLogDetailTable;
import com.rci.ui.swing.model.SaleLogDetailTable.SaleLogDetailTableModel;
import com.rci.ui.swing.model.SaleLogTable;
import com.rci.ui.swing.model.SaleLogTable.SaleLogTableModel;
import com.rci.ui.swing.model.SelloffWarningTable;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.encapsulation.BaseTextField;
import com.rci.ui.swing.views.component.encapsulation.DateTextField;
import com.rci.ui.swing.views.component.tab.Tab;
import com.rci.ui.swing.views.component.tab.TabbedPane;
import com.rci.ui.swing.views.component.tab.TabbedPaneListener;
import com.rci.ui.swing.vos.SaleLogVO;

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
		containerPanel.setBackground(Color.WHITE);
		JPanel dishlistPanel = new ProductionPanel();
		JPanel purchasePanel = new PurchasePanel();
		JPanel salePanel = new SalePanel(this.getWidth(),this.getHeight());
		JPanel selloffPanel = new SelloffPanel();
		URL iconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/16x16/warning.png");
		ImageIcon icon = new ImageIcon(iconUrl);
		TabbedPane tabbedPane = new TabbedPane();
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.addTabbedPaneListener(new TabbedPaneListener() {
			
			@Override
			public void tabSelected(Tab tab, Component component, int index) {
				if(index == 0){
					ProductionPanel pp =  (ProductionPanel) component;
					pp.refresh();
				}
				if(index == 1){
					PurchasePanel purchasePanel =  (PurchasePanel) component;
					purchasePanel.refresh();
				}
				if(index == 2){
					SalePanel salePane = (SalePanel) component;
					salePane.refresh();
				}
				if(index == 3){
					SelloffPanel warningPanel = (SelloffPanel) component;
					warningPanel.refresh();
				}
			}
			
			@Override
			public void tabAdded(Tab tab, Component component, int index) {
			}
		});
		tabbedPane.addTab("菜品在售列表", null, dishlistPanel);
		tabbedPane.addTab("进货记录", null, purchasePanel);
		tabbedPane.addTab("销售记录", null, salePanel);
		tabbedPane.addTab("沽清警告", icon, selloffPanel);
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
		private BaseTextField keywordInput;
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
			
			table = new InventoryTable(9);
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
			actionBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			actionBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			actionBar.setBackground(Color.WHITE);
			InventoryActionPolicy policy = new InventoryActionPolicy(table);
			
			addBtn = ButtonFactory.createImageButton("添加","skin/gray/images/24x24/addBtn_0.png", "skin/gray/images/24x24/addBtn_1.png");
			addBtn.setToolTipText("添加");
			addBtn.addActionListener(policy.doAddAction());
			
			delBtn = ButtonFactory.createImageButton("停用","skin/gray/images/24x24/delBtn_0.png", "skin/gray/images/24x24/delBtn_1.png");
			delBtn.setToolTipText("停用");
			delBtn.addActionListener(policy.doDisableAction());
			
			refreshBtn = ButtonFactory.createImageButton("刷新","skin/gray/images/24x24/refreshBtn_0.png", "skin/gray/images/24x24/refreshBtn_1.png");
			refreshBtn.setToolTipText("刷新");
			refreshBtn.addActionListener(policy.doReflushAction());
			
			relatedDishBtn = ButtonFactory.createImageButton("关联菜品","skin/gray/images/btn_orange.png",null);
			relatedDishBtn.setToolTipText("关联菜品");
			relatedDishBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			relatedDishBtn.addActionListener(policy.doRelateDishAction());
			
			purchaseBtn = ButtonFactory.createImageButton("进货","skin/gray/images/24x24/restock.png",null);
			purchaseBtn.addActionListener(policy.doPurchaseAction());
			
			JLabel keywordLabel = new JLabel("关键字查询：");
			keywordInput = new BaseTextField("品种名称");
			searchBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/search_1.png",null);
			searchBtn.setToolTipText("搜索");
			policy.setKeywordInput(keywordInput);
			searchBtn.addActionListener(policy.doQueryAction());
			
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
	public class PurchasePanel extends JPanel implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTable table;
		private JScrollPane scrollPane;
		private JPanel actionBar;
		private BaseTextField keywordInput;
		private DateTextField timeInput;
		private JButton searchBtn;
		
		public PurchasePanel(){
			initComponent();
		}

		private JPanel createActionBar(){
			actionBar = new JPanel();
			actionBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			actionBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			actionBar.setBackground(Color.WHITE);
			
//			JLabel dateLabel = new JLabel("日期");
			timeInput = new DateTextField("查询日期");
			JLabel keywordLabel = new JLabel("关键字查询：");
			keywordInput = new BaseTextField("品种名称");
			searchBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/search_1.png",null);
			searchBtn.setToolTipText("搜索");
			searchBtn.addActionListener(this);
			searchBtn.registerKeyboardAction(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
					JComponent.WHEN_IN_FOCUSED_WINDOW);
//			actionBar.add(dateLabel);
			actionBar.add(timeInput);
			actionBar.add(keywordLabel);
			actionBar.add(keywordInput);
			actionBar.add(searchBtn);
			return actionBar;
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
			setLayout(new BorderLayout());
			table = new PurchaseRecordTable(5);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
			scrollPane = new JScrollPane();
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);
			scrollPane.getViewport().setBackground(Color.WHITE);
			add(createActionBar(),BorderLayout.NORTH);
			add(scrollPane,BorderLayout.CENTER);
		}
		
		public void refresh(){
			((PurchaseRecordTable)table).refresh();
		}

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			String keyword = keywordInput.getText();
			String time = timeInput.getText();
			PurchaseRecordQueryDTO queryDTO = new PurchaseRecordQueryDTO();
			if(StringUtils.hasText(time)){
				try {
					Date queryDate = DateUtil.parseDate(time, "yyyyMMdd");
					queryDTO.setPurDate(queryDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(StringUtils.hasText(keyword)){
				queryDTO.setKeyword(keyword);
			}
			((PurchaseRecordTable)table).query(queryDTO);
		}
	}
	
	public class SalePanel extends JPanel implements ActionListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = -1498379543085914073L;
		private JPanel actionBar;
		private BaseTextField keywordInput;
		private DateTextField timeInput;
		private JButton searchBtn;
		private int width;
		private int height;
		
		private ContentPanel contentPane;
		
		public SalePanel(int width,int height){
			this.width = width;
			this.height = height;
			initComponent();
		}
		
		private JPanel createActionBar(){
			actionBar = new JPanel();
			actionBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			actionBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			actionBar.setBackground(Color.WHITE);
			
//			JLabel dateLabel = new JLabel("日期");
			timeInput = new DateTextField("查询日期");
			JLabel keywordLabel = new JLabel("关键字查询：");
			keywordInput = new BaseTextField("品种名称");
			searchBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/search_1.png",null);
			searchBtn.setToolTipText("搜索");
			searchBtn.addActionListener(this);
			searchBtn.registerKeyboardAction(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
					JComponent.WHEN_IN_FOCUSED_WINDOW);
//			actionBar.add(dateLabel);
			actionBar.add(timeInput);
			actionBar.add(keywordLabel);
			actionBar.add(keywordInput);
			actionBar.add(searchBtn);
			return actionBar;
		}
		
		public class ContentPanel extends JSplitPane implements ListSelectionListener{
			/**
			 * 
			 */
			private static final long serialVersionUID = 5447255020807010826L;
			private SaleLogTable mainTable;
			private SaleLogDetailTable subTable;
			public ContentPanel(int orientation,int width, int height){
				super(orientation,true);
				this.setSize(width, height);
				buildPane();
			}
			private void buildPane() {
				this.setDividerSize(3);
				this.setDividerLocation(0.45);
				this.setResizeWeight(0.45);
				this.setBorder(BorderFactory.createEmptyBorder());
				JScrollPane mainScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
				mainScrollPane.setBorder(BorderFactory.createEmptyBorder());
				mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				mainTable = new SaleLogTable(4);
				mainScrollPane.setViewportView(mainTable);
				mainScrollPane.getViewport().setBackground(Color.WHITE);
				mainTable.getSelectionModel().addListSelectionListener(this);
				JScrollPane rTopScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
				rTopScrollPane.setBorder(BorderFactory.createEmptyBorder());
				rTopScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				subTable = new SaleLogDetailTable(new SaleLogDetailTableModel(4));
				//默认选中第一行，确保subTable已被初始化
				mainTable.setRowSelectionAllowed(true);
				if(!CollectionUtils.isEmpty(mainTable.getDataList())){
					mainTable.setRowSelectionInterval(0,0);
				}
				rTopScrollPane.setViewportView(subTable);
				rTopScrollPane.getViewport().setBackground(Color.WHITE);
				
				this.add(mainScrollPane);
				this.add(rTopScrollPane);
			}
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if(event.getSource() == mainTable.getSelectionModel()
						&& mainTable.getRowSelectionAllowed()){
					int row = mainTable.getSelectedRow();
					SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
					String queryDay = DateUtil.date2Str(DateUtil.getCurrentDate(),"yyyyMMdd");
					if(row != -1){
						SaleLogTableModel dm = (SaleLogTableModel) mainTable.getModel();
						SaleLogVO salelogvo = dm.getItem(row);
						queryDay = DateUtil.date2Str(salelogvo.getSaleDate(), "yyyyMMdd");
						queryDTO.setIno(salelogvo.getIno());
					}
					queryDTO.setDay(queryDay);
					subTable.reflushTableData(queryDTO);
				}
			}
			public SaleLogTable getMainTable() {
				return mainTable;
			}
			public SaleLogDetailTable getSubTable() {
				return subTable;
			}
			public void setMainTable(SaleLogTable mainTable) {
				this.mainTable = mainTable;
			}
			public void setSubTable(SaleLogDetailTable subTable) {
				this.subTable = subTable;
			}
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
			setLayout(new BorderLayout());
			add(createActionBar(),BorderLayout.NORTH);
			contentPane = new ContentPanel(JSplitPane.HORIZONTAL_SPLIT,this.getWidth(),this.getHeight());
			add(contentPane,BorderLayout.CENTER);
		}
		
		public void refresh(){
			SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
			queryDTO.setDay(DateUtil.date2Str(DateUtil.getCurrentDate(),"yyyyMMdd"));
			contentPane.getMainTable().reflushTableData(queryDTO);
			if(!CollectionUtils.isEmpty(contentPane.getMainTable().getDataList())){
				contentPane.getMainTable().setRowSelectionInterval(0,0);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
			String keyword = keywordInput.getText();
			String day = timeInput.getText();
			queryDTO.setDay(day);
			queryDTO.setKeyword(keyword);
			contentPane.getMainTable().reflushTableData(queryDTO);
			List<SaleLogVO> dataList = contentPane.getMainTable().getDataList();
			if(CollectionUtils.isEmpty(dataList)){
				return;
			}
			contentPane.getMainTable().setRowSelectionInterval(0,0);
			SaleLogVO salelogvo = dataList.get(0);
			if(salelogvo != null){
				queryDTO.setIno(salelogvo.getIno());
				queryDTO.setDay(DateUtil.date2Str(salelogvo.getSaleDate(), "yyyyMMdd"));
				contentPane.getSubTable().reflushTableData(queryDTO);
			}
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public void setHeight(int height) {
			this.height = height;
		}
		
	}

	public class SelloffPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 294560575471679923L;
		private SelloffWarningTable table;
		private JScrollPane scrollPane;
		
		public SelloffPanel(){
			initComponent();
		}
		private void initComponent() {
			setLayout(new BorderLayout(0,0));
			setBackground(Color.WHITE);
			table = new SelloffWarningTable(6);
			scrollPane = new JScrollPane();
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);
			scrollPane.getViewport().setBackground(Color.WHITE);
			add(scrollPane,BorderLayout.CENTER);
		}
		
		public void refresh(){
			table.reflushTableData();
		}
	}
}
