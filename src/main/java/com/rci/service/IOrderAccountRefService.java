package com.rci.service;

import java.util.List;

import com.rci.bean.entity.OrderAccountRef;

public interface IOrderAccountRefService {
	List<OrderAccountRef> getOARef(String billno);
}
