package com.rci.service.base.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.base.DictGroup;
import com.rci.service.base.BaseService;
import com.rci.service.base.IDictGroupService;

@Service("DictGroupService")
public class DictGroupServiceImpl extends BaseService<DictGroup, Long>
		implements IDictGroupService {

	@Override
	public void rwCreateDictGroup(DictGroup group) {
		super.rwCreate(group);
	}

	@Override
	public void rwCreateDictGroup(DictGroup[] groups) {
		super.rwCreate(groups);
	}

}
