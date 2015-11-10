package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.SchemeManagementWin;

public class SchemeManagementWinBuilder extends AbstractWinBuilder {

	private SchemeManagementWinBuilder(){}
	
	private static class SchemeManagementWinBuilderHolder{
		private static SchemeManagementWinBuilder instance = new SchemeManagementWinBuilder();
	}
	
	public static SchemeManagementWinBuilder getInstance(){
		return SchemeManagementWinBuilderHolder.instance;
	}
	
	@Override
	protected PopWindow createWindow() {
		return new SchemeManagementWin(900,600,"在线平台活动设置");
	}

}
