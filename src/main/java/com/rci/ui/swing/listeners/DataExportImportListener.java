package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

import com.rci.ui.swing.handler.DataExport;

public abstract class DataExportImportListener implements ActionListener{
	public static final int EXPORT = 0;
	public static final int IMPORT = 1;
	
	protected int action;
	protected JFileChooser fileChooser;
	protected JProgressBar progressBar;
	
	public DataExportImportListener(int action){
		this.action = action;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(action == EXPORT){
			exportData();
		}else if(action == IMPORT){
			importData();
		}
	}
	
	public void exportData(){
		String name = getFileName();//DateUtil.date2Str(new Date(), "yyyyMMdd") + ".xls";
		// 构造文件保存对话框
		final JFileChooser chooser = new JFileChooser();
		fileChooser = chooser;
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(getDialogTitle());
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
			final DataExport target = getDataExport();
			final JProgressBar bar = progressBar;
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
	
	
	
	protected abstract String getFileName();
	protected abstract String getDialogTitle();
	protected abstract DataExport getDataExport();
	
	protected abstract void importData();
}
