package com.rci.service.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.SchemeTypeDishRef;
import com.rci.service.ISchemeTypeDishRefService;
import com.rci.service.base.BaseServiceImpl;

@Service("SchemeTypeDishRefService")
public class SchemeTypeDishRefServiceImpl extends
		BaseServiceImpl<SchemeTypeDishRef, Long> implements
		ISchemeTypeDishRefService {
	
	@Override
	public void deleteRefByTypeno(String no){
		String hql = "delete SchemeTypeDishRef stdr where stdr.typeno=?";
		baseDAO.executeHQL(hql);
	}
}
