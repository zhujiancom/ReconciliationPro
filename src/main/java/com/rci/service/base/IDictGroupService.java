package com.rci.service.base;

import com.rci.bean.entity.base.DictGroup;

public interface IDictGroupService {
	void rwCreateDictGroup(DictGroup group);
	void rwCreateDictGroup(DictGroup[] groups);
}
