package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.rci.tools.DateUtil;

public class DataIOHandler implements ActionListener {
	public static final int EXPORT = 0;
	public static final int IMPORT = 1;
	
	private int action;
	
	public DataIOHandler(int action){
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
		String name = DateUtil.date2Str(new Date(), "yyyyMMdd") + ".xlsx";
		// 构造文件保存对话框
		JFileChooser chooser = new JFileChooser();
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
				return "Excel文件(*.xlsx)";
			}

		});
		int result = chooser.showSaveDialog(null);
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			String filePath = chooser.getCurrentDirectory()+ File.separator+ chooser.getSelectedFile().getName();
			System.out.println("改变路径之后，文件的保存路径=" + filePath);
			System.out.println(chooser.getSelectedFile().getAbsolutePath() + "-->这是绝对路径");// 绝对路径
			
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
		JFileChooser chooser = new JFileChooser();
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
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
