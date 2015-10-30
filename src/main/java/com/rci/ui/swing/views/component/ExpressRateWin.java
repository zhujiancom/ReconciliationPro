package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.core.StatisticCenterFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.ExpressRateTabel;
import com.rci.ui.swing.model.ExpressRateTabel.ExpressRateTabelModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.ExpressRateVO;

public class ExpressRateWin extends PopWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7037359594917766875L;
	private ExpressRateTabel table;
	private JTextField sdateInput;
	private JTextField edateInput;
	
	public ExpressRateWin(String title){
		super(title);
		createQueryPane();
		createContentPane();
	}
	
	/**
	 * 
	 * Describle(描述)： 查询面板
	 *
	 * 方法名称：createQueryPane
	 *
	 * 所在类名：ExpressRateWin
	 *
	 * Create Time:2015年10月30日 上午10:47:53
	 *
	 */
	public void createQueryPane() {
		JPanel queryPane = new JPanel();
		JLabel sdateLabel = new JLabel("开始时间");
		sdateInput = new JTextField(20);
		JLabel edateLabel = new JLabel("结束时间");
		edateInput = new JTextField(20);
		JButton queryBtn = ButtonFactory.createImageButton("查询", "skin/gray/images/24x24/search.png", null);
		ActionListener listener = new ExpressRateQueryListener();
		queryBtn.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryBtn.addActionListener(listener);
		queryPane.add(sdateLabel);
		queryPane.add(sdateInput);
		queryPane.add(edateLabel);
		queryPane.add(edateInput);
		queryPane.add(queryBtn);
		
		JPanel containerPanel = this.getContainerPanel();
		containerPanel.add(queryPane,BorderLayout.NORTH);
	}

	/**
	 * 
	 * Describle(描述)： 创建内容面板
	 *
	 * 方法名称：createContentPane
	 *
	 * 所在类名：ExpressRateWin
	 *
	 * Create Time:2015年10月30日 上午10:48:27
	 *
	 */
	public void createContentPane() {
		JScrollPane contentPane = new JScrollPane();
		table = new ExpressRateTabel(3);
		contentPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.setViewportView(table);
		JPanel containerPanel = this.getContainerPanel();
		containerPanel.add(contentPane,BorderLayout.CENTER);
	}
	
	private class ExpressRateQueryListener implements ActionListener{

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
				List<ExpressRateVO> rates = scf.getExpressRateList(ssdate,eedate);
				ExpressRateTabelModel dm = (ExpressRateTabelModel) table.getModel();
				dm.setItems(rates);
				dm.fireTableDataChanged();
			}catch(ServiceException se){
				JOptionPane.showMessageDialog(null, se.getMessage());
			} catch (ParseException pe) {
				JOptionPane.showMessageDialog(null, pe.getMessage());
			}
		}
		
	}
	
}
