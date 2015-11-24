package com.rci.ui.swing.views.component;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.DishJCheckBox;
import com.rci.ui.swing.vos.DishTypeVO;
import com.rci.ui.swing.vos.DishVO;

public class DisplayDishPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036983150085052451L;

	private String seriesno;
	
	private JPanel leftPane;
	private JPanel rightContentPane;
	
	private JScrollPane rightPane;
	
	private IMetadataService metadataService;
	
	private DishSelectListener selectListener;
	
	public DisplayDishPanel(DishSelectListener selectListener){
		this.selectListener = selectListener;
		metadataService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}
	
	public DisplayDishPanel(String seriesno,DishSelectListener selectListener){
		this(selectListener);
		this.seriesno = seriesno;
//		this.selectListener = selectListener;
//		metadataService = (IMetadataService) SpringUtils.getBean("MetadataService");
//		initComponent();
	}

	private void initComponent() {
		GridBagLayout gblayout = new GridBagLayout();
		this.setLayout(gblayout);
		leftPane = new JPanel();
		leftPane.setBackground(Color.WHITE);
		final CardLayout card= new CardLayout();
		leftPane.setLayout(card);
		
		rightContentPane = new JPanel();
		BoxLayout rightLayout = new BoxLayout(rightContentPane, BoxLayout.Y_AXIS);
		rightContentPane.setLayout(rightLayout);
		List<DishTypeVO> dishtypes = metadataService.getAllDishTypeBySeriesNo(seriesno);
//		addContent(dishtypes);
		int index = 1;
		for(DishTypeVO type:dishtypes){
			JButton btn = ButtonFactory.createImageButton(type.getDtName(),"skin/gray/images/64x64/btn_"+index+".png", null);
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			rightContentPane.add(btn);
			final String typeno = type.getDtNo();
			leftPane.add(typeno,new DishPanel(type.getDtNo()));
			index++;
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent paramActionEvent) {
					card.show(leftPane, typeno);
				}
			});
		}
		rightPane = new JScrollPane(rightContentPane); 
		rightPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPane.setBorder(BorderFactory.createEmptyBorder());
		
		GridBagConstraints gb1 = new GridBagConstraints();
		gb1.insets = new Insets(0,0,5,5);//设置控件的空白 , 上、左、下、右
		gb1.fill = GridBagConstraints.BOTH;// 设置填充方式
		gb1.weightx=85.0;// 第二列的分布方式为80%
		gb1.gridx=0; //起始点为第3列
		gb1.gridy=0;	//起始点为第1行
		this.add(leftPane,gb1);
		
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.insets = new Insets(0,0,5,5);//设置控件的空白 , 上、左、下、右
		gb2.fill = GridBagConstraints.BOTH;// 设置填充方式
		gb2.weightx=15.0;// 第二列的分布方式为20%
		gb2.weighty=100.0;
		gb2.gridx=2; //起始点为第2列
		gb2.gridy=0;	//起始点为第1行
		this.add(rightPane,gb2);
	}
	private void addContent(List<DishTypeVO> dishtypes){
		final CardLayout card = (CardLayout) leftPane.getLayout(); 
		int index = 1;
		for(DishTypeVO type:dishtypes){
			JButton btn = ButtonFactory.createImageButton(type.getDtName(),"skin/gray/images/64x64/btn_"+index+".png", null);
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			rightContentPane.add(btn);
			final String typeno = type.getDtNo();
			leftPane.add(typeno,new DishPanel(type.getDtNo()));
			index++;
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent paramActionEvent) {
					card.show(leftPane, typeno);
				}
			});
		}
	}
	
	public String getSeriesno() {
		return seriesno;
	}

	public void setSeriesno(String seriesno) {
		this.seriesno = seriesno;
	}
	
	
	
//	@Override
//	protected Graphics getComponentGraphics(Graphics paramGraphics) {
//		if(StringUtils.hasText(seriesno)){
//			List<DishTypeVO> dishtypes = metadataService.getAllDishTypeBySeriesNo(seriesno);
////			leftPane.removeAll();
////			rightContentPane.removeAll();
//			addContent(dishtypes);
//		}
////		return super.getComponentGraphics(paramGraphics);
//	}

	public void refresh(){
		List<DishTypeVO> dishtypes = metadataService.getAllDishTypeBySeriesNo(seriesno);
		leftPane.removeAll();
		rightContentPane.removeAll();
		addContent(dishtypes);
//		leftPane.updateUI();
		leftPane.repaint();
		rightContentPane.repaint();
	}

	public class DishPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8067782465585803055L;
		private String typeno;
		
		public DishPanel(String typeno){
			this.typeno = typeno;
			initComponent();
		}
		
		private void initComponent(){
			this.setLayout(new GridLayout(10, 2));
			this.setBackground(Color.WHITE);
			List<DishVO> dishes = metadataService.getAllDishByTypeNo(typeno);
			for(DishVO dish:dishes){
				DishJCheckBox c = new DishJCheckBox(dish.getDishName()+"-"+dish.getDishPrice(),dish);
				c.setBackground(Color.WHITE);
				c.addItemListener(selectListener);
				this.add(c);
			}
		}
	}
	
}
