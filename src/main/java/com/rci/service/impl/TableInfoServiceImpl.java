package com.rci.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.TableInfo;
import com.rci.service.ITableInfoService;
import com.rci.service.base.BaseServiceImpl;

@Service("TableInfoService")
public class TableInfoServiceImpl extends BaseServiceImpl<TableInfo,Long>  implements ITableInfoService {

	@Override
	public TableInfo getTableInfoByNo(String tableno) {
		DetachedCriteria dc = DetachedCriteria.forClass(TableInfo.class);
		dc.add(Restrictions.eq("tbNo", tableno));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void deleteAll() {
		List<TableInfo> tables = super.getAll();
		((ITableInfoService)AopContext.currentProxy()).rwDelete(tables.toArray(new TableInfo[0]));
	}
	
	public boolean isExpressTable(String tableno){
		TableInfo table = getTableInfoByNo(tableno);
		if("03".equals(table.getTbType())){
			return true;
		}
		return false;
	}
}
