package com.rci.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rci.bean.dto.SchemeDTO;
import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.base.IBaseService;

public interface ISchemeService extends IBaseService<Scheme, Long>{
	public Scheme getScheme(String typeno,Vendor vendor,Date date);
	
	public Scheme getScheme(Vendor vendor,BigDecimal freePrice,Date date);
	
	public void checkStatus(Date deadLine);
	
	List<Scheme> getSchemes(SchemeQueryDTO queryDTO);
	
	List<SchemeDTO> getAllSchemes();
	
}
