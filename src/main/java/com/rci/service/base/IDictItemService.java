package com.rci.service.base;

import java.util.List;

import com.rci.bean.entity.base.DictItem;

public interface IDictItemService {
	DictItem getDictItem(String groupCode, String itemCode);

	List<DictItem> getDictItems(String groupCode);
}
