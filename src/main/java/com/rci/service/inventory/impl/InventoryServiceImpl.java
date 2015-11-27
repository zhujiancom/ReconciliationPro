package com.rci.service.inventory.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopContext;
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
		sdc.add(SafeRestrictions.likeAnyWhere("iname", iname));
		try{
			return baseDAO.queryListByCriteria(sdc);
		}catch(Exception e){
			logger.error("查询库存品种["+iname+"]出错！",e);
		}
		return null;
	}
	
	/**
	 * 
	 * Describle(描述)：
	 *
	 * 方法名称：consume
	 *
	 * 所在类名：InventoryServiceImpl
	 *
	 * Create Time:2015年11月27日 上午9:38:05
	 *  
	 * @param ino	库存产品编号
	 * @param consumeAmount	销售数量
	 */
	public Inventory consume(String ino,BigDecimal consumeAmount){
		Inventory inventory = queryInventoryByNo(ino);
		inventory.setBalanceAmount(inventory.getBalanceAmount().subtract(consumeAmount));
		inventory.setConsumeAmount(inventory.getConsumeAmount().add(consumeAmount));
		((IInventoryService)AopContext.currentProxy()).rwUpdate(inventory);
		return inventory;
	}
	
	public void rollback(String ino,BigDecimal consumeAmount){
		try{
			Inventory inventory = queryInventoryByNo(ino);
			inventory.setBalanceAmount(inventory.getBalanceAmount().add(consumeAmount));
			inventory.setConsumeAmount(inventory.getConsumeAmount().subtract(consumeAmount));
			((IInventoryService)AopContext.currentProxy()).rwUpdate(inventory);
		}catch(Exception ex){
			System.out.println(" ino = "+ino);
		}
	}
}
