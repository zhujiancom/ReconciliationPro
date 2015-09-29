package com.rci.ui.swing.vos;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;

public class SchemeVO {
	private String name;
	
	/* 活动平台 */
	private Vendor vendor;
	
	private String dishplayVendor;
	
	/* 平台补贴 */
	private BigDecimal postPrice;
	
	/* 优惠金额 */
	private BigDecimal price;
	
	/* 餐厅补贴 */
	private BigDecimal spread;
	
	private SchemeType type;
	
	/* 开始时间 */
	private Date startDate;
	/* 结束时间 */
	private Date endDate;
	
	/* 活动状态  */
	private String status;
	
	/* 最高消费额度 */
	private BigDecimal ceilAmount;
	
	/* 最低消费额度 */
	private BigDecimal floorAmount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getDishplayVendor() {
		return dishplayVendor;
	}

	public void setDishplayVendor(String dishplayVendor) {
		this.dishplayVendor = dishplayVendor;
	}

	public BigDecimal getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(BigDecimal postPrice) {
		this.postPrice = postPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	public SchemeType getType() {
		return type;
	}

	public void setType(SchemeType type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getCeilAmount() {
		return ceilAmount;
	}

	public void setCeilAmount(BigDecimal ceilAmount) {
		this.ceilAmount = ceilAmount;
	}

	public BigDecimal getFloorAmount() {
		return floorAmount;
	}

	public void setFloorAmount(BigDecimal floorAmount) {
		this.floorAmount = floorAmount;
	}
}
