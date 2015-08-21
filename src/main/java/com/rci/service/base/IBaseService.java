package com.rci.service.base;

import java.io.Serializable;
import java.util.List;

import com.rci.bean.entity.base.BaseEntity;


public interface IBaseService<T extends BaseEntity, PK extends Serializable> {
	/**
	 * 
	 * Describle(描述)：创建/批量创建
	 *
	 * 方法名称：rwCreate
	 *
	 * 所在类名：IBaseService
	 *
	 * Create Time:2015年6月23日 上午9:38:44
	 *  
	 * @param entity
	 */
	public void rwCreate(T entity);
	public void rwCreate(T[] entities);
	
	/**
	 * 
	 * Describle(描述)：更新/批量更新
	 *
	 * 方法名称：rwUpdate
	 *
	 * 所在类名：IBaseService
	 *
	 * Create Time:2015年6月23日 上午9:41:01
	 *  
	 * @param entity
	 */
	public void rwUpdate(T entity);
	public void rwUpdate(T[] entity);
	
	/**
	 * 
	 * Describle(描述)：删除/批量删除
	 *
	 * 方法名称：rwDelete
	 *
	 * 所在类名：IBaseService
	 *
	 * Create Time:2015年6月23日 上午9:41:38
	 *  
	 * @param pk
	 */
	public void rwDelete(PK pk);
	public void rwDelete(T entity);
	public void rwDelete(T[] entities);
	
	/**
	 * 
	 * Describle(描述)：主键Get
	 *
	 * 方法名称：get
	 *
	 * 所在类名：IBaseService
	 *
	 * Create Time:2015年6月23日 上午9:43:13
	 *  
	 * @param pk
	 * @return
	 */
	public T get(PK pk);
	
	/**
	 * 
	 * Describle(描述)： 获取所有
	 *
	 * 方法名称：getAll
	 *
	 * 所在类名：IBaseService
	 *
	 * Create Time:2015年6月23日 上午9:43:46
	 *  
	 * @return
	 */
	public List<T> getAll();
}
