package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.IAccFlowService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
import com.rci.service.ITicketStatisticService;
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
	private IAccFlowService accFlowService;
	private ITicketStatisticService tsService;
	private JTextField timeInput;
	private JLabel posValue;
	private JLabel cashValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel shValue;
	private JLabel eleFreeValue;
	private JLabel eleValue;
	private JLabel tddValue;
	private JLabel mtwmValue;
	private JLabel mtwmFreeValue;
	private JLabel mtSuperValue;
	private JLabel mtSuperFreeValue;
	private JLabel freeValue;
	private JLabel totalValue;
	private JLabel tgRemark;
	private JLabel mtRemark;
	private JLabel expRateValue;
	
	public CleanListener(JTable mainTable,JTable subTable){
		this.mainTable = mainTable;
		this.subTable = subTable;
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
		markService = (IFetchMarkService) SpringUtils.getBean("FetchMarkService");
		accFlowService = (IAccFlowService) SpringUtils.getBean("AccFlowService");
		tsService = (ITicketStatisticService) SpringUtils.getBean("TicketStatisticService");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(mainTable.getModel() instanceof OrderTableModel){
			OrderTableModel orderModel = (OrderTableModel) mainTable.getModel();
			orderModel.setRowCount(0);
		}
		if(subTable.getModel() instanceof OrderItemTableModel){
			OrderItemTableModel itemModel = (OrderItemTableModel) subTable.getModel();
			itemModel.setRowCount(0);
		}
		String time = timeInput.getText();
		try{
			if(!StringUtils.hasText(time)){
				ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "请填写日期");
			}
			if(!DateUtil.isDateFormat(time,"yyyyMMdd")){
				ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
			}
			orderService.deleteOrders(time);
			markService.deleteMark(time);
			accFlowService.rwDeleteFlowInfo(time,DataGenerateType.AUTO);
			tsService.deleteTicketStatistic(time);
			cashValue.setText(null);
			posValue.setText(null);
			mtValue.setText(null);
			tgValue.setText(null);
			shValue.setText(null);
			eleFreeValue.setText(null);
			eleValue.setText(null);
			tddValue.setText(null);
			mtwmValue.setText(null);
			mtwmFreeValue.setText(null);
			mtSuperValue.setText(null);
			mtSuperFreeValue.setText(null);
			freeValue.setText(null);
			totalValue.setText(null);
			tgRemark.setText("");
			mtRemark.setText("");
			expRateValue.setText("");
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

	/**
	 * @param mtSuperValue the mtSuperValue to set
	 */
	public void setMtSuperValue(JLabel mtSuperValue) {
		this.mtSuperValue = mtSuperValue;
	}

	/**
	 * @param mtSuperFreeValue the mtSuperFreeValue to set
	 */
	public void setMtSuperFreeValue(JLabel mtSuperFreeValue) {
		this.mtSuperFreeValue = mtSuperFreeValue;
	}

	public void setShValue(JLabel shValue) {
		this.shValue = shValue;
	}

	public void setCashValue(JLabel cashValue) {
		this.cashValue = cashValue;
	}

	public void setTgRemark(JLabel tgRemark) {
		this.tgRemark = tgRemark;
	}

	public void setMtRemark(JLabel mtRemark) {
		this.mtRemark = mtRemark;
	}

	/**
	 * @param expRateValue the expRateValue to set
	 */
	public void setExpRateValue(JLabel expRateValue) {
		this.expRateValue = expRateValue;
	}

}
