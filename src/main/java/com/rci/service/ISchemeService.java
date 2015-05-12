package com.rci.service;

import java.util.List;

import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.SchemeType;

public interface ISchemeService {
	public Scheme getScheme(SchemeType type,String paymodeno);
	
	public List<Scheme> getSchemes(String paymodeno);
	
	void rwCreateScheme(Scheme scheme);
	
	void rwCreateScheme(Scheme[] schemes);
}
