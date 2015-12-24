package com.rci.service.inventory.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.inventory.SellOffWarning;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.enums.BusinessEnums.State;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.ISellOffWarningService;

@Service("SellOffWarningService")
public class SellOffWarningServiceImpl extends BaseServiceImpl<SellOffWarning, Long> implements ISellOffWarningService{

	@Override
	public SellOffWarning queryValidWarningInfo(String ino) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(SellOffWarning.class);
		sdc.add(SafeRestrictions.eq("ino", ino)).add(SafeRestrictions.eq("state", State.VALID));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

	@Override
	public List<SellOffWarning> queryAllValidWarningInfo() {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(SellOffWarning.class);
		sdc.add(SafeRestrictions.eq("state", State.VALID));
		return baseDAO.queryListByCriteria(sdc);
	}

}
