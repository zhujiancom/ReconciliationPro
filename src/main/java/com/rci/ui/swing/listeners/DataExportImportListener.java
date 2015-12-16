package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public abstract class DataExportImportListener{
	protected int action;
	protected JFileChooser fileChooser;
	
	public ActionListener exportData(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						String name = getFileName();
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
							doDataExport();
							break;
						case JFileChooser.CANCEL_OPTION:
							System.out.println("取消");
						case JFileChooser.ERROR_OPTION:
							System.out.println("Error");
						}
					}
				}).start();
			}
		};
	}
	
	
	
	protected abstract String getFileName();
	protected abstract String getDialogTitle();
	
	protected abstract void doDataExport();
	
	public abstract ActionListener importData();
}
