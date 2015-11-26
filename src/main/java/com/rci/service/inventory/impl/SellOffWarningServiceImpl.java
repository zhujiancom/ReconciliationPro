package com.rci.service.inventory.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.inventory.SellOffWarning;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.ISellOffWarningService;

@Service("SellOffWarningService")
public class SellOffWarningServiceImpl extends BaseServiceImpl<SellOffWarning, Long> implements ISellOffWarningService{

}
