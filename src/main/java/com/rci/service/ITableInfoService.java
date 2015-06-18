package com.rci.service;

import com.rci.bean.entity.TableInfo;

public interface ITableInfoService {
	void rwCreateTableInfos(TableInfo[] tables);
	
	void rwUpdateTableInfo(TableInfo table);
	
	TableInfo get(Long id);
	
	TableInfo getTableInfoByNo(String tableno);
	
}
