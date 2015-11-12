package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.rci.bean.SchemeWrapper;
import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.CommonEnums;
import com.rci.tools.StringUtils;
@Entity
@Table(name="bus_tb_order")
public class Order extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5047158554635480893L;
	
	private Long oid;
	
	/* 订单编号   */
	private String orderNo;
	
	/* 付费编号 */
	private String payNo;
	
	/* 桌号 */
	private String tableNo;
	
	/* 开桌时间  */
	private Date openDeskTime;
	
	/* 结账时间  */
	private Date checkoutTime;
	
	/*日期 */
	private String day;
	
	/* 订单原价   */
	private BigDecimal originPrice;
	
	/* 订单支付方式对应金额 */
	private Map<PaymodeCode,BigDecimal> paymodeMapping=new HashMap<PaymodeCode,BigDecimal>();
	
	/* 订单所有的支付方式 ，用(,)逗号分割*/
	private String paymodes;

	/* 折扣方案 名称 */
	private String schemeName;
	
	/* 是否具有单品折扣  */
	private CommonEnums.YOrN singleDiscount;
	
	/* 实收金额   */
	private BigDecimal realAmount;
	
	/* 具体菜品明细  */
	private List<OrderItem> items;
	
	/* 记录该订单使用的方案 */
	private Map<SchemeType,SchemeWrapper> schemes; 
	
	/* 该单有异常 */
	private CommonEnums.YOrN unusual;
	
	/* 不可打折金额   */
	private BigDecimal nodiscountAmount;
	
	/* 订单错误原因信息*/
	private String warningInfo;
	
	/* 订单平台 */
	private BusinessEnums.OrderFramework framework;
	
	/**
	 * @return the oid
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="oid", nullable=false,updatable=false)
	public Long getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(Long oid) {
		this.oid = oid;
	}

	/**
	 * @return the orderNo
	 */
	@Column(name="order_no")
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the payNo
	 */
	@Column(name="pay_no")
	public String getPayNo() {
		return payNo;
	}

	/**
	 * @param payNo the payNo to set
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	/**
	 * @return the openDeskTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="opendesk_time")
	public Date getOpenDeskTime() {
		return openDeskTime;
	}

	/**
	 * @param openDeskTime the openDeskTime to set
	 */
	public void setOpenDeskTime(Date openDeskTime) {
		this.openDeskTime = openDeskTime;
	}

	/**
	 * @return the checkoutTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="checkout_time")
	public Date getCheckoutTime() {
		return checkoutTime;
	}

	/**
	 * @param checkoutTime the checkoutTime to set
	 */
	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	/**
	 * @return the day
	 */
	@Column(name="day")
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the originPrice
	 */
	@Column(name="origin_price")
	public BigDecimal getOriginPrice() {
		return originPrice;
	}

	/**
	 * @param originPrice the originPrice to set
	 */
	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}

	/**
	 * @return the paymodeMapping
	 */
	@Transient
	public Map<PaymodeCode, BigDecimal> getPaymodeMapping() {
		return paymodeMapping;
	}

	/**
	 * @param paymodeMapping the paymodeMapping to set
	 */
	public void setPaymodeMapping(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		this.paymodeMapping = paymodeMapping;
	}

	/**
	 * @return the schemeName
	 */
	@Column(name="scheme_name")
	public String getSchemeName() {
		return schemeName;
	}

	/**
	 * @param schemeName the schemeName to set
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	/**
	 * @return the singleDiscount
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="is_single_discount")
	public CommonEnums.YOrN getSingleDiscount() {
		return singleDiscount;
	}

	/**
	 * @param singleDiscount the singleDiscount to set
	 */
	public void setSingleDiscount(CommonEnums.YOrN singleDiscount) {
		this.singleDiscount = singleDiscount;
	}

	/**
	 * @return the realAmount
	 */
	@Column(name="real_amount")
	public BigDecimal getRealAmount() {
		return realAmount;
	}

	/**
	 * @param realAmount the realAmount to set
	 */
	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	/**
	 * @return the items
	 */
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="order")
	public List<OrderItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	@Transient
	public Map<SchemeType, SchemeWrapper> getSchemes() {
		return schemes;
	}

	public void setSchemes(Map<SchemeType, SchemeWrapper> schemes) {
		this.schemes = schemes;
	}

	/**
	 * @return the paymodes
	 */
	@Column(name="paymodes")
	public String getPaymodes() {
		return paymodes;
	}

	/**
	 * @param paymodes the paymodes to set
	 */
	public void setPaymodes(String paymodes) {
		this.paymodes = paymodes;
	}

	/**
	 * @return the unusual
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="is_unusual")
	public CommonEnums.YOrN getUnusual() {
		return unusual;
	}

	/**
	 * @param unusual the unusual to set
	 */
	public void setUnusual(CommonEnums.YOrN unusual) {
		this.unusual = unusual;
	}

	/**
	 * @return the nodiscountAmount
	 */
	@Column(name="no_discount_amount")
	public BigDecimal getNodiscountAmount() {
		return nodiscountAmount;
	}

	/**
	 * @param nodiscountAmount the nodiscountAmount to set
	 */
	public void setNodiscountAmount(BigDecimal nodiscountAmount) {
		this.nodiscountAmount = nodiscountAmount;
	}

	@Column(name="table_no")
	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	/* 
	 * @see org.zj.framework.core.entity.BaseEntity#getId()
	 */
	@Override
	@Transient
	public Serializable getId() {
		return oid;
	}

	/* 
	 * @see org.zj.framework.core.entity.BaseEntity#getVersion()
	 */
	
	/**
	 * 
	 *
	 * Describle(描述)：添加order所使用的支付方式和对应的支付金额
	 *
	 * 方法名称：addPayMode
	 *
	 * 所在类名：Order
	 *
	 * Create Time:2015年4月23日 下午10:49:03
	 *  
	 * @param paymodeNo
	 * @param amount
	 */
	public void addPayMode(String paymodeNo,BigDecimal amount){
		PaymodeCode paycode = PaymodeCode.paymodeCode(paymodeNo);
		if(paymodeMapping.get(paycode) != null){
			BigDecimal preAmount = paymodeMapping.get(paycode);
			amount = preAmount.add(amount);
		}
		paymodeMapping.put(PaymodeCode.paymodeCode(paymodeNo), amount);
		if(!StringUtils.hasLength(paymodes)){
			paymodes = paymodeNo;
		}else{
			paymodes = paymodes+","+paymodeNo;
		}
	}

	@Column(name="warning_info")
	public String getWarningInfo() {
		return warningInfo;
	}

	public void setWarningInfo(String warningInfo) {
		this.warningInfo = warningInfo;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="framework")
	public BusinessEnums.OrderFramework getFramework() {
		return framework;
	}

	public void setFramework(BusinessEnums.OrderFramework framework) {
		this.framework = framework;
	}
}
