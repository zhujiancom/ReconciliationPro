package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import com.rci.bean.dto.QueryDishDTO;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.model.DishTable;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.builder.DishModifyWinBuilder;
import com.rci.ui.swing.views.builder.PopWindowBuilder;
import com.rci.ui.swing.vos.DishSeriesVO;
import com.rci.ui.swing.vos.DishTypeVO;

public class DishManagementWin extends PopWindow implements TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4194126541971058047L;
	private IMetadataService metaservice;
	private JTable table;
	private URL closeFolderUrl;
	private URL openFolderUrl;

	public DishManagementWin(int width,int height,String title){
		super(width,height,title);
		metaservice = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}

	private void initComponent() {
		JPanel containerPanel = this.getContainerPanel();
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
		mainPanel.setSize(getWidth(), getHeight());
		mainPanel.setDividerSize(5);
		mainPanel.setDividerLocation(0.3);
		mainPanel.setResizeWeight(0.3);
		JScrollPane tablePanel = createTablePane();
		JScrollPane treeListPanel = createTreeListPane();
		mainPanel.add(treeListPanel);
		mainPanel.add(tablePanel);
		containerPanel.add(mainPanel,BorderLayout.CENTER);
	}
	
	private JScrollPane createTreeListPane(){
		JScrollPane pane = new JScrollPane();
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		List<DishSeriesVO> series = metaservice.getAllDishSeries();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("菜品管理");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		for(DishSeriesVO s:series){
			DefaultMutableTreeNode seriesNode = new DefaultMutableTreeNode(s);
			treeModel.insertNodeInto(seriesNode, root, root.getChildCount());
			List<DishTypeVO> types = metaservice.getAllDishTypeBySeriesNo(s.getSeriesno());
			for(DishTypeVO type:types){
				DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(type);
				treeModel.insertNodeInto(typeNode, seriesNode, seriesNode.getChildCount());
			}
		}
		closeFolderUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/folder_close.png");
		openFolderUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/folder_open.png");
		JTree tree = new JTree(treeModel);
		tree.setRowHeight(25);
		tree.addTreeSelectionListener(this);
		DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		cellRenderer.setLeafIcon(null);
		cellRenderer.setClosedIcon(new ImageIcon(closeFolderUrl));
		cellRenderer.setOpenIcon(new ImageIcon(openFolderUrl));
		pane.setViewportView(tree);
		return pane;
	}
	
	private JScrollPane createTablePane(){
		JScrollPane pane = new JScrollPane();
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table = new DishTable(6);
		pane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				if(event.getClickCount() == 2){
					PopWindowBuilder winBuilder = new DishModifyWinBuilder(table);
					winBuilder.retrieveWindow();
				}
			}
			
		});
		return pane;
	}
	

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		JTree tree = (JTree) event.getSource();
		DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		Object obj = selectionNode.getUserObject();
		if(obj != null && obj.getClass().isAssignableFrom(DishSeriesVO.class)){
			DishSeriesVO series = (DishSeriesVO) obj;
			QueryDishDTO queryDTO = new QueryDishDTO();
			queryDTO.setSno(series.getSeriesno());
			((DishTable)table).reflushTableData(queryDTO);
		}
		if(obj != null && obj.getClass().isAssignableFrom(DishTypeVO.class)){
			DishTypeVO type = (DishTypeVO) obj;
			QueryDishDTO queryDTO = new QueryDishDTO();
			queryDTO.setTno(type.getDtNo());
			((DishTable)table).reflushTableData(queryDTO);
		}
	}

}
