package com.rci.service.inventory.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.inventory.PurchaseRecord;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IPurchaseRecordService;

@Service("PurchaseRecordService")
public class PurchaseRecordServiceImpl extends
		BaseServiceImpl<PurchaseRecord, Long> implements IPurchaseRecordService{

}
