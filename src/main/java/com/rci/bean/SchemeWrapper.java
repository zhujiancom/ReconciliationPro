package com.rci.bean;

import java.math.BigDecimal;

import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.base.DictItem;
import com.rci.service.base.IDictItemService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;

public class SchemeWrapper {
private Scheme scheme;
	
	private Integer count;
	
	private BigDecimal postAmount;
	
	public SchemeWrapper(){}
	
	public SchemeWrapper(Scheme s){
		this.scheme = s;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getName() {
		StringBuffer name = new StringBuffer();
		if(StringUtils.hasText(scheme.getUnitCode())){
			//如果有数量单位，获取数量单位名称
			IDictItemService dictService = (IDictItemService) SpringUtils.getBean("DictionaryService");
			DictItem item = dictService.getDictItem("UNIT", scheme.getUnitCode());
			String unitName = item.getItemCname();
			name.append(scheme.getName()).append("【").append(+this.count).append("】").append(unitName);
		}else{
			name.append(scheme.getName()).append("-").append(postAmount);
		}
		return name.toString();
	}

	public BigDecimal getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}

	public void increasement(){
		this.count++;
	}
}
