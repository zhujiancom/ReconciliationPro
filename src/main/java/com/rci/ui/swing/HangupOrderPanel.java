package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.component.TitleBar;
import com.rci.ui.swing.views.component.slidebar.HangupTableSlideBarHandler;
import com.rci.ui.swing.views.component.slidebar.SlideBar;
import com.rci.ui.swing.views.component.slidebar.SlideElement;
import com.rci.ui.swing.vos.HangupTabelInfoVO;

public class HangupOrderPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1960658657988367726L;

	private IMetadataService metaService;
	
	private HangupTableDetailInfoPanel infoPanel;
	
	private HangupOrderItemInfoPanel itemInfoPanel;
	
	public HangupOrderPanel(){
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}
	
	private void initComponent(){
		setLayout(new BorderLayout(0, 0));
//		setBackground(Color.WHITE);
		add(createTableSlideBar(),BorderLayout.NORTH);
		add(new HangupTableDetailInfoPanel(),BorderLayout.CENTER);
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
	private JPanel createTableSlideBar() {
		List<HangupTabelInfoVO> tables = metaService.getHangupTablesInfo();
		List<SlideElement> elements = new ArrayList<SlideElement>();
		HangupTableSlideBarHandler handler = new HangupTableSlideBarHandler();
		for(int i=0;i<tables.size();i++){
			HangupTabelInfoVO table = tables.get(i);
			if(i == 0){
				SlideElement element = new SlideElement(table.getTableName(),table,true);
				element.setIndex(i+1);
				element.addActionListener(handler);
				elements.add(element);
			}else{
				SlideElement element =new SlideElement(table.getTableName(),table);
				element.setIndex(i+1);
				element.addActionListener(handler);
				elements.add(element);
			}
		}
		return new SlideBar(elements);
	}
	
	private class HangupTableDetailInfoPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3999767740426706062L;

		public HangupTableDetailInfoPanel(){
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
			add(new TitleBar(new JLabel("<html>桌号：<font color='red'>1</font></html>"),500,30),BorderLayout.NORTH);
			JPanel p = new JPanel();
			p.add(new JLabel("test"));
			add(p,BorderLayout.CENTER);
		}
	}
	
	private class HangupOrderItemInfoPanel extends JPanel{
		private JTable table;
		
		public HangupOrderItemInfoPanel(){
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
		}
		
		
	}
	
}
