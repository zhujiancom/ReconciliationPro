package com.rci.service;

import com.rci.bean.entity.Paymode;

public interface IPayModeService {
	public void rwCreatePayMode(Paymode[] modes);
	
	public Paymode getPayModeByNo(String pmno);
}
