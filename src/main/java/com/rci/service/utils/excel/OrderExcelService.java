package com.rci.service.utils.excel;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rci.exceptions.ServiceException;
import com.rci.metadata.dto.OrderDTO;

@Service("OrderExcelService")
public class OrderExcelService extends AbstractExcelOperationService<OrderDTO> {
	@Override
	public <T> Collection<T> getDataSet() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
