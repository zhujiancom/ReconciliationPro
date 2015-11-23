package com.rci.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.SchemeTypeDishRef;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.service.ISchemeTypeDishRefService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.ui.swing.vos.DishVO;

@Service("SchemeTypeDishRefService")
public class SchemeTypeDishRefServiceImpl extends
		BaseServiceImpl<SchemeTypeDishRef, Long> implements
		ISchemeTypeDishRefService {
	
	@Override
	public void rwDeleteRefByTypeno(String no){
		String hql = "delete from SchemeTypeDishRef stdr where stdr.typeno='"+no+"'";
		baseDAO.executeHQL(hql);
	}

	/* 
	 * @see com.rci.service.ISchemeTypeDishRefService#queryDishesByTypeno(java.lang.String)
	 */
	@Override
	public List<DishVO> queryDishesByTypeno(String typeno) {
		List<DishVO> result = new ArrayList<DishVO>();
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(SchemeTypeDishRef.class);
		sdc.add(SafeRestrictions.eq("typeno", typeno));
		List<SchemeTypeDishRef> refs = baseDAO.queryListByCriteria(sdc);
		if(!CollectionUtils.isEmpty(refs)){
			for(SchemeTypeDishRef ref:refs){
				DishVO vo = new DishVO(ref.getDishno(),ref.getDishname());
				result.add(vo);
			}
		}
		return result;
	}
}
