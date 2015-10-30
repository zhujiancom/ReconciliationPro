package com.rci.ui.swing.views.builder;

import com.rci.ui.swing.views.PopWindow;

public class CalendarWinBuilder extends AbstractWinBuilder implements PopWindowBuilder{
	private static class CalendarWinBuilderHolder{
		private static CalendarWinBuilder instance = new CalendarWinBuilder();
	}
	private CalendarWinBuilder(){}
	
	public static CalendarWinBuilder getInstance(){
		return CalendarWinBuilderHolder.instance;
	}
	
	@Override
	protected PopWindow createWindow() {
		return new PopWindow(300, 200, "日期时间选择");
	}

}
