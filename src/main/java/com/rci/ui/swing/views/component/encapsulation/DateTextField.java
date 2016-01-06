package com.rci.ui.swing.views.component.encapsulation;

import java.text.ParseException;
import java.util.Date;

import com.rci.tools.DateUtil;

public class DateTextField extends BaseTextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1240705056069794921L;
	private static final String DEFAULT_PATTERN = "yyyyMMdd";
	private String pattern;
	
	private Date date;
	
	private String errorMsg;
	
	public DateTextField(){
		this("");
	}
	
	public DateTextField(String placeholder){
		this(placeholder,DEFAULT_PATTERN);
	}
	
	public DateTextField(String placeholder,String pattern){
		super(placeholder);
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	protected void doMouseExitedAction() {
		super.doMouseExitedAction();
		if(getPlaceholder().equals(getText())){
			setDate(DateUtil.getCurrentDate());
			return;
		}else if(!DateUtil.isDateFormat(getText(),pattern)){
			errorMsg = "日期格式不正确,日期格式应为："+pattern;
			super.setToolTipText(errorMsg);
			setDate(DateUtil.getCurrentDate());
		}else{
			super.setToolTipText(null);
			setErrorMsg(null);
			try {
				setDate(DateUtil.parseDate(getText().trim(), pattern));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doMouseEnterAction() {
		if(DateUtil.isDateFormat(getText().trim(),pattern)){
			super.setToolTipText(null);
		}
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
