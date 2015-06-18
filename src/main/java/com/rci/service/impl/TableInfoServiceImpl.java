package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.TableInfo;
import com.rci.service.ITableInfoService;
import com.rci.service.base.BaseService;

@Service("TableInfoService")
public class TableInfoServiceImpl extends BaseService<TableInfo, Long>  implements ITableInfoService {
	
	@Override
	public void rwCreateTableInfos(TableInfo[] tables) {
		super.rwCreate(tables);
	}

	@Override
	public void rwUpdateTableInfo(TableInfo table) {
		super.rwUpdate(table);
	}

	@Override
	public TableInfo get(Long id) {
		return super.get(id);
	}

	@Override
	public TableInfo getTableInfoByNo(String tableno) {
		DetachedCriteria dc = DetachedCriteria.forClass(TableInfo.class);
		dc.add(Restrictions.eq("tbNo", tableno));
		return baseDAO.queryUniqueByCriteria(dc);
	}

}
