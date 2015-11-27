package com.rci.service.inventory;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.inventory.Inventory;
import com.rci.service.base.IBaseService;

public interface IInventoryService extends IBaseService<Inventory, Long> {
	List<Inventory> queryValidInventories();
	
	Inventory queryInventoryByNo(String ino);
	
	List<Inventory> queryInventoryByName(String iname);
	
	/**
	 * 
	 * Describle(描述)： 销售
	 *
	 * 方法名称：consume
	 *
	 * 所在类名：IInventoryService
	 *
	 * Create Time:2015年11月27日 上午11:31:46
	 *  
	 * @param ino
	 * @param consumeAmount
	 */
	Inventory consume(String ino,BigDecimal consumeAmount);
	
	void rollback(String ino,BigDecimal consumeAmount);
}
