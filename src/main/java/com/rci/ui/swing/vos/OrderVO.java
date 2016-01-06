/**
 * 
 */
package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.enums.CommonEnums;
import com.rci.enums.CommonEnums.YOrN;

/**
 * @author zj
 * 
 *         项目名称：BillSystem
 * 
 *         类名称：OrderVO
 * 
 *         包名称：com.bill.sys.bean.vos
 * 
 *         Create Time: 2015年4月23日 下午11:36:54
 * 
 *         remark (备注):
 * 
 */
public class OrderVO {
	private Long orderId;

	private String payNo;
	
	private String tableName;

	private BigDecimal originAmount;

	private BigDecimal actualAmount;

	private String schemeName;

	private String isTempScheme;

	private Date checkoutTime;
	
	private String[] paymodecodes;

	/* 收银机支付金额*/
	private BigDecimal cashmachineAmount;

	/* 美团在线支付金额  */
	private BigDecimal mtAmount;

	/* 大众点评团购在线支付金额  */
	private BigDecimal dptgAmount;
	
	private BigDecimal dpshAmount;

	/* 淘点点在线支付金额支付宝入账  */
	private BigDecimal aliPayAmount;

	/* 饿了么在线支付金额 */
	private BigDecimal eleAmount;
	
	/* 整单实际收入总额  */
	private BigDecimal totalAmount;

	/* 美团外卖在线支付金额  */
	private BigDecimal mtwmAmount;
	
	/* 美团外卖活动补贴免单金额 */
	private BigDecimal mtwmFreeAmount;
	
	/* 饿了么活动补贴免单金额  */
	private BigDecimal eleFreeAmount;
	
	/* 堂食免单金额 */
	private BigDecimal freeAmount;
	
	/* 在线免单金额  */
	private BigDecimal onlineFreeAmount;
	
	/* 美团超级代金券金额*/
	private BigDecimal mtSuperAmount;
	
	private BigDecimal mtSuperFreeAmount;
	
	private BigDecimal posAmount;
	
	private BigDecimal wmcrAmount;
	
	private BigDecimal wmcrbtAmount;
	
	private YOrN unusual;

	private BigDecimal nodiscountAmount;

	private CommonEnums.YOrN singleDiscount;
	
	private String warningInfo;

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the payNo
	 */
	public String getPayNo() {
		return payNo;
	}

	/**
	 * @param payNo
	 *            the payNo to set
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the originAmount
	 */
	public BigDecimal getOriginAmount() {
		return originAmount == null ? BigDecimal.ZERO:originAmount;
	}

	/**
	 * @param originAmount
	 *            the originAmount to set
	 */
	public void setOriginAmount(BigDecimal originAmount) {
		this.originAmount = originAmount;
	}

	/**
	 * @return the actualAmount
	 */
	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount
	 *            the actualAmount to set
	 */
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return the schemeName
	 */
	public String getSchemeName() {
		return schemeName;
	}

	/**
	 * @param schemeName
	 *            the schemeName to set
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	/**
	 * @return the isTempScheme
	 */
	public String getIsTempScheme() {
		return isTempScheme;
	}

	/**
	 * @param isTempScheme
	 *            the isTempScheme to set
	 */
	public void setIsTempScheme(String isTempScheme) {
		this.isTempScheme = isTempScheme;
	}

	/**
	 * @return the checkoutTime
	 */
	public Date getCheckoutTime() {
		return checkoutTime;
	}

	/**
	 * @param checkoutTime
	 *            the checkoutTime to set
	 */
	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}


	public BigDecimal getCashmachineAmount() {
		return cashmachineAmount;
	}

	public void setCashmachineAmount(BigDecimal cashmachineAmount) {
		this.cashmachineAmount = cashmachineAmount;
	}

	/**
	 * @return the mtAmount
	 */
	public BigDecimal getMtAmount() {
		return mtAmount;
	}

	/**
	 * @param mtAmount
	 *            the mtAmount to set
	 */
	public void setMtAmount(BigDecimal mtAmount) {
		this.mtAmount = mtAmount;
	}

	/**
	 * @return the dptgAmount
	 */
	public BigDecimal getDptgAmount() {
		return dptgAmount;
	}

	/**
	 * @param dptgAmount
	 *            the dptgAmount to set
	 */
	public void setDptgAmount(BigDecimal dptgAmount) {
		this.dptgAmount = dptgAmount;
	}
//
//	/**
//	 * @return the dpshAmount
//	 */
//	@Deprecated
//	public BigDecimal getDpshAmount() {
//		return dpshAmount;
//	}
//
//	/**
//	 * @param dpshAmount
//	 *            the dpshAmount to set
//	 */
//	@Deprecated
//	public void setDpshAmount(BigDecimal dpshAmount) {
//		this.dpshAmount = dpshAmount;
//	}

	public BigDecimal getDpshAmount() {
		return dpshAmount;
	}

	public void setDpshAmount(BigDecimal dpshAmount) {
		this.dpshAmount = dpshAmount;
	}


	/**
	 * @return the aliPayAmount
	 */
	public BigDecimal getAliPayAmount() {
		return aliPayAmount;
	}

	/**
	 * @param aliPayAmount the aliPayAmount to set
	 */
	public void setAliPayAmount(BigDecimal aliPayAmount) {
		this.aliPayAmount = aliPayAmount;
	}

	/**
	 * @return the eleAmount
	 */
	public BigDecimal getEleAmount() {
		return eleAmount;
	}

	/**
	 * @param eleAmount
	 *            the eleAmount to set
	 */
	public void setEleAmount(BigDecimal eleAmount) {
		this.eleAmount = eleAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the mtwmAmount
	 */
	public BigDecimal getMtwmAmount() {
		return mtwmAmount;
	}

	/**
	 * @param mtwmAmount
	 *            the mtwmAmount to set
	 */
	public void setMtwmAmount(BigDecimal mtwmAmount) {
		this.mtwmAmount = mtwmAmount;
	}

	public BigDecimal getMtwmFreeAmount() {
		return mtwmFreeAmount;
	}

	public void setMtwmFreeAmount(BigDecimal mtwmFreeAmount) {
		this.mtwmFreeAmount = mtwmFreeAmount;
	}

	public BigDecimal getEleFreeAmount() {
		return eleFreeAmount;
	}

	public void setEleFreeAmount(BigDecimal eleFreeAmount) {
		this.eleFreeAmount = eleFreeAmount;
	}

	public YOrN getUnusual() {
		return unusual;
	}

	public void setUnusual(YOrN unusual) {
		this.unusual = unusual;
	}

	/**
	 * @return the nodiscountAmount
	 */
	public BigDecimal getNodiscountAmount() {
		return nodiscountAmount;
	}

	/**
	 * @param nodiscountAmount
	 *            the nodiscountAmount to set
	 */
	public void setNodiscountAmount(BigDecimal nodiscountAmount) {
		this.nodiscountAmount = nodiscountAmount;
	}

	public CommonEnums.YOrN getSingleDiscount() {
		return singleDiscount;
	}

	public void setSingleDiscount(CommonEnums.YOrN singleDiscount) {
		this.singleDiscount = singleDiscount;
	}

	public BigDecimal getFreeAmount() {
		return freeAmount;
	}

	public void setFreeAmount(BigDecimal freeAmount) {
		this.freeAmount = freeAmount;
	}

	/**
	 * @return the mtSuperAmount
	 */
	public BigDecimal getMtSuperAmount() {
		return mtSuperAmount;
	}

	/**
	 * @param mtSuperAmount the mtSuperAmount to set
	 */
	public void setMtSuperAmount(BigDecimal mtSuperAmount) {
		this.mtSuperAmount = mtSuperAmount;
	}

	/**
	 * @return the mtSuperFreeAmount
	 */
	public BigDecimal getMtSuperFreeAmount() {
		return mtSuperFreeAmount;
	}

	/**
	 * @param mtSuperFreeAmount the mtSuperFreeAmount to set
	 */
	public void setMtSuperFreeAmount(BigDecimal mtSuperFreeAmount) {
		this.mtSuperFreeAmount = mtSuperFreeAmount;
	}

	public BigDecimal getPosAmount() {
		return posAmount;
	}

	public void setPosAmount(BigDecimal posAmount) {
		this.posAmount = posAmount;
	}

	public String getWarningInfo() {
		return warningInfo;
	}

	public void setWarningInfo(String warningInfo) {
		this.warningInfo = warningInfo;
	}

	public BigDecimal getOnlineFreeAmount() {
		return onlineFreeAmount;
	}

	public void setOnlineFreeAmount(BigDecimal onlineFreeAmount) {
		this.onlineFreeAmount = onlineFreeAmount;
	}

	public BigDecimal getWmcrAmount() {
		return wmcrAmount;
	}

	public void setWmcrAmount(BigDecimal wmcrAmount) {
		this.wmcrAmount = wmcrAmount;
	}

	public BigDecimal getWmcrbtAmount() {
		return wmcrbtAmount;
	}

	public void setWmcrbtAmount(BigDecimal wmcrbtAmount) {
		this.wmcrbtAmount = wmcrbtAmount;
	}

	public String[] getPaymodecodes() {
		return paymodecodes;
	}

	public void setPaymodecodes(String[] paymodecodes) {
		this.paymodecodes = paymodecodes;
	}

}
