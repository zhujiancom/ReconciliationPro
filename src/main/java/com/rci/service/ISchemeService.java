package com.rci.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.base.IBaseService;
import com.rci.ui.swing.vos.SchemeVO;

public interface ISchemeService extends IBaseService<Scheme, Long>{
	public Scheme getScheme(SchemeType type,String paymodeno);
	
	public List<Scheme> getSchemes(String paymodeno);
	
	public List<Scheme> getSchemes(Vendor vendor,BigDecimal freePrice,Date date);
	public List<Scheme> getSchemes(Vendor vendor,Date date);
	
	public Scheme getScheme(Vendor vendor,BigDecimal freePrice,Date date);
	
	public List<SchemeVO> getSchemeVOs(Vendor vendor);
	
}
