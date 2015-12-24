package com.rci.service.inventory;

import java.util.List;

import com.rci.bean.entity.inventory.SellOffWarning;
import com.rci.service.base.IBaseService;

public interface ISellOffWarningService extends
		IBaseService<SellOffWarning, Long> {
	SellOffWarning queryValidWarningInfo(String ino);
	
	List<SellOffWarning> queryAllValidWarningInfo();
}
