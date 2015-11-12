package com.rci.service;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.service.base.IBaseService;

public interface ISchemeTypeService extends IBaseService<SchemeType, Long>{
	SchemeType getSchemeTypeByDishno(String dishno);
	
	SchemeType getSchemeType(BigDecimal amount,ActivityType activity);
	
	SchemeType getSchemeTypeByNo(String typeno);
	
	List<SchemeType> getSchemeTypes(ActivityStatus status);
}