package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.OrderItemTable;
import com.rci.ui.swing.views.component.TitleBar;
import com.rci.ui.swing.views.component.slidebar.HangupTableSlideBarHandler;
import com.rci.ui.swing.views.component.slidebar.SlideBar;
import com.rci.ui.swing.views.component.slidebar.SlideElement;
import com.rci.ui.swing.views.component.slidebar.SlideElement.State;
import com.rci.ui.swing.vos.HangupTabelInfoVO;

public class HangupOrderPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1960658657988367726L;
	
	protected Container parentContainer;

	private IMetadataService metaService;
	
	private HangupTableSlideBarHandler handler;
	
	private List<SlideElement> elements = new ArrayList<SlideElement>();
	
	public HangupOrderPanel(Container parentContainer){
		this.parentContainer = parentContainer;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}
	
	private void initComponent(){
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		JButton refreshBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/refresh.png","skin/gray/images/64x64/refresh_pressed.png");
		refreshBtn.setToolTipText("刷新");
		JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
		upPanel.setBackground(Color.WHITE);
		upPanel.add(createTableSlideBar());
		upPanel.add(refreshBtn);
		add(upPanel,BorderLayout.NORTH);
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parentContainer.removeAll();
				parentContainer.add(new HangupOrderPanel(parentContainer),BorderLayout.CENTER);
				((JPanel) parentContainer).updateUI();				
			}
		});
	}

	/**
	 * 
	 *
	 * Describle(描述)：创建slide bar
	 *
	 * 方法名称：createTableSlideBar
	 *
	 * 所在类名：HangupOrderPanel
	 *
	 * Create Time:2015年12月12日 下午2:49:07
	 *  
	 * @return
	 */
	public JPanel createTableSlideBar() {
		List<HangupTabelInfoVO> tables = metaService.getHangupTablesInfo();
		handler = new HangupTableSlideBarHandler();
		for(int i=0;i<tables.size();i++){
			HangupTabelInfoVO table = tables.get(i);
			if(i == 0){
				SlideElement element = new SlideElement(table.getTableName(),table,State.SELECTED);
				element.setIndex(i+1);
				element.addActionListener(handler);
				elements.add(element);
				HangupTableDetailInfoPanel detailInfoPanel = new HangupTableDetailInfoPanel(element);
				HangupOrderItemInfoPanel itemInfoPanel = new HangupOrderItemInfoPanel(element);
				handler.setDetailInfoPanel(detailInfoPanel);
				handler.setItemInfoPanel(itemInfoPanel);
				add(detailInfoPanel,BorderLayout.CENTER);
				add(itemInfoPanel,BorderLayout.EAST);
			}else{
				SlideElement element =new SlideElement(table.getTableName(),table);
				element.setIndex(i+1);
				element.addActionListener(handler);
				elements.add(element);
			}
		}
		return new SlideBar(elements);
	}
	
	public class HangupTableDetailInfoPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3999767740426706062L;
		
		private SlideElement selectedElement;

		public HangupTableDetailInfoPanel(SlideElement selectedElement){
			this.selectedElement = selectedElement;
			initInternalComponent();
		}

		/**
		 *
		 * Describle(描述)：
		 *
		 * 方法名称：ininComponent
		 *
		 * 所在类名：HangupTableDetailInfoPanel
		 *
		 * Create Time:2015年12月12日 下午5:31:04
		 *   
		 */
		private void initInternalComponent() {
			setLayout(new BorderLayout(0, 0));
			setBackground(Color.WHITE);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			invalidate();
			HangupTabelInfoVO tableInfo = (HangupTabelInfoVO) selectedElement.getValue();
			add(new TitleBar(new JLabel("<html>桌号：<font color='red'>"+tableInfo.getTableName()+"</font></html>"),500,30),BorderLayout.NORTH);
			JPanel content = new JPanel();
			content.setBackground(Color.WHITE);
			content.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			content.add(new JLabel("<html>消费总额：<font color='green' size='18'>"+tableInfo.getConsumAmount()+"</font></html>"));
			content.add(Box.createVerticalStrut(10));
			content.add(new JLabel("<html>可打折金额：<font color='blue' size='18'>"+tableInfo.getDiscountAmount()+"</font></html>"));
			content.add(new JLabel("<html>不可打折金额：<font color='red' size='20'>"+tableInfo.getNodiscountAmount()+" </html>"));
			content.add(Box.createVerticalStrut(30));
			content.add(new JLabel("<html>开台时间："+DateUtil.time2Str(tableInfo.getOpendeskTime())+" </html>"));
			add(content,BorderLayout.CENTER);
			validate();
		}

		public SlideElement getSelectedElement() {
			return selectedElement;
		}

		public void setSelectedElement(SlideElement selectedElement) {
			this.selectedElement = selectedElement;
			updateUI();
		}
		
	}
	
	public class HangupOrderItemInfoPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3545811358900115805L;

		private OrderItemTable itemTable;
		
		private SlideElement selectedElement;
		
		
		public HangupOrderItemInfoPanel(SlideElement selectedElement){
			this.selectedElement = selectedElement;
			initInternalComponent();
		}

		/**
		 *
		 * Describle(描述)：
		 *
		 * 方法名称：initInternalComponent
		 *
		 * 所在类名：HangupOrderItemInfoPanel
		 *
		 * Create Time:2015年12月13日 下午2:20:17
		 *   
		 */
		private void initInternalComponent() {
			setLayout(new BorderLayout(0, 0));
			add(new TitleBar(new JLabel("<html>订单信息</html>"),500,30),BorderLayout.NORTH);
			JScrollPane spane = new JScrollPane();
			spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			spane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
			if(itemTable == null){
				itemTable = new OrderItemTable(7);
			}
			spane.setViewportView(itemTable);
			spane.getViewport().setBackground(Color.WHITE);
			HangupTabelInfoVO tableInfo = (HangupTabelInfoVO) selectedElement.getValue();
			itemTable.reflushTable(tableInfo.getOrderItems());
			add(spane,BorderLayout.CENTER);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			HangupTabelInfoVO tableInfo = (HangupTabelInfoVO) selectedElement.getValue();
			itemTable.reflushTable(tableInfo.getOrderItems());
		}

		public SlideElement getSelectedElement() {
			return selectedElement;
		}

		public void setSelectedElement(SlideElement selectedElement) {
			this.selectedElement = selectedElement;
			updateUI();
		}
		
		
	}

	public List<SlideElement> getElements() {
		return elements;
	}

	public void setElements(List<SlideElement> elements) {
		this.elements = elements;
	}

	public Container getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(Container parentContainer) {
		this.parentContainer = parentContainer;
	}
	
}
