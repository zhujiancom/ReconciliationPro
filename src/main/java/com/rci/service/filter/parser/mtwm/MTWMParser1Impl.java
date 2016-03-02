package com.rci.service.filter.parser.mtwm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ISchemeService;
import com.rci.service.filter.FilterChain;
import com.rci.service.filter.parser.AbstractPlatformParser;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;

/**
 * 
 * remark (备注): 2016-01-18号之前使用该算法
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：MTWMParser1Impl
 *
 * 包名称：com.rci.service.filter.parser
 *
 * Create Time: 2016年1月22日 上午10:12:30
 *
 */
@Service("MtwmParser1")
public class MTWMParser1Impl extends AbstractPlatformParser {
	private static final Log logger = LogFactory.getLog(MTWMParser1Impl.class);
	
	@Resource(name="SchemeService")
	protected ISchemeService schemeService;
//	@Resource(name="filterChain")
	private FilterChain filterChain;
	
	@Override
	public void doParse(Order order) {
		BigDecimal onlineAmount = order.getPaymodeMapping().get(PaymodeCode.MTWM);
		BigDecimal freeAmount = order.getPaymodeMapping().get(PaymodeCode.FREE);
		BigDecimal originAmount = order.getOriginPrice();
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",美团外卖在线支付"+onlineAmount+"元";
		}else{
			schemeName = "美团外卖在线支付"+onlineAmount+"元";
		}
		if(freeAmount != null){
			Map<String,BigDecimal> freeMap = filterChain.getFreeOnlineMap();
			String day = order.getDay();
			try {
				Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
				/* 查找美团外卖符合条件的活动 */
				SchemeQueryDTO queryDTO = new SchemeQueryDTO();
				queryDTO.setStatus(ActivityStatus.ACTIVE);
				queryDTO.setEndDate(orderDate);
				queryDTO.setStartDate(orderDate);
				queryDTO.setVendor(Vendor.MTWM);
				List<Scheme> schemes = schemeService.getSchemes(queryDTO);
				if(CollectionUtils.isEmpty(schemes)){
					logger.warn(order.getPayNo()+"---[美团外卖 ] 没有找到匹配的Scheme -----");
					String warningInfo = "[美团外卖活动 ] 没有找到匹配的Scheme";
					order.setWarningInfo(warningInfo);
				}else{
					Scheme s = isNewCustomer(freeAmount, schemes);
					if(s == null){
						for(Scheme scheme:schemes){
							if(originAmount.compareTo(scheme.getFloorAmount())>=0 && originAmount.compareTo(scheme.getCeilAmount()) < 0){
								//满减活动
								BigDecimal price = scheme.getPrice();
								BigDecimal redundant = freeAmount.subtract(price); //红包支付金额
								BigDecimal allowanceAmount = redundant.add(scheme.getPostPrice());
								if(freeMap.get(order.getPayNo()) == null){
									freeMap.put(order.getPayNo(), allowanceAmount);
								}
								schemeName = schemeName+","+scheme.getName();
								//保存美团外卖补贴金额
								preserveOAR(allowanceAmount,BusinessConstant.AccountCode_FREE_MTWM,order);
								//在线优惠金额
								preserveOAR(scheme.getSpread(),BusinessConstant.AccountCode_FREE_ONLINE,order);
								break;
							}else{
								logger.info(order.getPayNo()+"--- 【美团外卖活动】："+scheme.getName()+" 不匹配！");
							}
						}
					}else{
						BigDecimal allowanceAmount = s.getPostPrice();
						if(freeMap.get(order.getPayNo()) == null){
							freeMap.put(order.getPayNo(), allowanceAmount);
						}
						schemeName = schemeName+","+s.getName();
						//保存美团外卖补贴金额
						preserveOAR(allowanceAmount,BusinessConstant.AccountCode_FREE_MTWM,order);
						preserveOAR(s.getSpread(),BusinessConstant.AccountCode_FREE_ONLINE,order);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		order.setSchemeName(schemeName);
		//保存美团外卖在线支付金额
		preserveOAR(onlineAmount,BusinessConstant.AccountCode_FREE_MTWM,order);
	}
	
	/**
	 * 
	 * Describle(描述)：判断是否新用户
	 *
	 * 方法名称：isNewCustomer
	 *
	 * 所在类名：MTWMFilter
	 *
	 * Create Time:2016年1月6日 上午9:39:54
	 *  
	 * @param freeAmount
	 * @param schemes
	 * @return
	 */
	private Scheme isNewCustomer(BigDecimal freeAmount,List<Scheme> schemes){
		Scheme result = null;
		for(Scheme scheme:schemes){
			if(freeAmount.compareTo(scheme.getFloorAmount()) == 0 && freeAmount.compareTo(scheme.getCeilAmount()) == 0){
				result = scheme;
				break;
			}
		}
		return result;
	}
}
