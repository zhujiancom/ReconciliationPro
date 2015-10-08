package com.rci.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.base.IBaseService;

public interface ISchemeService extends IBaseService<Scheme, Long>{
//	public Scheme getScheme(SchemeType type,String paymodeno);
	
//	public List<Scheme> getSchemes(String paymodeno);
	
	public Scheme getScheme(SchemeType type,Vendor vendor);
	
//	public List<Scheme> getSchemes(Vendor vendor,BigDecimal freePrice,Date date);
//	public List<Scheme> getSchemes(Vendor vendor,Date date);
	
	public Scheme getScheme(Vendor vendor,BigDecimal freePrice,Date date);
	
//	public List<Scheme> getActiveSchemes();
	
	public void checkStatus(Date deadLine);
	
//	public List<SchemeVO> getSchemeVOs(Vendor vendor);
	
	List<Scheme> getSchemes(SchemeQueryDTO queryDTO);
	
}
