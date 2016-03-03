package com.rci.enums;

import java.util.EnumSet;


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
		SYSTEM_INIT,ORDER_FETCH,STOCK_INIT
	}
	
	/**
	 * remark (备注):支付方式种类 {50代金券，100代金券，套餐A,套餐B,套餐B-1,套餐C,套餐D,套餐E,免单,现金}
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
//	public static enum SchemeType{
//		CHIT_50,CHIT_100,SUIT_32,SUIT_68,SUIT_98,SUIT_128,SUIT_138,SUIT_198,SUIT_200,SUIT_50
//	}
	
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
	
	public static enum ActivityStatus{
		ACTIVE,INACTIVE
	}
	
	public static enum State{
		VALID,INVALID,OVERDUE
	}
	
	/**
	 * 
	 * remark (备注): 活动类型 （代金券，外卖活动）
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：ActivityType
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年11月11日 下午3:58:29
	 *
	 */
	public static enum ActivityType{
		VOUCHER,TAKEOUT
	}
	
	public static enum Vendor{
		ELE,DZDP,MTWM,MT,LS,TDD,WMCR,SH,MTSUPER,BDNM,PLQ,DDF,ALIPAY,BDWM,UNIONPAY;
	}
	
	public static enum StockOpType{
		/**
		 * 总量增加,进货
		 */
		GROSS_INCREASEMENT,
		/**
		 * 总量减少
		 */
		GROSS_DECREASEMENT,
		/**
		 * 余量增加
		 */
		BALANCE_INCREASEMENT,
		/**
		 * 余量减少
		 */
		BALANCE_DECREASEMENT,
		/**
		 * 正常消费
		 */
		CONSUME
	}
	
//	public static enum AccountCode{
//		MT,MTWM,MT_SUPER,FREE_MT_SUPER,DPTG,DPSH,
//		ELE,ALIPAY,FREE,FREE_ONLINE,FREE_MTWM,FREE_ELE,
//		CASH_MACHINE,POS,WMCR,FREE_WMCR,BDNM,PLQ,FREE_PLQ,TDD;
//	}
	
	public static enum AccountCode{
		CASH_MACHINE,						//收银机账户
		ELE,								//饿了么主账户
		ONLINE_ELE,
		ALLOWANCE_ELE,
		FREE_ELE,
		MT,
		ONLINE_MT,
		FREE_MT,
		MT_SUPER,
		FREE_MT_SUPER,
		DZDP,
		ONLINE_DPTG,
		FREE_DPTG,
		DPSH,
		FREE_DPSH,
		MTWM,
		ONLINE_MTWM,
		ALLOWANCE_MTWM,
		FREE_MTWM,
		ALIPAY,
		ONLINE_ALIPAY,
		FREE_ALIPAY,
		FREE,
		FREE_ONLINE,
		FREE_OFFLINE,
		POS,
		ONLINE_POS,
		FREE_POS,
		WMCR,
		FREE_WMCR,
		BDNM,
		ONLINE_BDNM,
		FREE_BDNM,
		DDF_BDNM,
		FREE_DDF_BDNM,
		PLQ,
		ONLINE_PLQ,
		ALLOWANCE_PLQ,
		FREE_PLQ,
		TDD,
		ONLINE_TDD,
		FREE_TDD,
		WALLET,
	}
	
	public static enum PaymodeCode{
		CASH_MACHINE("00"),					//收银机现金支付
		ELE("11"),							//饿了么在线支付
		MTWM("14"),							//美团外卖在线支付
		WMCR("12"),	
		TDD("96"),							//淘点点支付
		DPTG("98"),							//大众点评团购券支付
		DPSH("13"),							//大众点评闪惠支付
		MT("99"),							//美团团购支付
		FREE("YY"),OFFLINE_FREE("OFFLINE_FREE"),ONLINE_FREE("ONLINE_FREE"),	//线下现金免单,线上免单
		MTSUPER("87"),						//美团超券支付
		POS("03"),							//pos机刷卡支付
		ALIPAY("86"),						//支付宝支付
		BDNM("04"),							//百度糯米团购支付
		DDF("05"),							//百度糯米到店付支付
		PLQ("08"),							//派乐趣支付
		BDWM("20"),							//百度外卖支付
		VOUCHER("88"),						//商家发放的代金券
		UNKNOW("");							//未知支付方式
		
		private String paymodeno;
		private PaymodeCode(String paymodeno){
			this.paymodeno = paymodeno;
		}
		
		public String getPaymodeno(){
			return paymodeno;
		}
		
		public static PaymodeCode paymodeCode(String payno){
			EnumSet<PaymodeCode> enumSet = EnumSet.allOf(PaymodeCode.class);
			for(PaymodeCode code:enumSet){
				if(code.getPaymodeno().equals(payno)){
					return code;
				}
			}
			return UNKNOW;
		}
	}

	/**
	 * 
	 * remark (备注): 订单平台 - 饿了么，美团外卖，堂食，淘点点，外卖超人，到家,拉手网
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：OrderFramework
	 *
	 * 包名称：com.rci.enums
	 *
	 * Create Time: 2015年9月24日 上午10:04:37
	 *
	 */
	public static enum OrderFramework{
		ELE,MTWM,TS,TDD,WMCR,LS,PLQ,ALIPAY,BDWM,BDNM
	}
}
