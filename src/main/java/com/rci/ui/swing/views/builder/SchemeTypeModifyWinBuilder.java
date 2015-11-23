package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.LabelValueBean;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;
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
import com.rci.ui.swing.model.SchemeTypeTable.SchemeTypeTabelModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.SelectedDishPanel;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.SchemeTypeVO;

public class SchemeTypeModifyWinBuilder implements PopWindowBuilder,
		ActionListener {
	private Log logger = LogFactory.getLog(SchemeTypeModifyWinBuilder.class);
	private PopWindow modifyForm;
	private SchemeTypeVO schemeType;
	private JScrollPane sPane;
	private JTextField nameInput;
	private JTextField typenoInput;
//	private JComboBox<DishVO> dishInput;
	private SelectedDishPanel selectedDishPanel;
	private JComboBox<LabelValueBean<String>> activityTypeInput;
	private JTextField discountInput;
	private JTextField realAmountInput;
	private JTextField floorInput;
	private JTextField ceilInput;
	private JLabel name = new JLabel("活动类型名称");
	private JLabel typeno = new JLabel("活动类型编号");
	private JLabel dish = new JLabel("适用菜品");
	private JLabel discount = new JLabel("不可打折金额");
	private JLabel realAmount = new JLabel("可抵扣金额");
	private JLabel floor = new JLabel("最低消费金额");
	private JLabel ceil = new JLabel("最高消费金额");
	private JLabel activityType = new JLabel("活动形式");
	private JLabel createTime = new JLabel("创建时间");
	private JLabel modifyTime = new JLabel("修改时间");
	private JButton confirmBtn;
	private JButton activeBtn;
	private JButton inactiveBtn;
	IMetadataService metaService;
	private ActivityTypeCheckListener checkListener;
	
	public SchemeTypeModifyWinBuilder(ActivityTypeCheckListener checkListener){
		this.checkListener = checkListener;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			validation();
			schemeType.setTypeName(nameInput.getText());
			schemeType.setTypeNo(typenoInput.getText());
//			DishVO dish = (DishVO) dishInput.getSelectedItem();
//			if(dish != null){
//				schemeType.setDishNo(dish.getDishNo());
//			}
			List<DishVO> selectDishes = selectedDishPanel.getSelectedDishes();
			schemeType.setDishes(selectDishes);
			if(StringUtils.hasText(discountInput.getText())){
				schemeType.setDiscountAmount(new BigDecimal(discountInput.getText().trim()));
			}
			schemeType.setRealAmount(new BigDecimal(realAmountInput.getText().trim()));
			schemeType.setFloorAmount(new BigDecimal(floorInput.getText().trim()));
			schemeType.setCeilAmount(new BigDecimal(ceilInput.getText().trim()));
			LabelValueBean<String> activityTypeVal = (LabelValueBean<String>) activityTypeInput.getSelectedItem();
			ActivityType activityType = ActivityType.valueOf(activityTypeVal.getValue()); 
			schemeType.setActivity(activityType);
			metaService.updateSchemeType(schemeType);
			JOptionPane.showMessageDialog(null, "活动修改成功！");
			checkListener.refreshTableData();
			modifyForm.close();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+se.getMessage()+"</h3></html>"));
		}catch(Exception ex){
			logger.error(ex);
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	@Override
	public PopWindow retrieveWindow() {
		modifyForm = new PopWindow(350,500);
		JPanel containerPanel = modifyForm.getContainerPanel();
		JTable table = checkListener.getTable();
		int selectIndex = table.getSelectedRow();
		SchemeTypeTabelModel model = (SchemeTypeTabelModel) table.getModel();
		schemeType = model.getScheme(selectIndex);
		/* 创建窗口主体 */
		createContentPane();
		containerPanel.add(sPane,BorderLayout.CENTER);
		return modifyForm;
	}

	private void createContentPane() {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		sPane = new JScrollPane(mainPane);
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(name);
		nameInput = new JTextField(schemeType.getTypeName(),30);
		firstPane.add(nameInput);
		mainPane.add(firstPane);
		JPanel eightPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		eightPane.add(typeno);
		typenoInput = new JTextField(schemeType.getTypeNo(),30);
		eightPane.add(typenoInput);
		mainPane.add(eightPane);
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,0));
		secondPane.add(dish);
		selectedDishPanel = new SelectedDishPanel(schemeType);
		secondPane.add(selectedDishPanel);
		mainPane.add(secondPane);
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(discount);
		discountInput = new JTextField(StringUtils.trimToEmpty(schemeType.getDiscountAmount()),30);
		thirdPane.add(discountInput);
		mainPane.add(thirdPane);
		JPanel ninthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		ninthPane.add(realAmount);
		realAmountInput = new JTextField(StringUtils.trimToEmpty(schemeType.getRealAmount()),30);
		ninthPane.add(realAmountInput);
		mainPane.add(ninthPane);
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(floor);
		floorInput = new JTextField(StringUtils.trimToEmpty(schemeType.getFloorAmount()),30);
		forthPane.add(floorInput);
		mainPane.add(forthPane);
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		fifthPane.add(ceil);
		ceilInput = new JTextField(StringUtils.trimToEmpty(schemeType.getCeilAmount()),30);
		fifthPane.add(ceilInput);
		mainPane.add(fifthPane);
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,2));
		sixthPane.add(activityType);
		List<LabelValueBean<String>> activityTypeList = EnumUtils.getEnumLabelValueBeanList(ActivityType.class, false);
		ListItemComboBoxModel<LabelValueBean<String>> atcm = new ListItemComboBoxModel<LabelValueBean<String>>(activityTypeList);
		activityTypeInput = new JComboBox<LabelValueBean<String>>(atcm);
		LabelValueBean<String> labelvalue = new LabelValueBean<String>(EnumUtils.getEnumMessage(schemeType.getActivity()),schemeType.getActivity().name());
		activityTypeInput.setSelectedItem(labelvalue);
		sixthPane.add(activityTypeInput);
		mainPane.add(sixthPane);
		JPanel elevenPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		elevenPane.add(createTime);
		elevenPane.add(new JLabel(DateUtil.time2Str(schemeType.getCreateTime())));
		mainPane.add(elevenPane);
		JPanel twelvePane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		twelvePane.add(modifyTime);
		twelvePane.add(new JLabel(DateUtil.time2Str(schemeType.getModifyTime())));
		mainPane.add(twelvePane);
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmBtn = ButtonFactory.createImageButton("保存","skin/gray/images/64x64/saveBtn_0.png", null);
		confirmBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.addActionListener(this);
		activeBtn = new JButton("启用");
		inactiveBtn = new JButton("停用");
		if(ActivityStatus.ACTIVE.equals(schemeType.getStatus())){
			activeBtn.setEnabled(false);
			inactiveBtn.setEnabled(true);
		}
		if(ActivityStatus.INACTIVE.equals(schemeType.getStatus())){
			activeBtn.setEnabled(true);
			inactiveBtn.setEnabled(false);
		}
		activeBtn.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				metaService.activeSchemeType(schemeType.getStid());
				JOptionPane.showMessageDialog(null, "活动类型已启用！");
				checkListener.refreshTableData();
				modifyForm.close();
			}
		});
		inactiveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				metaService.inactiveSchemeType(schemeType.getStid());
				JOptionPane.showMessageDialog(null, "活动类型已禁用！");
				checkListener.refreshTableData();
				modifyForm.close();
			}
		});
		seventhPane.add(confirmBtn);
		seventhPane.add(activeBtn);
		seventhPane.add(inactiveBtn);
		mainPane.add(seventhPane);
	}
	
	public void validation() throws ServiceException{
		if(!StringUtils.hasText(nameInput.getText())){
			ExceptionManage.throwServiceException("活动类型名称必填!");
		}
		if(activityTypeInput.getSelectedItem() == null){
			ExceptionManage.throwServiceException("请选择活动形式!");
		}
		if(!StringUtils.hasText(realAmountInput.getText())){
			ExceptionManage.throwServiceException("抵扣金额必填!");
		}
		if(!StringUtils.hasText(floorInput.getText())){
			ExceptionManage.throwServiceException("请填写最低消费金额!");
		}
		if(!StringUtils.hasText(ceilInput.getText())){
			ExceptionManage.throwServiceException("请填写最高消费金额!");
		}
		if(activityTypeInput.getSelectedItem() == null){
			ExceptionManage.throwServiceException("请选择活动形式!");
		}
	}

}
