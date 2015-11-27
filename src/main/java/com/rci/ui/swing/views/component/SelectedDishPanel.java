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
import javax.swing.SwingConstants;

import org.springframework.util.CollectionUtils;

import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.listeners.SelectedDishPanelListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.vos.DishVO;

public class SelectedDishPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930010936116109514L;
	
	private JButton addBtn;
	
	private List<DishVO> selectedDishes = new ArrayList<DishVO>();
	
	private List<DishVO> oldDishes;
	
	private JLabel displayLabel = new JLabel();
	
	private DishSelectWin win;
	
	public SelectedDishPanel(){
		initComponent();
	}
	
	public SelectedDishPanel(List<DishVO> oldDishes){
		this.oldDishes = oldDishes;
		initComponent();
	}

	private void initComponent() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		addBtn = ButtonFactory.createImageButton("菜品选择", "skin/gray/images/btn_orange.png", null);
		addBtn.setToolTipText("菜品选择");
		addBtn.setHorizontalTextPosition(SwingConstants.CENTER);
//		if(StringUtils.hasText(selectedDishNames)){
//			displayLabel.setText(selectedDishNames);
//		}
		if(!CollectionUtils.isEmpty(oldDishes)){
			StringBuffer sb = new StringBuffer();
			for(DishVO dish:oldDishes){
				sb.append(",").append(dish.getDishName());
			}
			displayLabel.setText(sb.substring(1));
		}
		this.add(addBtn);
		this.add(Box.createVerticalStrut(10));
		this.add(displayLabel);
		addBtn.addActionListener(this);
	}

	public void reflushView(){
		win.close();
		StringBuffer sb = new StringBuffer();
		if(!CollectionUtils.isEmpty(selectedDishes)){
			for(DishVO dish:selectedDishes){
				sb.append(",").append(dish.getDishName());
			}
			displayLabel.setText(sb.substring(1));
		}else{
			displayLabel.setText(null);
		}
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
		DishSelectListener selectListener = new SelectedDishPanelListener(this);
		if(oldDishes != null){
			selectListener.setOldDishes(oldDishes);
		}
		win = new DishSelectWin(selectListener,900,600,"菜品选择");
	}

}
