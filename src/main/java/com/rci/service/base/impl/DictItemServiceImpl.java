package com.rci.service.base.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.base.DictItem;
import com.rci.service.base.BaseService;
import com.rci.service.base.IDictItemService;

@Service("DictionaryService")
public class DictItemServiceImpl extends BaseService<DictItem, Long> implements
		IDictItemService {

	@Override
	public DictItem getDictItem(String groupCode, String itemCode) {
		//TODO  add cache
		DetachedCriteria dc = DetachedCriteria.forClass(DictItem.class);
		dc.add(Restrictions.eq("groupCode", groupCode)).add(Restrictions.eq("itemCode", itemCode));
		DictItem item = baseDAO.queryUniqueByCriteria(dc);
		return item;
	}

	@Override
	public List<DictItem> getDictItems(String groupCode) {
		//TODO  add cache
		DetachedCriteria dc = DetachedCriteria.forClass(DictItem.class);
		dc.add(Restrictions.eq("groupCode", groupCode));
		List<DictItem> items = baseDAO.queryListByCriteria(dc);
		return items;
	}

	@Override
	public void rwCreateDictItem(DictItem item) {
		super.rwCreate(item);
	}

	@Override
	public void rwCreateDictItem(DictItem[] items) {
		super.rwCreate(items);
	}

}
