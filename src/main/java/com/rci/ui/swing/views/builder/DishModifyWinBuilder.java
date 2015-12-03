package com.rci.ui.swing.views.builder;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.springframework.util.CollectionUtils;

import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.DishTable.DishTableModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.InventoryDishRefVO;

public class DishModifyWinBuilder implements PopWindowBuilder, ItemListener , ActionListener{
	private JTable table;
	private PopWindow modifyForm;
	private DishVO dish;
	private IMetadataService metaService;
	private JCheckBox stopFlag;
	private JCheckBox discountFlag;
	private JCheckBox suitFlag;
	private InputPanel costPane;
	private Map<InventoryDishRefVO,InputPanel> standardMap;
	
	public DishModifyWinBuilder(JTable table){
		this.table = table;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}
	
	@Override
	public PopWindow retrieveWindow() {
		modifyForm = new PopWindow(600,400,"菜品编辑");
		int selectIndex = table.getSelectedRow();
		DishTableModel model = (DishTableModel) table.getModel();
		dish = model.getItem(selectIndex);
		/* 创建窗口主体 */
		initComponent();
		return modifyForm;
	}

	private void initComponent() {
		standardMap = new HashMap<InventoryDishRefVO,InputPanel>();
		JPanel containerPanel = modifyForm.getContainerPanel();
		JPanel mainPane = new JPanel();
		BoxLayout mainLayout = new BoxLayout(mainPane, BoxLayout.Y_AXIS);
		mainPane.setLayout(mainLayout);
		
		JPanel baseInfoPane = new JPanel(new GridLayout(0, 3));
		baseInfoPane.setSize(modifyForm.getWidth(), 80);
		baseInfoPane.setPreferredSize(new Dimension(modifyForm.getWidth(), 80));
		baseInfoPane.add(new InputPanel("菜品编号",dish.getDishNo()).initComponet());
		baseInfoPane.add(new InputPanel("菜品名称",dish.getDishName()).initComponet());
		baseInfoPane.add(new InputPanel("菜品价格",dish.getDishPrice()).initComponet());
		costPane = new InputPanel("成本单价",dish.getCost(),true);
		baseInfoPane.add(costPane.initComponet());
		mainPane.add(baseInfoPane);
		
		JPanel secondPane = new JPanel();
		secondPane.setLayout(null);
		stopFlag = new JCheckBox("停用");
		stopFlag.setEnabled(false);
		stopFlag.addItemListener(this);
		if(YOrN.Y.equals(dish.getStopFlag())){
			stopFlag.setSelected(true);
		}
		stopFlag.setBounds(0, 0, 100, 20);
		discountFlag = new JCheckBox("不可打折");
		discountFlag.setEnabled(false);
		discountFlag.addItemListener(this);
		if(YOrN.N.equals(dish.getDiscountFlag())){
			discountFlag.setSelected(true);
		}
		discountFlag.setBounds(100, 0, 100, 20);
		suitFlag = new JCheckBox("套餐");
		suitFlag.setEnabled(false);
		suitFlag.addItemListener(this);
		if(YOrN.Y.equals(dish.getSuitFlag())){
			suitFlag.setSelected(true);
		}
		suitFlag.setBounds(200, 0, 100, 20);
		secondPane.add(stopFlag);
		secondPane.add(discountFlag);
		secondPane.add(suitFlag);
		mainPane.add(secondPane);
		
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		thirdPane.add(new JLabel("规格设置："));
		List<InventoryDishRefVO> refs = metaService.queryInventoryDishRefByDish(dish.getDishNo());
		for(InventoryDishRefVO ref:refs){
			InputPanel p = new InputPanel(ref.getIname(), ref.getStandard(),true); 
			standardMap.put(ref,p);
			thirdPane.add(p.initComponet());
		}
		mainPane.add(thirdPane);
		
		JPanel actionPane = new JPanel();
		JButton confirmBtn = ButtonFactory.createImageButton("确定","skin/gray/images/btn_green.png",null);
		confirmBtn.addActionListener(this);
		actionPane.add(confirmBtn);
		mainPane.add(actionPane);
		
		containerPanel.add(mainPane);
	}
	
	private class InputPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5214468218079383781L;

		private String labelName;
		
		private Object value;
		
		private JTextField field;
		
		private boolean isEditable = false;
		
		public InputPanel(String labelName){
			this.labelName = labelName;
		}
		
		public InputPanel(String labelName,Object value){
			this(labelName);
			this.value = value;
		}
		
		public InputPanel(String labelName,Object value,boolean isEditable){
			this(labelName,value);
			this.isEditable = isEditable;
		}
		
		public JPanel initComponet(){
			this.setLayout(new FlowLayout(FlowLayout.LEADING));
			this.setPreferredSize(new Dimension(120,50));
			JLabel label = new JLabel(labelName);
			field = new JTextField(15);
			if(value != null){
				field.setText(value.toString());
			}
			field.setEditable(isEditable);
			this.add(label);
			this.add(field);
			return this;
		}

		public String getInputResult(){
			String result = field.getText();
			if(StringUtils.hasText(result)){
				return result;
			}
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		BigDecimal cost = null;
		if(costPane.getInputResult() != null){
			cost = new BigDecimal(costPane.getInputResult());
		}
		dish.setCost(cost);
		metaService.updateDishCost(dish);
		if(!CollectionUtils.isEmpty(standardMap)){
			for(Iterator<Entry<InventoryDishRefVO,InputPanel>> it = standardMap.entrySet().iterator();it.hasNext();){
				Entry<InventoryDishRefVO,InputPanel> entry = it.next();
				InventoryDishRefVO refvo = entry.getKey();
				InputPanel inputPanel = entry.getValue();
				BigDecimal standardAmount = null;
				if(inputPanel.getInputResult() != null ){
					standardAmount = new BigDecimal(inputPanel.getInputResult());
				}
				refvo.setStandard(standardAmount);
				metaService.standardSetting(refvo);
			}
		}
		modifyForm.close();
	}

	@Deprecated
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if(source == stopFlag){
			if(e.getStateChange() == ItemEvent.SELECTED){
				dish.setStopFlag(YOrN.Y);
			}else{
				dish.setStopFlag(YOrN.N);
			}
		}
		if(source == discountFlag){
			if(e.getStateChange() == ItemEvent.SELECTED){
				dish.setDiscountFlag(YOrN.Y);
			}else{
				dish.setDiscountFlag(YOrN.N);
			}
		}
		if(source == suitFlag){
			if(e.getStateChange() == ItemEvent.SELECTED){
				dish.setSuitFlag(YOrN.Y);
			}else{
				dish.setSuitFlag(YOrN.N);
			}
		}
	}

}
