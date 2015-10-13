package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;

import com.rci.bean.LabelValueBean;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.VendorComboBoxModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.SchemeVO;

public class SchemeModifyWinBuilder implements PopWindowBuilder,MouseListener {
	private Log logger = LogFactory.getLog(SchemeModifyWinBuilder.class);
	private PopWindow modifyForm;
	private SchemeVO data;
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
	private JButton confirmBtn;
	private JButton activeBtn;
	private JButton inactiveBtn;
	IMetadataService metaService;
	
	public SchemeModifyWinBuilder(SchemeVO data) {
		super();
		this.data = data;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}

	@Override
	public PopWindow retrieveWindow() {
		modifyForm = new PopWindow(350,500);
		JPanel containerPanel = modifyForm.getContainerPanel();
		createContentPane();
		containerPanel.add(sPane,BorderLayout.CENTER);
		confirmBtn.addMouseListener(this);
		activeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				metaService.activeScheme(data.getSid());
				JOptionPane.showMessageDialog(null, "活动已启用！");
				modifyForm.close();
			}
		});
		inactiveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				metaService.inactiveScheme(data.getSid());
				JOptionPane.showMessageDialog(null, "活动已禁用！");
				modifyForm.close();
			}
		});
		return modifyForm;
	}

	@Override
	public void createQueryPane() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createContentPane() {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		sPane = new JScrollPane(mainPane);
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(name);
		nameInput = new JTextField(data.getName(),30);
		firstPane.add(nameInput);
		mainPane.add(firstPane);
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
		secondPane.add(vendor);
		List<LabelValueBean<String>> itemList = EnumUtils.getEnumLabelValueBeanList(Vendor.class, false);
		VendorComboBoxModel vcm = new VendorComboBoxModel(itemList);
		vendorInput = new JComboBox<LabelValueBean<String>>(vcm);
		LabelValueBean<String> labelvalue = new LabelValueBean<String>(data.getDishplayVendor(),data.getVendor().name());
		vendorInput.setSelectedItem(labelvalue);
		secondPane.add(vendorInput);
		mainPane.add(secondPane);
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(price);
		priceInput = new JTextField(StringUtils.trimToEmpty(data.getPrice()),30);
		thirdPane.add(priceInput);
		mainPane.add(thirdPane);
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(postPrice);
		postPriceInput = new JTextField(StringUtils.trimToEmpty(data.getPostPrice()),30);
		forthPane.add(postPriceInput);
		mainPane.add(forthPane);
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		fifthPane.add(spread);
		spreadInput = new JTextField(StringUtils.trimToEmpty(data.getSpread()),30);
		fifthPane.add(spreadInput);
		mainPane.add(fifthPane);
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		sixthPane.add(start);
		startInput = new JTextField(DateUtil.date2Str(data.getStartDate(), "yyyyMMdd"),30);
		sixthPane.add(startInput);
		mainPane.add(sixthPane);
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		seventhPane.add(end);
		endInput = new JTextField(DateUtil.date2Str(data.getEndDate(), "yyyyMMdd"),30);
		seventhPane.add(endInput);
		mainPane.add(seventhPane);
		JPanel eightthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eightthPane.add(floor);
		floorInput = new JTextField(StringUtils.trimToEmpty(data.getFloorAmount()),30);
		eightthPane.add(floorInput);
		mainPane.add(eightthPane);
		JPanel ninthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ninthPane.add(ceil);
		ceilInput = new JTextField(StringUtils.trimToEmpty(data.getCeilAmount()),30);
		ninthPane.add(ceilInput);
		mainPane.add(ninthPane);
		JPanel tenthPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		URL confirmBtnUrl = this.getClass().getClassLoader().getResource("skin/submitBtn.png");
		confirmBtn = new JButton(new ImageIcon(confirmBtnUrl));
		confirmBtn.setUI(new BasicButtonUI());
		confirmBtn.setContentAreaFilled(false);
		confirmBtn.setMargin(new Insets(0,0,0,0));
		confirmBtn.setPreferredSize(new Dimension(64,64));
		activeBtn = new JButton("启用");
		inactiveBtn = new JButton("停用");
		if(ActivityStatus.ACTIVE.equals(data.getActivityStatus())){
			activeBtn.setEnabled(false);
			inactiveBtn.setEnabled(true);
		}
		if(ActivityStatus.INACTIVE.equals(data.getActivityStatus())){
			activeBtn.setEnabled(true);
			inactiveBtn.setEnabled(false);
		}
		tenthPane.add(confirmBtn);
		tenthPane.add(activeBtn);
		tenthPane.add(inactiveBtn);
		mainPane.add(tenthPane);
	}

	@Override
	public void createBottomPane() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try{
			@SuppressWarnings("unchecked")
			LabelValueBean<String> item = (LabelValueBean<String>) vendorInput.getSelectedItem();
			String vendor = item.getValue();
			data.setVendor(Vendor.valueOf(vendor));
			data.setName(nameInput.getText());
			data.setPrice(new BigDecimal(priceInput.getText().trim()));
			data.setPostPrice(new BigDecimal(postPriceInput.getText().trim()));
			data.setSpread(new BigDecimal(spreadInput.getText().trim()));
			if(!StringUtils.hasText(startInput.getText())){
				throw new ServiceException("开始时间必填");
			}
			data.setStartDate(DateUtil.parseDate(startInput.getText().trim(), "yyyyMMdd"));
			if(!StringUtils.hasText(endInput.getText())){
				throw new ServiceException("结束时间必填");
			}
			data.setEndDate(DateUtil.parseDate(endInput.getText().trim(),"yyyyMMdd"));
			if(StringUtils.hasText(floorInput.getText())){
				data.setFloorAmount(new BigDecimal(floorInput.getText().trim()));
			}
			if(StringUtils.hasText(ceilInput.getText())){
				data.setCeilAmount(new BigDecimal(StringUtils.trim(ceilInput.getText())));
			}
			metaService.updateScheme(data);
			JOptionPane.showMessageDialog(null, "活动修改成功！");
			modifyForm.close();
		}catch (Exception ex){
			logger.error(ex);
			JOptionPane.showMessageDialog(null, "请填写必填项");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
