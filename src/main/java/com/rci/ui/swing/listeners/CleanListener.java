package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTableModel;

public class CleanListener implements ActionListener {
	private JTable mainTable;
	private JTable subTable;
	private IOrderService orderService;
	private IFetchMarkService markService;
	private JTextField timeInput;
	private JLabel posValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel eleFreeValue;
	private JLabel eleValue;
	private JLabel tddValue;
	private JLabel mtwmValue;
	private JLabel mtwmFreeValue;
	private JLabel freeValue;
	private JLabel totalValue;
	
	public CleanListener(JTable mainTable,JTable subTable){
		this.mainTable = mainTable;
		this.subTable = subTable;
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
		markService = (IFetchMarkService) SpringUtils.getBean("FetchMarkService");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		OrderTableModel orderModel = (OrderTableModel) mainTable.getModel();
		orderModel.setRowCount(0);
		OrderItemTableModel itemModel = (OrderItemTableModel) subTable.getModel();
		itemModel.setRowCount(0);
		String time = timeInput.getText();
		try{
			if(!StringUtils.hasText(time)){
				ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "请填写日期");
			}
			if(!DateUtil.isDateFormat(time,"yyyyMMdd")){
				ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
			}
			orderService.rwDeleteOrders(time);
			markService.rwDeleteMark(time);
			posValue.setText(BigDecimal.ZERO.toString());
			mtValue.setText(BigDecimal.ZERO.toString());
			tgValue.setText(BigDecimal.ZERO.toString());
			eleFreeValue.setText(BigDecimal.ZERO.toString());
			eleValue.setText(BigDecimal.ZERO.toString());
			tddValue.setText(BigDecimal.ZERO.toString());
			mtwmValue.setText(BigDecimal.ZERO.toString());
			mtwmFreeValue.setText(BigDecimal.ZERO.toString());
			freeValue.setText(BigDecimal.ZERO.toString());
			totalValue.setText(BigDecimal.ZERO.toString());
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
		}
	}

	public JTextField getTimeInput() {
		return timeInput;
	}

	public void setTimeInput(JTextField timeInput) {
		this.timeInput = timeInput;
	}

	public void setPosValue(JLabel posValue) {
		this.posValue = posValue;
	}

	public void setMtValue(JLabel mtValue) {
		this.mtValue = mtValue;
	}

	public void setTgValue(JLabel tgValue) {
		this.tgValue = tgValue;
	}

	public void setEleFreeValue(JLabel eleFreeValue) {
		this.eleFreeValue = eleFreeValue;
	}

	public void setEleValue(JLabel eleValue) {
		this.eleValue = eleValue;
	}

	public void setTddValue(JLabel tddValue) {
		this.tddValue = tddValue;
	}

	public void setMtwmValue(JLabel mtwmValue) {
		this.mtwmValue = mtwmValue;
	}

	public void setMtwmFreeValue(JLabel mtwmFreeValue) {
		this.mtwmFreeValue = mtwmFreeValue;
	}

	public void setFreeValue(JLabel freeValue) {
		this.freeValue = freeValue;
	}

	public void setTotalValue(JLabel totalValue) {
		this.totalValue = totalValue;
	}

}
