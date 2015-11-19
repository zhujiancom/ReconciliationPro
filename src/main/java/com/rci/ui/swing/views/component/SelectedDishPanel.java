package com.rci.ui.swing.views.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.util.CollectionUtils;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.vos.DishVO;

public class SelectedDishPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930010936116109514L;
	
	private JButton addBtn;
	
	private List<DishVO> selectedDishes = new ArrayList<DishVO>();
	
	private String typeno;
	
	private JLabel displayLabel = new JLabel();
	
	private DishSelectWin win;
	
	public SelectedDishPanel(){
		initComponent();
	}
	
	public SelectedDishPanel(String typeno){
		this.typeno = typeno;
		initComponent();
	}

	private void initComponent() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		addBtn = ButtonFactory.createImageButton("选择菜品", "skin/gray/images/24x24/addBtn_2.png", null);
		if(StringUtils.hasText(typeno)){
			IMetadataService metadataService = (IMetadataService) SpringUtils.getBean("MetadataService");
			List<DishVO> dishes = metadataService.getRefDishesBySchemeTypeno(typeno);
			StringBuffer sb = new StringBuffer();
			if(!CollectionUtils.isEmpty(dishes)){
				for(DishVO dish:dishes){
					sb.append(",").append(dish.getDishName());
				}
				displayLabel.setText(sb.substring(1));
			}
		}
		this.add(addBtn);
		this.add(Box.createVerticalStrut(10));
		this.add(displayLabel);
		addBtn.addActionListener(this);
	}

	public void reflushView(){
		win.close();
		StringBuffer sb = new StringBuffer();
		for(DishVO dish:selectedDishes){
			sb.append(",").append(dish.getDishName());
		}
		displayLabel.setText(sb.substring(1));
		this.repaint();
	}

	public JButton getAddBtn() {
		return addBtn;
	}

	public List<DishVO> getSelectedDishes() {
		return selectedDishes;
	}

	public JLabel getDisplayLabel() {
		return displayLabel;
	}

	public void setAddBtn(JButton addBtn) {
		this.addBtn = addBtn;
	}

	public void setSelectedDishes(List<DishVO> selectedDishes) {
		this.selectedDishes = selectedDishes;
	}

	public void setDisplayLabel(JLabel displayLabel) {
		this.displayLabel = displayLabel;
	}
	
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		DishSelectListener selectListener = new DishSelectListener(this);
		win = new DishSelectWin(selectListener,900,600,"菜品选择");
	}

	public String getTypeno() {
		return typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}
}
