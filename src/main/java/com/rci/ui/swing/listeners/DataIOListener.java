package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

import org.springframework.util.CollectionUtils;

import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.exceptions.ServiceException;
import com.rci.service.IDataLoaderService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.handler.ExcelDataImport;
import com.rci.ui.swing.model.OrderItemTable.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.builder.ProgressWinBuilder;
import com.rci.ui.swing.views.builder.WindowBuilderFactory;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

public class DataIOListener implements ActionListener {
	public static final int EXPORT = 0;
	public static final int IMPORT = 1;
	
	private int action;
	
//	private IDataFetchService fetchService;
	
	private ContentPanel contentPane;
	
	private ConculsionPanel conclusionPane;
	
	private Map<AccountCode,BigDecimal> sumMap;
	
	public DataIOListener(int action){
		this.action = action;
//		fetchService = (IDataFetchService) SpringUtils.getBean("DataFetchService");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(action == EXPORT){
			exportData();
		}else if(action == IMPORT){
			importData();
		}
	}
	
	/**
	 * 
	 * Describle(描述)： 数据导出
	 *
	 * 方法名称：exportData
	 *
	 * 所在类名：DataIOHandler
	 *
	 * Create Time:2015年8月25日 上午10:20:38
	 *
	 */
	public void exportData(){
		String name = DateUtil.date2Str(new Date(), "yyyyMMdd") + ".xls";
		// 构造文件保存对话框
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("原始数据文件导出");
		// 设置文件名称
		chooser.setSelectedFile(new File(name));
		// 添加文件过滤器
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return true;
			}

			@Override
			public String getDescription() {
				return "所有文件(*.*)";
			}

		});
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith("xls") || f.getName().endsWith("xlsx") || f.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public String getDescription() {
				return "Excel文件(*.xls)";
			}

		});
		int result = chooser.showSaveDialog(null);
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			ProgressWinBuilder progressBarBuilder = WindowBuilderFactory.createProgressWinBuilder();
			PopWindow progressBarWin = progressBarBuilder.retrieveWindow();
			final JProgressBar bar = progressBarBuilder.getBar();
			final ExcelDataImport target = new ExcelDataImport(chooser,progressBarWin);
			new Thread(target).start();
			
			bar.setMinimum(0);
			bar.setMaximum(target.getAmount());
			Timer timer = new Timer(300,new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					bar.setValue(target.getCurrent());
					
				}
			});
			timer.start();
			
			break;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("取消");
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
		}
	}
	
	/**
	 * 
	 * Describle(描述)： 数据导入
	 *
	 * 方法名称：importData
	 *
	 * 所在类名：DataIOHandler
	 *
	 * Create Time:2015年8月25日 上午10:20:24
	 *
	 */
	public void importData(){
		// 构造文件保存对话框
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("原始数据文件导入");
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return true;
			}

			@Override
			public String getDescription() {
				return "所有文件(*.*)";
			}

		});
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith("xls") || f.getName().endsWith("xlsx") || f.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public String getDescription() {
				return "Excel文件(*.xlsx)";
			}
		});
		int result = chooser.showOpenDialog(null);
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					String fileName = chooser.getSelectedFile().getName();
					String day = fileName.substring(0,fileName.indexOf("."));
					try {
						Date date = DateUtil.parseDate(day, "yyyyMMdd");
						BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
						IDataLoaderService loaderService = (IDataLoaderService) SpringUtils.getBean("ExcelDataLoaderService");
						loaderService.load(bin, date);
						IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
						List<OrderVO> ordervos = orderService.accquireOrderVOsByDay(day);
						OrderTableModel otm = (OrderTableModel) contentPane.getMainTable().getModel();
						otm.setOrders(ordervos);
						otm.fireTableDataChanged();
						if(!CollectionUtils.isEmpty(ordervos)){
							OrderVO order = otm.getOrderAt(0);
							contentPane.getMainTable().setRowSelectionAllowed(true);
							contentPane.getMainTable().setRowSelectionInterval(0, 0);
							loadItemData(order.getPayNo());
							for(OrderVO ov:ordervos){
								if(StringUtils.hasText(ov.getWarningInfo())){
									contentPane.getTextArea().append("【"+ov.getPayNo()+"】"+ov.getWarningInfo()+"\n");
								}
							}
						}
						//2. 根据订单数据统计今日收入明细
						loadSumData(date);
						conclusionPane.refreshUI();
						JOptionPane.showMessageDialog(null, "导入成功");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch(ServiceException se){
						JOptionPane.showMessageDialog(null, se.getMessage());
					} catch(Exception e){
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}).start();
			break;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("取消");
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
		}
	}
	
	public void loadItemData(String payno){
		IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
		OrderItemTableModel oitm = (OrderItemTableModel) contentPane.getItemTable().getModel();
		oitm.setItems(orderItems);
		oitm.fireTableDataChanged();
	}
	
	private void loadSumData(Date date){
		sumMap = new HashMap<AccountCode,BigDecimal>();
		IOrderAccountRefService oaService = (IOrderAccountRefService) SpringUtils.getBean("OrderAccountRefService");
		List<AccountSumResult> sumRes = oaService.querySumAmount(date);
		for(AccountSumResult res:sumRes){
			AccountCode accNo = res.getAccNo();
			BigDecimal amount = res.getSumAmount();
			sumMap.put(accNo, amount);
		}
		conclusionPane.setQueryDate(date);
		conclusionPane.setSumMap(sumMap);
	}
	
	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * @return the contentPane
	 */
	public ContentPanel getContentPane() {
		return contentPane;
	}

	/**
	 * @param contentPane the contentPane to set
	 */
	public void setContentPane(ContentPanel contentPane) {
		this.contentPane = contentPane;
	}

	/**
	 * @return the conclusionPane
	 */
	public ConculsionPanel getConclusionPane() {
		return conclusionPane;
	}

	/**
	 * @param conclusionPane the conclusionPane to set
	 */
	public void setConclusionPane(ConculsionPanel conclusionPane) {
		this.conclusionPane = conclusionPane;
	}
}
