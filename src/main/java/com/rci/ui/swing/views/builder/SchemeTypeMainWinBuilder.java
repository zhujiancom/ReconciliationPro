package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.SchemeTypeMainWin;

/**
 * 
 * remark (备注): 活动类型设置窗口
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SchemeTypeMainWinBuilder
 *
 * 包名称：com.rci.ui.swing.views.builder
 *
 * Create Time: 2015年11月12日 下午2:36:07
 *
 */
public class SchemeTypeMainWinBuilder extends AbstractWinBuilder {
	private SchemeTypeMainWinBuilder(){}
	
	private static class SchemeTypeMainWinBuilderHolder{
		private static SchemeTypeMainWinBuilder instance = new SchemeTypeMainWinBuilder();
	}
	
	public static SchemeTypeMainWinBuilder getInstance(){
		return SchemeTypeMainWinBuilderHolder.instance;
	}

	@Override
	protected PopWindow createWindow() {
		return new SchemeTypeMainWin(900,600,"活动类型设置");
	}

}
