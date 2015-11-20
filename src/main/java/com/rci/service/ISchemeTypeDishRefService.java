package com.rci.service;

import com.rci.bean.entity.SchemeTypeDishRef;
import com.rci.service.base.IBaseService;

public interface ISchemeTypeDishRefService extends IBaseService<SchemeTypeDishRef, Long> {
	void deleteRefByTypeno(String no);
}
