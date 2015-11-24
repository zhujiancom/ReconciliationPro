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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.InventoryTable;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.SelectedDishPanel;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.InventoryVO;

/**
 * 
 * remark (备注): 库存品种添加窗口
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：InventoryAddWinBuilder
 *
 * 包名称：com.rci.ui.swing.views.builder
 *
 * Create Time: 2015年11月24日 上午9:43:11
 *
 */
public class InventoryAddWinBuilder implements PopWindowBuilder,ActionListener {
	private static final Log logger = LogFactory.getLog(InventoryAddWinBuilder.class);
	private PopWindow addForm;
	private JPanel mainPane;
	private JLabel pname = new JLabel("品种名称");
	private JLabel ino = new JLabel("库存编号");
	private JLabel relatedDish = new JLabel("关联菜品");
	private JLabel cardinal = new JLabel("基数");
	private JTextField nameInput = new JTextField(30);
	private JTextField noInput = new JTextField(10);
	private JTextField cardinalInput = new JTextField(10);
	private SelectedDishPanel selectedDishPanel;;
	private JButton confirmBtn;
	private IMetadataService metaService;
	private JTable table;
	
	public InventoryAddWinBuilder(JTable table){
		this.table = table;
	}
	
	@Override
	public PopWindow retrieveWindow() {
		addForm = new PopWindow(350,300);
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		JPanel containerPanel = addForm.getContainerPanel();
		createFormPane();
		containerPanel.add(mainPane,BorderLayout.CENTER);
		return addForm;
	}

	private void createFormPane() {
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(pname);
		firstPane.add(nameInput);
		mainPane.add(firstPane);
		
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		secondPane.add(ino);
		secondPane.add(noInput);
		mainPane.add(secondPane);
		
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,2));
		fifthPane.add(cardinal);
		fifthPane.add(cardinalInput);
		mainPane.add(fifthPane);
		
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(relatedDish);
		selectedDishPanel = new SelectedDishPanel();
		thirdPane.add(selectedDishPanel);
		mainPane.add(thirdPane);
		
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmBtn = ButtonFactory.createImageButton("保存","skin/gray/images/64x64/saveBtn_0.png", null);
		confirmBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.addActionListener(this);
		forthPane.add(confirmBtn);
		mainPane.add(forthPane);
	}

	/**
	 * 保存按钮操作
	 */
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try{
			validation();
			InventoryVO inventoryvo = new InventoryVO();
			inventoryvo.setIname(nameInput.getText());
			inventoryvo.setIno(noInput.getText());
			inventoryvo.setCardinal(new BigDecimal(cardinalInput.getText().trim()));
			List<DishVO> selectedDishes = selectedDishPanel.getSelectedDishes();
			inventoryvo.setRelatedDishes(selectedDishes);
			metaService.createInventory(inventoryvo);
			JOptionPane.showMessageDialog(null, "库存产品创建成功！");
			addForm.close();
			((InventoryTable)table).reflush();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+se.getMessage()+"</h3></html>"));
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("添加库存品种出错", ex);
		}
	}

	public void validation() throws ServiceException{
		if(!StringUtils.hasText(nameInput.getText())){
			ExceptionManage.throwServiceException("品种名称必填!");
		}
		if(!StringUtils.hasText(noInput.getText())){
			ExceptionManage.throwServiceException("库存编号必填!");
		}
		if(!StringUtils.hasText(cardinalInput.getText())){
			ExceptionManage.throwServiceException("基数必填!");
		}
		String no = noInput.getText().trim();
		if(metaService.checkInventoryNoExist(no)){
			ExceptionManage.throwServiceException("库存编号已存在，请重新填写!");
		}
	}

}
