package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.DishJCheckBox;
import com.rci.ui.swing.views.component.slidebar.SlideElement;
import com.rci.ui.swing.vos.DishSeriesVO;
import com.rci.ui.swing.vos.DishTypeVO;
import com.rci.ui.swing.vos.DishVO;

public class DishChooserPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8221448443726263565L;
	
	private SlideElement selectedElement;
	
	private JPanel leftPanel;
	
	private CardLayout leftCard;
	
	private JPanel rightPanel;
	
	private IMetadataService metadataService;
	
	private DishSelectListener selectionListener;
	
	public DishChooserPanel(DishSelectListener selectionListener,SlideElement selectedElement){
		this.selectionListener = selectionListener;
		this.selectedElement = selectedElement;
		metadataService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}

	private void initComponent() {
		setLayout(new BorderLayout(0, 0));
		leftPanel = new JPanel();
		leftCard= new CardLayout();
		leftPanel.setLayout(leftCard);
		rightPanel = new JPanel(new GridLayout(0, 2));
		rightPanel.setBackground(Color.WHITE);
	}

	public SlideElement getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(SlideElement selectedElement) {
		this.selectedElement = selectedElement;
		updateUI();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		invalidate();
		DishSeriesVO series = (DishSeriesVO) selectedElement.getValue();
		List<DishTypeVO> dishtypes = metadataService.getAllDishTypeBySeriesNo(series.getSeriesno());
		createLeftPanel(dishtypes);
		createRightPanel(dishtypes);
		add(leftPanel,BorderLayout.CENTER);
		add(rightPanel,BorderLayout.EAST);
		validate();
	}

	private void createRightPanel(List<DishTypeVO> dishtypes) {
		rightPanel.removeAll();
		for(DishTypeVO type:dishtypes){
			JButton btn = ButtonFactory.createImageButton(type.getDtName(),"skin/gray/images/64x64/btn_1.png", "skin/gray/images/64x64/btn_2.png");
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			rightPanel.add(btn);
			final String typeno = type.getDtNo();
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					leftCard.show(leftPanel, typeno);
				}
			});
		}
		rightPanel.validate();
	}

	private void createLeftPanel(List<DishTypeVO> dishtypes) {
		leftPanel.removeAll();
		for(DishTypeVO type:dishtypes){
			leftPanel.add(type.getDtNo(),new DishListPanel(type.getDtNo()));
		}
		leftPanel.validate();
	}
	
	private class DishListPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1741981930995216971L;
		private String typeno;
		
		public DishListPanel(String typeno){
			this.typeno = typeno;
			initComponent();
		}
		
		private void initComponent(){
			this.setLayout(new GridLayout(0, 4));
			this.setBackground(Color.WHITE);
			List<DishVO> dishes = metadataService.getAllDishByTypeNo(typeno);
			List<DishVO> selectedDishes = selectionListener.getOldDishes();
			for(DishVO dish:dishes){
				DishJCheckBox c = new DishJCheckBox(dish.getDishName()+"-"+dish.getDishPrice(),dish);
				c.setBackground(Color.WHITE);
				c.addItemListener(selectionListener);
				if(selectedDishes != null && selectedDishes.contains(dish)){
					c.setSelected(true);
				}
				this.add(c);
			}
		}
	}
}
