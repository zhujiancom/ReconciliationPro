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
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.listeners.VendorCheckListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.VendorComboBoxModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.SchemeVO;

public class SchemeCreateWinBuilder implements PopWindowBuilder,ActionListener {
	private PopWindow addForm;
	private JScrollPane sPane;
	private JTextField nameInput = new JTextField(30);
	private JComboBox<LabelValueBean<String>> vendorInput;
	private JTextField priceInput = new JTextField(30);
	private JTextField postPriceInput = new JTextField(30);
	private JTextField spreadInput = new JTextField(30);
	private JTextField startInput = new JTextField(30);
	private JTextField endInput = new JTextField(30);
	private JTextField floorInput = new JTextField(30);
	private JTextField ceilInput = new JTextField(30);
	private JLabel name = new JLabel("活动名称");
	private JLabel vendor = new JLabel("活动平台");
	private JLabel price = new JLabel("优惠金额");
	private JLabel postPrice = new JLabel("平台补贴");
	private JLabel spread = new JLabel("餐厅补贴");
	private JLabel start = new JLabel("开始时间");
	private JLabel end = new JLabel("结束时间");
	private JLabel floor = new JLabel("最低消费金额");
	private JLabel ceil = new JLabel("最高消费金额");
	private JButton confirmBtn;
	private VendorCheckListener checkListener;
	
	public SchemeCreateWinBuilder(){}
	
	public SchemeCreateWinBuilder(VendorCheckListener checkListener){
		this.checkListener=checkListener;
	}
	
	
	@Override
	public PopWindow retrieveWindow() {
		addForm = new PopWindow(350,500);
		JPanel containerPanel = addForm.getContainerPanel();
		createContentPane();
		containerPanel.add(sPane,BorderLayout.CENTER);
		return addForm;
	}

	@Override
	public void createQueryPane() {

	}

	@Override
	public void createContentPane() {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		sPane = new JScrollPane(mainPane);
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(name);
		firstPane.add(nameInput);
		mainPane.add(firstPane);
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
		secondPane.add(vendor);
		List<LabelValueBean<String>> itemList = EnumUtils.getEnumLabelValueBeanList(Vendor.class, false);
		VendorComboBoxModel vcm = new VendorComboBoxModel(itemList);
		vendorInput = new JComboBox<LabelValueBean<String>>(vcm);
		secondPane.add(vendorInput);
		mainPane.add(secondPane);
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(price);
		thirdPane.add(priceInput);
		mainPane.add(thirdPane);
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(postPrice);
		forthPane.add(postPriceInput);
		mainPane.add(forthPane);
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		fifthPane.add(spread);
		fifthPane.add(spreadInput);
		mainPane.add(fifthPane);
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		sixthPane.add(start);
		sixthPane.add(startInput);
		mainPane.add(sixthPane);
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		seventhPane.add(end);
		seventhPane.add(endInput);
		mainPane.add(seventhPane);
		JPanel eightthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eightthPane.add(floor);
		eightthPane.add(floorInput);
		mainPane.add(eightthPane);
		JPanel ninthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ninthPane.add(ceil);
		ninthPane.add(ceilInput);
		mainPane.add(ninthPane);
		JPanel tenthPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmBtn = ButtonFactory.createImageButton("保存","skin/gray/images/64x64/saveBtn_0.png", null);
		confirmBtn.addActionListener(this);
		tenthPane.add(confirmBtn);
		mainPane.add(tenthPane);
	}

	@Override
	public void createBottomPane() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			validation();
			@SuppressWarnings("unchecked")
			LabelValueBean<String> item = (LabelValueBean<String>) vendorInput.getSelectedItem();
			SchemeVO newScheme = new SchemeVO();
			Vendor vendor = Vendor.valueOf(item.getValue());
			newScheme.setVendor(vendor);
			newScheme.setName(nameInput.getText());
			BigDecimal price = new BigDecimal(priceInput.getText().trim());
			if(price.compareTo(new BigDecimal("50")) == 0){
				newScheme.setType(SchemeType.CHIT_50);
			}
			if(price.compareTo(new BigDecimal("100")) == 0){
				newScheme.setType(SchemeType.CHIT_100);
			}
			if(price.compareTo(new BigDecimal("98")) == 0){
				newScheme.setType(SchemeType.SUIT_98);
			}
			if(price.compareTo(new BigDecimal("68")) == 0){
				newScheme.setType(SchemeType.SUIT_68);
			}
			if(price.compareTo(new BigDecimal("32")) == 0){
				newScheme.setType(SchemeType.SUIT_32);
			}
			newScheme.setPrice(price);
			newScheme.setPostPrice(new BigDecimal(postPriceInput.getText().trim()));
			newScheme.setSpread(new BigDecimal(spreadInput.getText().trim()));
			newScheme.setStartDate(DateUtil.parseDate(startInput.getText().trim(), "yyyyMMdd"));
			newScheme.setEndDate(DateUtil.parseDate(endInput.getText().trim(),"yyyyMMdd"));
			if(StringUtils.hasText(floorInput.getText())){
				newScheme.setFloorAmount(new BigDecimal(floorInput.getText().trim()));
			}
			if(StringUtils.hasText(ceilInput.getText())){
				newScheme.setCeilAmount(new BigDecimal(ceilInput.getText().trim()));
			}
			IMetadataService metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
			metaService.createScheme(newScheme);
			JOptionPane.showMessageDialog(null, "活动创建成功！");
			checkListener.refreshTableData();
			addForm.close();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+se.getMessage()+"</h3></html>"));
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void validation() throws ServiceException{
		if(vendorInput.getSelectedItem() == null){
			ExceptionManage.throwServiceException("请选择活动平台!");
		}
		if(!StringUtils.hasText(priceInput.getText())){
			throw new ServiceException("优惠金额必填!");
		}
		if(!StringUtils.hasText(postPriceInput.getText())){
			throw new ServiceException("平台补贴金额必填!");
		}
		if(!StringUtils.hasText(spreadInput.getText())){
			throw new ServiceException("餐厅补贴金额必填!");
		}
		if(!StringUtils.hasText(startInput.getText())){
			throw new ServiceException("开始时间必填!");
		}
		if(!StringUtils.hasText(endInput.getText())){
			throw new ServiceException("结束时间必填!");
		}
	}

}
