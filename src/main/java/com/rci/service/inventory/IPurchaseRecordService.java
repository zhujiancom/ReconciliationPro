package com.rci.service.inventory;

import java.util.List;

import com.rci.bean.dto.PurchaseRecordQueryDTO;
import com.rci.bean.entity.inventory.PurchaseRecord;
import com.rci.service.base.IBaseService;

public interface IPurchaseRecordService extends
		IBaseService<PurchaseRecord, Long> {
	List<PurchaseRecord> fuzzyQuery(PurchaseRecordQueryDTO queryDTO);
}
