package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

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
import com.rci.ui.swing.handler.InventoryWarningHandler;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.InventoryTable;
import com.rci.ui.swing.model.InventoryTable.InventoryTabelModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.InventoryVO;

public class InventoryPurchaseWin extends PopWindow implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9156143832303454329L;
	private static final Log logger = LogFactory.getLog(InventoryPurchaseWin.class);
	private JTable table;
	private JLabel pname = new JLabel("品种名称");
	private JLabel ino = new JLabel("库存编号");
	private JLabel relatedDish = new JLabel("已关联菜品");
	private JLabel totalAmount = new JLabel("库存总量");
	private JLabel balanceAmount = new JLabel("库存余量");
	private JLabel consumeAmount = new JLabel("消费数量");
	private JLabel cardinal = new JLabel("基数");
	private JLabel purchaseAmount = new JLabel("进货数量");
	private JTextField purchaseAmountInput = new JTextField(20);
	private JButton confirmBtn;
	private InventoryVO inventory;
	private IMetadataService metaService;
	
	public InventoryPurchaseWin(JTable table,int width,int height){
		super(width,height,"库存详细信息");
		this.table = table;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}

	private void initComponent() {
		int selectIndex = table.getSelectedRow();
		InventoryTabelModel model = (InventoryTabelModel) table.getModel();
		inventory = model.getInventory(selectIndex);
		
		JPanel containerPanel = this.getContainerPanel();
		JPanel mainPane = new JPanel();
		containerPanel.add(mainPane,BorderLayout.CENTER);
		
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(pname);
		firstPane.add(new JLabel(inventory.getIname()));
		mainPane.add(firstPane);
		
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		secondPane.add(ino);
		secondPane.add(new JLabel(inventory.getIno()));
		mainPane.add(secondPane);
		
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(totalAmount);
		forthPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getTotalAmount())));
		mainPane.add(forthPane);
		
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		sixthPane.add(balanceAmount);
		sixthPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getBalanceAmount())));
		mainPane.add(sixthPane);
		
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		seventhPane.add(consumeAmount);
		seventhPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getConsumeAmount())));
		mainPane.add(seventhPane);
		
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,2));
		fifthPane.add(cardinal);
		fifthPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getCardinal())));
		mainPane.add(fifthPane);
		
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(relatedDish);
		thirdPane.add(new JLabel(inventory.getRelatedDishNames()));
		mainPane.add(thirdPane);
		
		JPanel eightPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		eightPane.add(purchaseAmount);
		eightPane.add(purchaseAmountInput);
		mainPane.add(eightPane);
		
		JPanel ninePane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmBtn = ButtonFactory.createImageButton("保存","skin/gray/images/64x64/saveBtn_0.png", null);
		confirmBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.addActionListener(this);
		ninePane.add(confirmBtn);
		mainPane.add(ninePane);
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try{
			validation();
			BigDecimal purchaseAmountNum = new BigDecimal(purchaseAmountInput.getText().trim());
			metaService.purchaseInventory(inventory,purchaseAmountNum);
			JOptionPane.showMessageDialog(null, "进货成功！");
			this.close();
			((InventoryTable)table).reflush();
			InventoryWarningHandler.getInstance().displayWarningInfo();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><h4 color='red'>"+se.getMessage()+"</h3></html>"));
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("库存品种【"+inventory+"】进货出错", ex);
		}
	}
	
	private void validation() throws ServiceException{
		if(!StringUtils.hasText(purchaseAmountInput.getText())){
			ExceptionManage.throwServiceException("请填写进货数量!");
		}
		BigDecimal purchaseAmount = new BigDecimal(purchaseAmountInput.getText().toString());
		if(purchaseAmount.compareTo(BigDecimal.ZERO) <= 0){
			ExceptionManage.throwServiceException("进货数量必须大于0!");
		}
	}
}
