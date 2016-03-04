/**
 * 
 */
package com.rci.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.DataFetchMark;
import com.rci.bean.entity.Order;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
import com.rci.service.utils.IExImportService;
import com.rci.tools.DateUtil;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：ExcelDataLoaderService
 *
 * 包名称：com.rci.service.impl
 *
 * Create Time: 2015年8月26日 下午8:37:51
 *
 */
@Service("OrderExcelDataLoaderService")
public class OrderExcelDataLoaderService extends BaseDataLoaderService {
	@Resource(name="OrderExcelService")
	private IExImportService excelService;
	
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;

	@Override
	public void load(Date date) {
		throw new UnsupportedOperationException("不支持从SqlServer数据库中加载数据...");
	}

	/* 
	 * @see com.rci.service.IDataLoaderService#load(java.io.InputStream)
	 */
	@Override
	public void load(InputStream in,Date date) throws ServiceException{
		String day = DateUtil.date2Str(date, "yyyyMMdd");
		DataFetchMark mark = markService.getMarkRecordByDay(day);
		if(mark == null){
			excelService.importFrom(in);
			markService.markOrder(day);
			@SuppressWarnings("unchecked")
			List<Order> orders = (List<Order>) excelService.getDataSet();
			// 更新账单使用方案和库存数据
			updateRelativeInfo(orders);
			// 生成账单流水
//			generateAccountFlow(date);
		}else{
			ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "数据已被导入，不可重复导入");
		}
	}

}
