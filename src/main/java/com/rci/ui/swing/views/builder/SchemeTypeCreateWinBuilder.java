package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.rci.bean.LabelValueBean;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.listeners.ActivityTypeCheckListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.ListItemComboBoxModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.SchemeTypeVO;

public class SchemeTypeCreateWinBuilder implements PopWindowBuilder,ActionListener{
	private PopWindow addForm;
	private JScrollPane sPane;
	
	private JTextField nameInput = new JTextField(30);
	private JComboBox<DishVO> dishInput;
	private JComboBox<LabelValueBean<String>> activityTypeInput;
	private JTextField discountInput = new JTextField(30);
	private JTextField floorInput = new JTextField(30);
	private JTextField ceilInput = new JTextField(30);
	private JLabel name = new JLabel("活动类型名称");
	private JLabel dish = new JLabel("适用菜品");
	private JLabel discount = new JLabel("不可打折金额");
	private JLabel floor = new JLabel("最低消费金额");
	private JLabel ceil = new JLabel("最高消费金额");
	private JLabel activityType = new JLabel("活动形式");
	private JButton confirmBtn;
	
	private ActivityTypeCheckListener checkListener;
	
	private IMetadataService metaService;
	
	public SchemeTypeCreateWinBuilder(){}
	
	public SchemeTypeCreateWinBuilder(ActivityTypeCheckListener checkListener){
		this.checkListener = checkListener;
	}
	
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try{
			validation();
			@SuppressWarnings("unchecked")
			LabelValueBean<String> activityTypeSelected = (LabelValueBean<String>) activityTypeInput.getSelectedItem();
			SchemeTypeVO newSchemeType = new SchemeTypeVO();
			ActivityType activityType = ActivityType.valueOf(activityTypeSelected.getValue());
			newSchemeType.setActivity(activityType);
			newSchemeType.setTypeName(nameInput.getText());
			if(StringUtils.hasText(discountInput.getText())){
				BigDecimal discountAmount = new BigDecimal(discountInput.getText().trim());
				newSchemeType.setDiscountAmount(discountAmount);
			}
			DishVO dish = (DishVO) dishInput.getSelectedItem();
			if(dish != null){
				newSchemeType.setDishNo(dish.getDishNo());
			}
			if(StringUtils.hasText(floorInput.getText())){
				newSchemeType.setFloorAmount(new BigDecimal(floorInput.getText().trim()));
			}
			if(StringUtils.hasText(ceilInput.getText())){
				newSchemeType.setCeilAmount(new BigDecimal(ceilInput.getText().trim()));
			}
			metaService.createSchemeType(newSchemeType);
			JOptionPane.showMessageDialog(null, "活动类型创建成功！");
			checkListener.refreshTableData();
			addForm.close();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+se.getMessage()+"</h3></html>"));
		}catch (Exception ex){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+ex.getMessage()+"</h3></html>"));
		}
	}
	@Override
	public PopWindow retrieveWindow() {
		addForm = new PopWindow(350,500);
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		JPanel containerPanel = addForm.getContainerPanel();
		createContentPane();
		containerPanel.add(sPane,BorderLayout.CENTER);
		return addForm;
	}

	private void createContentPane() {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		sPane = new JScrollPane(mainPane);
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(name);
		firstPane.add(nameInput);
		mainPane.add(firstPane);
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,0));
		secondPane.add(dish);
		List<DishVO> itemList = metaService.displayDishSuits();
		ListItemComboBoxModel<DishVO> vcm = new ListItemComboBoxModel<DishVO>(itemList);
		dishInput = new JComboBox<DishVO>(vcm);
		secondPane.add(dishInput);
		mainPane.add(secondPane);
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(discount);
		thirdPane.add(discountInput);
		mainPane.add(thirdPane);
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(floor);
		forthPane.add(floorInput);
		mainPane.add(forthPane);
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		fifthPane.add(ceil);
		fifthPane.add(ceilInput);
		mainPane.add(fifthPane);
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,2));
		sixthPane.add(activityType);
		List<LabelValueBean<String>> activityTypeList = EnumUtils.getEnumLabelValueBeanList(ActivityType.class, false);
		ListItemComboBoxModel<LabelValueBean<String>> atcm = new ListItemComboBoxModel<LabelValueBean<String>>(activityTypeList);
		activityTypeInput = new JComboBox<LabelValueBean<String>>(atcm);
		sixthPane.add(activityTypeInput);
		mainPane.add(sixthPane);
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmBtn = ButtonFactory.createImageButton("保存","skin/gray/images/64x64/saveBtn_0.png", null);
		confirmBtn.addActionListener(this);
		seventhPane.add(confirmBtn);
		mainPane.add(seventhPane);
	}
	
	public void validation() throws ServiceException{
		if(!StringUtils.hasText(nameInput.getText())){
			ExceptionManage.throwServiceException("活动类型名称必填!");
		}
		if(activityTypeInput.getSelectedItem() == null){
			ExceptionManage.throwServiceException("请选择活动形式!");
		}
	}
}
