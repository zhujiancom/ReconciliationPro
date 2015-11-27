package com.rci.service.inventory;

import java.util.List;

import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.bean.entity.inventory.InventorySellLog;
import com.rci.service.base.IBaseService;
import com.rci.ui.swing.vos.SaleLogDetailVO;
import com.rci.ui.swing.vos.SaleLogVO;

public interface IInventorySellLogService extends
		IBaseService<InventorySellLog, Long> {
	List<SaleLogVO> querySumSaleLog(SaleLogQueryDTO queryDTO);
	
	List<SaleLogDetailVO> querySaleLogDetail(SaleLogQueryDTO queryDTO);
	
	void rollbackLog(SaleLogQueryDTO queryDTO);
	
	void deleteLogByDay(String day);
}
