package com.rci.metadata.service;

import java.util.Date;
import java.util.List;

import com.rci.metadata.dto.DishDTO;
import com.rci.metadata.dto.DishSeriesDTO;
import com.rci.metadata.dto.DishTypeDTO;
import com.rci.metadata.dto.HangupTableDTO;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.metadata.dto.PaymodeDTO;
import com.rci.metadata.dto.TableDTO;

public interface IDataFetchService {
	/**
	 * 
	 * Describle(描述)： 查找所有菜品
	 *
	 * 方法名称：fetchAllDish
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年11月13日 下午4:26:18
	 *  
	 * @return
	 */
	List<DishDTO> fetchAllDish();
	/**
	 * 
	 * Describle(描述)： 根据菜品编号查找菜品信息
	 *
	 * 方法名称：fetchDishByNo
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年5月11日 上午9:35:26
	 *  
	 * @param dishno
	 * @return
	 */
	DishDTO fetchDishByNo(String dishno);
	
	/**
	 * 
	 *
	 * Describle(描述)：获取指定类型的菜品
	 *
	 * 方法名称：fetchAllDishes
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年4月22日 上午10:28:14
	 *  
	 * @return
	 */
	List<DishDTO> fetchAllDishesByType(String typeno);
	
	/**
	 * 
	 * Describle(描述)： 根据菜品类型编号查找菜品类型信息
	 *
	 * 方法名称：fetchDishTypeByNo
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年5月11日 上午9:36:18
	 *  
	 * @param typeno
	 * @return
	 */
	DishTypeDTO fetchDishTypeByNo(String typeno);
	
	List<DishTypeDTO> fetchDishTypeBySeriesNo(String seriesno);
	/**
	 * 
	 * Describle(描述)：获取菜品的大类
	 *
	 * 方法名称：fetchAllDishSeries
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年11月13日 下午4:13:41
	 *  
	 * @return
	 */
	List<DishSeriesDTO> fetchAllDishSeries();
	/**
	 * 
	 * Describle(描述)：根据菜品大类编号查找名称
	 *
	 * 方法名称：fetchDishSeriesByno
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年12月31日 上午11:02:11
	 *  
	 * @param sno
	 * @return
	 */
	DishSeriesDTO fetchDishSeriesByno(String sno);
	
	DishSeriesDTO fetchDishSeriesByNo(String seriesno);
	
	/**
	 * 
	 *
	 * Describle(描述)： 获取所有的菜品类型
	 *
	 * 方法名称：fetchAllDishTypes
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年4月22日 上午10:28:47
	 *  
	 * @return
	 */
	List<DishTypeDTO> fetchAllDishTypes();
	
	/**
	 * Describle(描述)：获取所有的支付方式
	 *
	 * 方法名称：fetchPaymodes
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年4月22日 上午10:31:13
	 *  
	 * @return
	 */
	List<PaymodeDTO> fetchPaymodes();
	
	/**
	 * 
	 * Describle(描述)：获取指定时间内的所有订单。
	 * 注： 最大时间范围为1天。
	 *
	 * 方法名称：fetchOrders
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年4月22日 上午10:33:42
	 *  
	 * @param sdate
	 * @param edate
	 * @return
	 */
	List<OrderDTO> fetchOrders(Date sdate,Date edate);
	
	/**
	 * 
	 * Describle(描述)：根据订单号，获取该订单下的所有订单详细信息
	 *
	 * 方法名称：fetchOrderItems
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年4月22日 上午10:35:32
	 *  
	 * @param orderNo
	 * @return
	 */
	List<OrderItemDTO> fetchOrderItems(String orderNo);
	
	/**
	 * 
	 * Describle(描述)：获取一个时间范围内的所有点单详细信息
	 *
	 * 方法名称：fetchOrderItems
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年8月26日 上午9:43:46
	 *  
	 * @param sdate
	 * @param edate
	 * @return
	 */
	List<OrderItemDTO> fetchOrderItems(Date sdate,Date edate);
	
	/**
	 * 
	 * Describle(描述)：获取 桌号信息
	 *
	 * 方法名称：fetchTables
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年6月18日 下午2:27:55
	 *  
	 * @return
	 */
	List<TableDTO> fetchTables();
	
	/**
	 * 
	 * Describle(描述)：获取未结账桌号
	 *
	 * 方法名称：fetchHangupTables
	 *
	 * 所在类名：IDataFetchService
	 *
	 * Create Time:2015年12月11日 下午1:58:42
	 *  
	 * @return
	 */
	List<HangupTableDTO> fetchHangupTables();
}
