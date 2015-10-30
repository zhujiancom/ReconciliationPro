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
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;

import com.rci.bean.LabelValueBean;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionManage;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.listeners.VendorCheckListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.SchemeTable.SchemeTabelModel;
import com.rci.ui.swing.model.VendorComboBoxModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.SchemeVO;

public class SchemeModifyWinBuilder implements PopWindowBuilder,ActionListener {
	private Log logger = LogFactory.getLog(SchemeModifyWinBuilder.class);
	private PopWindow modifyForm;
	private SchemeVO scheme;
	private JScrollPane sPane;
	private JTextField nameInput;
	private JComboBox<LabelValueBean<String>> vendorInput;
	private JTextField priceInput ;
	private JTextField postPriceInput;
	private JTextField spreadInput;
	private JTextField startInput;
	private JTextField endInput;
	private JTextField floorInput;
	private JTextField ceilInput;
	private JLabel name = new JLabel("活动名称");
	private JLabel vendor = new JLabel("活动平台");
	private JLabel price = new JLabel("优惠金额");
	private JLabel postPrice = new JLabel("平台补贴");
	private JLabel spread = new JLabel("餐厅补贴");
	private JLabel start = new JLabel("开始时间");
	private JLabel end = new JLabel("结束时间");
	private JLabel floor = new JLabel("最低消费金额");
	private JLabel ceil = new JLabel("最高消费金额");
	private JLabel createTime = new JLabel("创建时间");
	private JLabel modifyTime = new JLabel("修改时间");
	private JButton confirmBtn;
	private JButton activeBtn;
	private JButton inactiveBtn;
	IMetadataService metaService;
	private VendorCheckListener checkListener;
	
	public SchemeModifyWinBuilder(VendorCheckListener checkListener) {
		this.checkListener = checkListener;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}

	@Override
	public PopWindow retrieveWindow() {
		modifyForm = new PopWindow(350,500);
		JPanel containerPanel = modifyForm.getContainerPanel();
		JTable table = checkListener.getTable();
		int selectIndex = table.getSelectedRow();
		SchemeTabelModel model = (SchemeTabelModel) table.getModel();
		scheme = model.getScheme(selectIndex);
		/* 创建窗口主体 */
		createContentPane();
		containerPanel.add(sPane,BorderLayout.CENTER);
		return modifyForm;
	}

	public void createContentPane() {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		sPane = new JScrollPane(mainPane);
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(name);
		nameInput = new JTextField(scheme.getName(),30);
		firstPane.add(nameInput);
		mainPane.add(firstPane);
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
		secondPane.add(vendor);
		List<LabelValueBean<String>> itemList = EnumUtils.getEnumLabelValueBeanList(Vendor.class, false);
		VendorComboBoxModel vcm = new VendorComboBoxModel(itemList);
		vendorInput = new JComboBox<LabelValueBean<String>>(vcm);
		LabelValueBean<String> labelvalue = new LabelValueBean<String>(scheme.getDishplayVendor(),scheme.getVendor().name());
		vendorInput.setSelectedItem(labelvalue);
		secondPane.add(vendorInput);
		mainPane.add(secondPane);
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(price);
		priceInput = new JTextField(StringUtils.trimToEmpty(scheme.getPrice()),30);
		thirdPane.add(priceInput);
		mainPane.add(thirdPane);
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(postPrice);
		postPriceInput = new JTextField(StringUtils.trimToEmpty(scheme.getPostPrice()),30);
		forthPane.add(postPriceInput);
		mainPane.add(forthPane);
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		fifthPane.add(spread);
		spreadInput = new JTextField(StringUtils.trimToEmpty(scheme.getSpread()),30);
		fifthPane.add(spreadInput);
		mainPane.add(fifthPane);
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		sixthPane.add(start);
		startInput = new JTextField(DateUtil.date2Str(scheme.getStartDate(), "yyyyMMdd"),30);
		sixthPane.add(startInput);
		mainPane.add(sixthPane);
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		seventhPane.add(end);
		endInput = new JTextField(DateUtil.date2Str(scheme.getEndDate(), "yyyyMMdd"),30);
		seventhPane.add(endInput);
		mainPane.add(seventhPane);
		JPanel eightthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eightthPane.add(floor);
		floorInput = new JTextField(StringUtils.trimToEmpty(scheme.getFloorAmount()),30);
		eightthPane.add(floorInput);
		mainPane.add(eightthPane);
		JPanel ninthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ninthPane.add(ceil);
		ceilInput = new JTextField(StringUtils.trimToEmpty(scheme.getCeilAmount()),30);
		ninthPane.add(ceilInput);
		mainPane.add(ninthPane);
		JPanel elevenPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		elevenPane.add(createTime);
		elevenPane.add(new JLabel(DateUtil.time2Str(scheme.getCreateTime())));
		mainPane.add(elevenPane);
		JPanel twelvePane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		twelvePane.add(modifyTime);
		twelvePane.add(new JLabel(DateUtil.time2Str(scheme.getModifyTime())));
		mainPane.add(twelvePane);
		JPanel tenthPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/saveBtn_0.png", null);
		confirmBtn.addActionListener(this);
		activeBtn = new JButton("启用");
		inactiveBtn = new JButton("停用");
		if(ActivityStatus.ACTIVE.equals(scheme.getActivityStatus())){
			activeBtn.setEnabled(false);
			inactiveBtn.setEnabled(true);
		}
		if(ActivityStatus.INACTIVE.equals(scheme.getActivityStatus())){
			activeBtn.setEnabled(true);
			inactiveBtn.setEnabled(false);
		}
		activeBtn.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				metaService.activeScheme(scheme.getSid());
				JOptionPane.showMessageDialog(null, "活动已启用！");
				checkListener.refreshTableData();
				modifyForm.close();
			}
		});
		inactiveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				metaService.inactiveScheme(scheme.getSid());
				JOptionPane.showMessageDialog(null, "活动已禁用！");
				checkListener.refreshTableData();
				modifyForm.close();
			}
		});
		tenthPane.add(confirmBtn);
		tenthPane.add(activeBtn);
		tenthPane.add(inactiveBtn);
		mainPane.add(tenthPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			validation();
			@SuppressWarnings("unchecked")
			LabelValueBean<String> item = (LabelValueBean<String>) vendorInput.getSelectedItem();
			String vendor = item.getValue();
			scheme.setVendor(Vendor.valueOf(vendor));
			scheme.setName(nameInput.getText());
			scheme.setPrice(new BigDecimal(priceInput.getText().trim()));
			scheme.setPostPrice(new BigDecimal(postPriceInput.getText().trim()));
			scheme.setSpread(new BigDecimal(spreadInput.getText().trim()));
			scheme.setEndDate(DateUtil.parseDate(endInput.getText().trim(),"yyyyMMdd"));
			if(StringUtils.hasText(floorInput.getText())){
				scheme.setFloorAmount(new BigDecimal(floorInput.getText().trim()));
			}
			if(StringUtils.hasText(ceilInput.getText())){
				scheme.setCeilAmount(new BigDecimal(StringUtils.trim(ceilInput.getText())));
			}
			metaService.updateScheme(scheme);
			JOptionPane.showMessageDialog(null, "活动修改成功！");
			checkListener.refreshTableData();
			modifyForm.close();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+se.getMessage()+"</h3></html>"));
		}catch (Exception ex){
			logger.error(ex);
			JOptionPane.showMessageDialog(null, "请填写必填项");
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
