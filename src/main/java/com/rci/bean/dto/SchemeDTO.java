package com.rci.bean.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.rci.annotation.ExcelColumn;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.SchemeType;
import com.rci.enums.BusinessEnums.Vendor;

public class SchemeDTO {
	private String name;
	
	private ActivityStatus activityStatus;
	
	/* 活动平台 */
	private Vendor vendor;
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
	
	/* 最高消费额度 */
	private BigDecimal ceilAmount;
	
	/* 最低消费额度 */
	private BigDecimal floorAmount;
	
	private Timestamp createTime;

	private Timestamp modifyTime;
	
	@ExcelColumn(value="活动名称",index=0)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelColumn(value="活动平台",index=1)
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	@ExcelColumn(value="活动状态",index=2)
	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	@ExcelColumn(value="平台补贴",index=3)
	public BigDecimal getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(BigDecimal postPrice) {
		this.postPrice = postPrice;
	}

	@ExcelColumn(value="优惠金额",index=4)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@ExcelColumn(value="餐厅补贴",index=5)
	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	@ExcelColumn(value="代金券类型",index=6)
	public SchemeType getType() {
		return type;
	}

	public void setType(SchemeType type) {
		this.type = type;
	}

	@ExcelColumn(value="开始时间",index=7)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@ExcelColumn(value="结束时间",index=8)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ExcelColumn(value="最低消费金额",index=9)
	public BigDecimal getFloorAmount() {
		return floorAmount;
	}

	public void setFloorAmount(BigDecimal floorAmount) {
		this.floorAmount = floorAmount;
	}

	@ExcelColumn(value="最高消费金额",index=10)
	public BigDecimal getCeilAmount() {
		return ceilAmount;
	}

	public void setCeilAmount(BigDecimal ceilAmount) {
		this.ceilAmount = ceilAmount;
	}

	@ExcelColumn(value="创建时间",index=11)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@ExcelColumn(value="更新时间",index=12)
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
