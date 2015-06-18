package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.TableInfo;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class TableInfoRepository extends DefaultHibernateDAOFacadeImpl<TableInfo, Long> {

}
