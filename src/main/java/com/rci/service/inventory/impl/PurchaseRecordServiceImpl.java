package com.rci.service.inventory.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rci.bean.dto.PurchaseRecordQueryDTO;
import com.rci.bean.entity.inventory.PurchaseRecord;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IPurchaseRecordService;

@Service("PurchaseRecordService")
public class PurchaseRecordServiceImpl extends
		BaseServiceImpl<PurchaseRecord, Long> implements IPurchaseRecordService{

	@Override
	public List<PurchaseRecord> fuzzyQuery(PurchaseRecordQueryDTO queryDTO) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(PurchaseRecord.class);
		sdc.add(SafeRestrictions.likeAnyWhere("iname", queryDTO.getKeyword()))
			.add(SafeRestrictions.eq("purDate", queryDTO.getPurDate()));
		try{
			return baseDAO.queryListByCriteria(sdc);
		}catch(Exception e){
			logger.error("查询进货记录出错！",e);
		}
		return null;
	}

}
