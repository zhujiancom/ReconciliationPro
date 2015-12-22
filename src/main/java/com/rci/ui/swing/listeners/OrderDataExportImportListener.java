package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.springframework.util.CollectionUtils;

import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.service.IDataLoaderService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.service.utils.IExImportService;
import com.rci.service.utils.excel.ExcelSheet;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.CheckedoutOrderPanel;
import com.rci.ui.swing.handler.DataExport;
import com.rci.ui.swing.model.OrderItemTable;
import com.rci.ui.swing.model.OrderItemTable.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTable;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.views.component.encapsulation.MaskDialog;
import com.rci.ui.swing.vos.OrderVO;

public class OrderDataExportImportListener extends DataExportImportListener implements ListSelectionListener {
	private JFrame frame;
	
	private ConculsionPanel conclusionPane; // 统计信息面板
	private QueryFormPanel queryPane; // 查询面板
	ContentPanel contentPane;
	private Map<AccountCode,BigDecimal> sumMap;
	
	public OrderDataExportImportListener(JFrame frame){
		this.frame = frame;
	}

	@Override
	protected String getFileName() {
		return DateUtil.date2Str(new Date(), "yyyyMMdd") + ".xls";
	}

	@Override
	protected String getDialogTitle() {
		return "原始数据文件导出";
	}
	
	@Override
	protected void doDataExport(){
		final MaskDialog dialog = new MaskDialog(frame);
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				dialog.loading("数据正在导出,请稍后...");
			}
		});
		IExImportService service = (IExImportService) SpringUtils.getBean("OrderExcelService");
		DataExport export = new DataExport(fileChooser,service);
		try{
			IDataFetchService fetchService = (IDataFetchService) SpringUtils.getBean("DataFetchService");
			String fileName = fileChooser.getSelectedFile().getName();
			List<ExcelSheet> sheets = new ArrayList<ExcelSheet>();
			String dateStr = fileName.substring(0,fileName.indexOf("."));
			Date exportDate = DateUtil.parseDate(dateStr, "yyyyMMdd");
			ExcelSheet sheet1 = new ExcelSheet(OrderDTO.class,"订单列表");
			List<OrderDTO> orders = fetchService.fetchOrders(exportDate, DateUtil.addDays(exportDate, 1));
			if(CollectionUtils.isEmpty(orders)){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "没有数据，备份文件失败");
			}
			sheet1.setDataset(orders);
			
			ExcelSheet sheet2 = new ExcelSheet(OrderItemDTO.class,"菜品销售列表");
			List<OrderItemDTO> orderItems = fetchService.fetchOrderItems(exportDate, DateUtil.addDays(exportDate, 1));
			sheet2.setDataset(orderItems);
			
			sheets.add(sheet1);
			sheets.add(sheet2);
			export.setSheets(sheets);
			new Thread(export).start();
		}catch(Exception e){
			dialog.error(e.getMessage());
			ExceptionManage.throwServiceException(e.getMessage(),e);
		}
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				dialog.done("数据导出成功!");
			}
		});
	}

	@Override
	public ActionListener importData() {
		final OrderDataExportImportListener loadItemListener = this;
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable(){

					@Override
					public void run() {
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
									final String day = fileName.substring(0,fileName.indexOf("."));
									final MaskDialog dialog = new MaskDialog(frame);
									try {
										SwingUtilities.invokeLater(new Runnable() {
											
											@Override
											public void run() {
												dialog.loading("数据正在导入，请稍后...");
											}
										});
										Date date = DateUtil.parseDate(day, "yyyyMMdd");
										BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
										IDataLoaderService loaderService = (IDataLoaderService) SpringUtils.getBean("OrderExcelDataLoaderService");
										loaderService.load(bin, date);
										
										CheckedoutOrderPanel rootPanel = (CheckedoutOrderPanel) frame.getContentPane().getComponent(0);
										conclusionPane = rootPanel.getConclusionPane();
										contentPane = rootPanel.getContentPane();
										queryPane = rootPanel.getQueryPanel();
										contentPane.getMainTable().getSelectionModel().addListSelectionListener(loadItemListener);
										
										IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
										List<OrderVO> ordervos = orderService.accquireOrderVOsByDay(day);
										
										OrderTableModel otm = (OrderTableModel) contentPane.getMainTable().getModel();
										OrderItemTableModel ottm = (OrderItemTableModel) contentPane.getItemTable().getModel();
										if(!CollectionUtils.isEmpty(ordervos)){
											((OrderTable)contentPane.getMainTable()).reflushTable(ordervos);
											OrderVO order = otm.getOrderAt(0); 
											((OrderItemTable)contentPane.getItemTable()).reflushTable(order.getPayNo());
											//2. 根据订单数据统计今日收入明细
											loadSumData(date);
											conclusionPane.refreshUI();
											SwingUtilities.invokeLater(new Runnable() {
												
												@Override
												public void run() {
													queryPane.getTimeInput().setText(day);
													queryPane.displayInfoDone("查询完毕");
												}
											});
										}else{
											otm.setRowCount(0);
											ottm.setRowCount(0);
											SwingUtilities.invokeLater(new Runnable() {
											
												@Override
												public void run() {
													conclusionPane.clearData();
													queryPane.displayInfoDone("没有记录！");
												}
											});
										}
										
										SwingUtilities.invokeLater(new Runnable() {
											
											@Override
											public void run() {
												dialog.done("数据导入成功！");
											}
										});
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									} catch (ParseException e) {
										e.printStackTrace();
									} catch(ServiceException se){
										JOptionPane.showMessageDialog(null, se.getMessage());
									} catch(Exception e){
										e.printStackTrace();
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
								}
							}).start();
							break;
						case JFileChooser.CANCEL_OPTION:
							System.out.println("取消");
							break;
						case JFileChooser.ERROR_OPTION:
							System.out.println("Error");
							break;
						}
					}
				}).start();;
			}
		};
	}

//	@Override
//	public void valueChanged(ListSelectionEvent event) {
//		if(event.getSource() == contentPane.getMainTable().getSelectionModel()
//				&& contentPane.getMainTable().getRowSelectionAllowed()){
//			int row = contentPane.getMainTable().getSelectedRow();
//			if(row != -1){
//				String payno = (String) contentPane.getMainTable().getValueAt(row, 2);
//				loadItemData(payno);
//			}
//		}
//	}
	
	/**
	 * 
	 * Describle(描述)：加载订单详细数据
	 *
	 * 方法名称：loadItemData
	 *
	 * 所在类名：OrderDataExportListener
	 *
	 * Create Time:2015年10月28日 上午9:55:00
	 *  
	 * @param payno
	 */
//	private void loadItemData(String payno){
//		IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
//		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
//		OrderItemTableModel oitm = (OrderItemTableModel) contentPane.getItemTable().getModel();
//		oitm.setItems(orderItems);
//		oitm.fireTableDataChanged();
//	}
	
	/**
	 * 
	 * Describle(描述)： 加载订单统计数据
	 *
	 * 方法名称：loadSumData
	 *
	 * 所在类名：OrderDataExportListener
	 *
	 * Create Time:2015年10月28日 上午9:55:20
	 *  
	 * @param date
	 */
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

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if(event.getSource() == contentPane.getMainTable().getSelectionModel()
				&& contentPane.getMainTable().getRowSelectionAllowed()){
			int row = contentPane.getMainTable().getSelectedRow();
			if(row != -1){
				String payno = (String) contentPane.getMainTable().getValueAt(row, 2);
				((OrderItemTable)contentPane.getItemTable()).reflushTable(payno);
			}
		}
	}
}
