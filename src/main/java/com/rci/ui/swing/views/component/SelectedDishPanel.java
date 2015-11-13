package com.rci.ui.swing.views.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.util.CollectionUtils;

import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.vos.DishVO;

public class SelectedDishPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930010936116109514L;
	
	private JButton addBtn;
	
	private List<DishVO> selectedDishes;
	
	private JLabel displayLabel;
	
	public SelectedDishPanel(){
		initComponent();
	}

	private void initComponent() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		addBtn = ButtonFactory.createImageButton("选择菜品", "skin/gray/images/24x24/addBtn_2.png", null);
		StringBuffer sb = new StringBuffer();
		String selectedDishNames = "套餐C(原味)";
		if(!CollectionUtils.isEmpty(selectedDishes)){
			for(DishVO dish:selectedDishes){
				sb.append(",").append(dish.getDishName());
			}
			selectedDishNames = sb.substring(1);
		}
		displayLabel = new JLabel(selectedDishNames);
		this.add(addBtn);
		this.add(Box.createVerticalStrut(10));
		this.add(displayLabel);
		
		addBtn.addActionListener(this);
	}

//	@Override
//	public void updateUI() {
//		StringBuffer sb = new StringBuffer();
//		String selectedDishNames = "";
//		if(!CollectionUtils.isEmpty(selectedDishes)){
//			for(DishVO dish:selectedDishes){
//				sb.append(",").append(dish.getDishName());
//			}
//			selectedDishNames = sb.substring(1);
//		}
//		displayLabel.setText(selectedDishNames);
//	}

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
		DishSelectWin win = new DishSelectWin(900,600,"菜品选择");
		win.setParentPanel(this);
	}
}
