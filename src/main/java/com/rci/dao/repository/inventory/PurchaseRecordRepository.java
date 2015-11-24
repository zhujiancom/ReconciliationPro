package com.rci.dao.repository.inventory;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.inventory.PurchaseRecord;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class PurchaseRecordRepository extends
		DefaultHibernateDAOFacadeImpl<PurchaseRecord, Long> {

}
