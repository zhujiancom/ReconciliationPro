/**
 * 
 */
package com.rci.service.base;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rci.bean.entity.base.BaseEntity;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

/**
 * @Description
 * @author zj
 * @Date 2014年7月25日
 *	
 */
public class BaseServiceImpl<T extends BaseEntity, PK extends Serializable> implements IBaseService<T,PK>{
	private transient Log logger = LogFactory.getLog(BaseServiceImpl.class);
	@Autowired
	protected DefaultHibernateDAOFacadeImpl<T, PK> baseDAO;

	protected Log logger(){
		if(logger == null){
			return LogFactory.getLog(BaseServiceImpl.class);
		}else{
			return logger;
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwCreate(T entity){
		baseDAO.save(entity);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwCreate(T[] entities){
		baseDAO.save(entities);
	}

	/**
	 * 
	 * Describle(描述)： 更新
	 *
	 * 方法名称：rwUpdate
	 *
	 * 所在类名：BaseService
	 *
	 * Create Time:2015年4月23日 下午4:55:55
	 *  
	 * @param entity
	 */
	@Override
	public void rwUpdate(T entity){
		baseDAO.update(entity);
	}
	@Override
	public void rwUpdate(T[] entity){
		baseDAO.update(entity);
	}

	/**
	 * 
	 * Describle(描述)：删除
	 *
	 * 方法名称：rwDelete
	 *
	 * 所在类名：BaseService
	 *
	 * Create Time:2015年4月23日 下午4:55:43
	 *  
	 * @param pk
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwDelete(PK pk){
		baseDAO.delete(pk);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwDelete(T[] entities){
		baseDAO.delete(entities);
	}
	
	/**
	 * 
	 * Describle(描述)： get
	 *
	 * 方法名称：get
	 *
	 * 所在类名：BaseService
	 *
	 * Create Time:2015年4月23日 下午4:56:18
	 *  
	 * @param pk
	 * @return
	 */
	@Override
	public T get(PK pk){
		return baseDAO.get(pk);
	}
	@Override
	public List<T> getAll(){
		return baseDAO.getAll();
	}
}
