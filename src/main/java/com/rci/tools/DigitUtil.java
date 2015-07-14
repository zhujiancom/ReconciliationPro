package com.rci.tools;

import java.math.BigDecimal;

import org.hibernate.service.spi.ServiceException;

public class DigitUtil {
	/**
	 * 计算两数相乘
	 * @param b1
	 * @param b2
	 * @param precision
	 * @return
	 */
	public static BigDecimal mutiplyUp(BigDecimal b1,BigDecimal b2,int precision){
		return b1.multiply(b2).setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal mutiplyDown(BigDecimal b1,BigDecimal b2,int precision){
		return b1.multiply(b2).setScale(precision, BigDecimal.ROUND_HALF_DOWN);
	}
	public static BigDecimal mutiplyDown(BigDecimal b1,BigDecimal b2){
		return mutiplyDown(b1,b2,2);
	}
	public static BigDecimal mutiplyUp(BigDecimal b1,BigDecimal b2){
		return mutiplyUp(b1,b2,2);
	}
	
	/**
	 * 计算百分比
	 * @param member
	 * @param divisor
	 * @param precision
	 * @return
	 */
	public static BigDecimal precentDown(BigDecimal member,int precision){
		return member.divide(new BigDecimal(100)).setScale(precision, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public static BigDecimal precentUp(BigDecimal member,int precision){
		return member.divide(new BigDecimal(100)).setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal precentDown(BigDecimal member){
		return precentDown(member,2);
	}
	public static BigDecimal precentUp(BigDecimal member,BigDecimal divisor){
		return precentUp(member,2);
	}
	
	/**
	 * 加法运算
	 * @param src
	 * @param d
	 * @param precision
	 * @return
	 */
	public static BigDecimal add(BigDecimal src,Double d,int precision){
		BigDecimal dd = new BigDecimal(d.toString()); 
//		if(d < 0){
//			dd = dd.multiply(new BigDecimal(-1)); 
//		}
		return src.add(dd).setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal add(BigDecimal src,Double d){
		return add(src,d,2);
	}
	
	/**
	 * 
	 * Describle(描述)：字符串转换成BigDecimal类型
	 *
	 * 方法名称：stringTobigDecimal
	 *
	 * 所在类名：DigitUtil
	 *
	 * Create Time:2015年7月14日 下午3:26:21
	 *  
	 * @param str
	 * @param scale
	 * @param round
	 * @return
	 */
	public static BigDecimal stringTobigDecimal(String str,int scale,int round){
		if(!StringUtils.hasText(str)){
			return BigDecimal.ZERO;
		}
		try{
			BigDecimal result = new BigDecimal(str);
			result.setScale(scale, round);
			return result;
		}catch(NumberFormatException nfe){
			throw new ServiceException("字符串【"+str+"】不能转成BigDecimal", nfe.getCause());
		}
	}
	public static BigDecimal stringTobigDecimal(String str,int scale){
		return stringTobigDecimal(str,scale,BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal stringTobigDecimal(String str){
		return stringTobigDecimal(str,0,BigDecimal.ROUND_HALF_UP);
	}
	
	
}
