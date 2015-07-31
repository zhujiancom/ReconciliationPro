package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.core.StatisticCenterFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.TurnoverTable;
import com.rci.ui.swing.views.PopWindow;

public class TurnoverWinBuilder implements PopWindowBuilder {
	private JPanel queryPane;
	private JScrollPane contentPane;
	private JPanel conclusionPane;
	private JTable table;
	
	@Override
	public PopWindow retrieveWindow() {
		PopWindow turnoverWindow = new PopWindow();
		createQueryPane();
		createContentPane();
		Container containerPanel = turnoverWindow.getContentPane();
		containerPanel.add(queryPane,BorderLayout.NORTH);
		containerPanel.add(contentPane,BorderLayout.CENTER);
		return turnoverWindow;
	}

	@Override
	public void createQueryPane() {
		queryPane = new JPanel();
		JLabel sdateLabel = new JLabel("开始时间");
		final JTextField sdateInput = new JTextField(20);
		JLabel edateLabel = new JLabel("结束时间");
		final JTextField edateInput = new JTextField(20);
		JButton queryBtn = new JButton("查询");
		queryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String sdate = sdateInput.getText();
					String edate = edateInput.getText();
					if(!StringUtils.hasText(sdate)){
						ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "开始日期不能是空");
					}
					if(!DateUtil.isDateFormat(sdate,"yyyyMMdd")){
						ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
					}
					if(StringUtils.hasText(edate) && !DateUtil.isDateFormat(edate,"yyyyMMdd")){
						ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
					}
					Date ssdate = DateUtil.parseDate(sdate, "yyyyMMdd");
					Date eedate = null;
					if(StringUtils.hasText(edate)){
						eedate = DateUtil.parseDate(edate, "yyyyMMdd");
					}
					StatisticCenterFacade scf = (StatisticCenterFacade) SpringUtils.getBean("StatisticCenterFacade");
//					List<ExpressRateVO> rates = scf.getExpressRateList(ssdate,eedate);
//					ExpressRateTabelModel dm = (ExpressRateTabelModel) table.getModel();
//					dm.setItems(rates);
//					dm.fireTableDataChanged();
				}catch(ServiceException se){
					JOptionPane.showMessageDialog(null, se.getMessage());
				} catch (ParseException pe) {
					JOptionPane.showMessageDialog(null, pe.getMessage());
				}
			}
		});
		queryPane.add(sdateLabel);
		queryPane.add(sdateInput);
		queryPane.add(edateLabel);
		queryPane.add(edateInput);
		queryPane.add(queryBtn);
	}

	@Override
	public void createContentPane() {
		table = new TurnoverTable(12);
		contentPane = new JScrollPane(table);
		contentPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	@Override
	public void createBottomPane() {
	}

}
