package com.rci.service.inventory.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.inventory.Inventory;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IInventoryService;

@Service("InventoryService")
public class InventoryServiceImpl extends BaseServiceImpl<Inventory, Long>
		implements IInventoryService {
}
