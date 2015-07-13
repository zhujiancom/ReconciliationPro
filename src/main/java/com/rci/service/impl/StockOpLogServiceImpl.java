package com.rci.service.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.StockOpLog;
import com.rci.service.IStockOpLogService;
import com.rci.service.base.BaseServiceImpl;

@Service("StockOpLogService")
public class StockOpLogServiceImpl extends BaseServiceImpl<StockOpLog, Long>
		implements IStockOpLogService {
}
