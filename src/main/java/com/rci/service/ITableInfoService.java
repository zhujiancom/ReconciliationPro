package com.rci.service;

import com.rci.bean.entity.TableInfo;
import com.rci.service.base.IBaseService;

public interface ITableInfoService extends IBaseService<TableInfo,Long>{
	TableInfo getTableInfoByNo(String tableno);
	
	void deleteAll();
	
	boolean isExpressTable(String tableno);
}
