package com.rci.enums;

public final class BusinessEnums {
	private BusinessEnums(){}
	
	/**
	 * 
	 * remark (备注): 数据自动获取标记符
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：MarkType
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年4月28日 下午1:59:55
	 *
	 */
	public static enum MarkType{
		SYSTEM_INIT,ORDER_FETCH
	}
	
	/**
	 * remark (备注):支付方式种类 {50代金券，100代金券，套餐A,套餐B,套餐C,免单,现金}
	 * 
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：SchemeType
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年4月28日 下午1:57:47
	 *
	 *
	 */
	public static enum SchemeType{
		CHIT_50,CHIT_100,SUIT_32,SUIT_68,SUIT_98,FREE,CASH
	}
	
	/**
	 * remark (备注): 活动补贴类型， {饿了么活动补贴， 美团外卖活动补贴}
	 * 
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：FreeType
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年4月28日 下午1:56:18
	 *
	 *
	 */
	public static enum FreeType{
		ELE,MTWM
	}
	
	/**
	 * remark (备注): 流水生成方式，{ 自动，手动 }
	 * 
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：DataGenerateType
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年4月28日 下午1:55:20
	 *
	 */
	public static enum DataGenerateType{
		AUTO,MANUAL
	}
	
	/**
	 * remark (备注): 流水类型, {流入，流出，转账}
	 * 
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：FlowType
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年4月28日 下午1:54:32
	 *
	 *
	 */
	public static enum FlowType{
		IN,OUT,CHANGE
	}
}
