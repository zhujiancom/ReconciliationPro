package com.rci.service;

import java.util.Date;
import java.util.List;

import com.rci.bean.entity.OrderAccountRef;

public interface IOrderAccountRefService {
	List<OrderAccountRef> getOARef(String billno);
	
	void rwInsertOar(OrderAccountRef oar);
	
	void rwDeleteOar(Date date);
}
