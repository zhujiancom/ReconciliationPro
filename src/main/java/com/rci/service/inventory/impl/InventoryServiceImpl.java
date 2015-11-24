package com.rci.service.inventory.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.inventory.Inventory;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IInventoryService;

@Service("InventoryService")
public class InventoryServiceImpl extends BaseServiceImpl<Inventory, Long>
		implements IInventoryService {
	private static final Log logger = LogFactory.getLog(InventoryServiceImpl.class);
	@Override
	public List<Inventory> queryValidInventories() {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Inventory.class);
		sdc.add(SafeRestrictions.eq("status", YOrN.Y));
		return baseDAO.queryListByCriteria(sdc);
	}

	@Override
	public Inventory queryInventoryByNo(String ino) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Inventory.class);
		sdc.add(SafeRestrictions.eq("ino", ino));
		try{
			return baseDAO.queryUniqueByCriteria(sdc);
		}catch(Exception e){
			logger.error("查询库存品种["+ino+"]出错！",e);
		}
		return null;
	}

	@Override
	public List<Inventory> queryInventoryByName(String iname) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Inventory.class);
		sdc.add(SafeRestrictions.like("iname", iname, MatchMode.ANYWHERE));
		try{
			return baseDAO.queryListByCriteria(sdc);
		}catch(Exception e){
			logger.error("查询库存品种["+iname+"]出错！",e);
		}
		return null;
	}
}
