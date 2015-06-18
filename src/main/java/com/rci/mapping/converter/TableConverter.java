package com.rci.mapping.converter;

import javax.annotation.Resource;

import org.dozer.DozerConverter;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.TableInfo;
import com.rci.service.ITableInfoService;

@Component("TableConverter")
public class TableConverter extends DozerConverter<String, String> {

	@Resource(name="TableInfoService")
	private ITableInfoService tbService;
	
	public TableConverter() {
		super(String.class, String.class);
	}

	@Override
	public String convertTo(String source, String destination) {
		return null;
	}

	@Override
	public String convertFrom(String source, String destination) {
		TableInfo table = tbService.getTableInfoByNo(source);
		return table.getTbName();
	}

}
