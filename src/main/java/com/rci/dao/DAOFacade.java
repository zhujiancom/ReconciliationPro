/**
 * 
 */
package com.rci.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @Description
 * @author zj
 * @Date 2014�?0�?7�?
 *	
 */
public interface DAOFacade<T,PK extends Serializable>{
	public void save(T t);
	public void save(T[] entities);

	public void delete(PK pk);
	public void delete(T t);
	public void delete(T[] entities);

	public void update(T t);
	public void update(T[] entities);
	public void update(String sql,Object[] params);

	public T get(PK pk);
	public List<T> getAll();

	public List<Map<String,Object>> queryListBySQL(String sql);
	
	public <E> List<E> queryListByCriteria(DetachedCriteria dc);
	
	public T queryUniqueByHQL(String hql);
	
	public <E> E queryUniqueByCriteria(DetachedCriteria dc);
	
	public int executeHQL(String hql);
	
	/**
	 * 
	 *
	 * Describle(描述)：获取记录条数
	 *
	 * 方法名称：getRowCount
	 *
	 * 所在类名：DAOFacade
	 *
	 * Create Time:2015年6月19日 下午11:58:30
	 *  
	 * @param dc
	 * @return
	 */
	public Long getRowCount(DetachedCriteria dc);

}
