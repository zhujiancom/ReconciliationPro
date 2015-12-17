package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeDTO;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.ISchemeService;
import com.rci.service.utils.IExImportService;
import com.rci.service.utils.excel.ExcelSheet;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.handler.DataExport;
import com.rci.ui.swing.views.component.encapsulation.MaskDialog;

public class SchemeDataExportImportListener extends DataExportImportListener {
	private JFrame frame;
		
	public SchemeDataExportImportListener(JFrame frame) {
		this.frame = frame;
	}

	@Override
	protected String getFileName() {
		return "在线平台活动列表-"+DateUtil.date2Str(new Date(), "yyyyMMdd") + ".xls";
	}

	@Override
	protected String getDialogTitle() {
		return "活动列表导出";
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
		try{
			IExImportService service = (IExImportService) SpringUtils.getBean("SchemeExcelService");
			DataExport export = new DataExport(fileChooser,service);
			ISchemeService schemeService = (ISchemeService) SpringUtils.getBean("SchemeService");
			List<SchemeDTO> schemes = schemeService.getAllSchemes();
			List<ExcelSheet> sheets = new ArrayList<ExcelSheet>();
			ExcelSheet sheet1 = new ExcelSheet(SchemeDTO.class,"在线活动列表");
			if(CollectionUtils.isEmpty(schemes)){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "没有活动数据，导出活动数据失败");
			}
			sheet1.setDataset(schemes);
			sheets.add(sheet1);
			export.setSheets(sheets);
			new Thread(export).start();
		}catch(Exception e){
			dialog.error(e.getMessage());
			ExceptionManage.throwServiceException("没有活动数据，导出活动数据失败",e);
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
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final MaskDialog dialog = new MaskDialog(frame);
				new Thread(new Runnable(){

					@Override
					public void run() {
						// 构造文件保存对话框
						final JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						chooser.setDialogType(JFileChooser.OPEN_DIALOG);
						chooser.setMultiSelectionEnabled(false);
						chooser.setAcceptAllFileFilterUsed(false);
						chooser.setDialogTitle("在线活动数据导入");
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
									SwingUtilities.invokeLater(new Runnable(){

										@Override
										public void run() {
											dialog.loading("数据正在导入,请稍后...");
										}
									});
									try {
										BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
										IExImportService excelService = (IExImportService) SpringUtils.getBean("SchemeExcelService");
										excelService.importFrom(bin);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									} catch(ServiceException se){
										JOptionPane.showMessageDialog(null, se.getMessage());
									} catch(Exception e){
										e.printStackTrace();
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
									SwingUtilities.invokeLater(new Runnable() {
										
										@Override
										public void run() {
											dialog.done("数据导入完成!");
										}
									});
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
				}).start();
			}
		};		
	}

}
