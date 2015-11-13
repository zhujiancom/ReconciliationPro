package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.DishSeries;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class DishSeriesRepository extends DefaultHibernateDAOFacadeImpl<DishSeries, Long>{

}
