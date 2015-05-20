package com.rci.bean.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums.Vendor;

@Entity
@Table(name = "bus_tb_ticket_statistic")
public class TicketStatistic extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1872886783289527864L;
	private Integer version;
	private Long tid;
	
	/* 当日 50元代金券验证数量*/
	private Integer chit50;
	
	/* 当日100元代金券验证数量*/
	private Integer chit100;
	
	/* 当日套餐A验证数量*/
	private Integer suit32;
	
	/* 当日套餐B验证数量*/
	private Integer suit68;
	
	/* 当日套餐C验证数量*/
	private Integer suit98;
	
	/* 总验券名称 */
	private String name;
	
	/* 代金券平台 */
	private Vendor vendor;
	
	/* 日期 */
	private Date date;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// MYSQL ID generator
	@Column(name = "tid", nullable = false, updatable = false)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	@Column(name="chit_50")
	public Integer getChit50() {
		return chit50;
	}

	public void setChit50(Integer chit50) {
		this.chit50 = chit50;
	}

	@Column(name="chit_100")
	public Integer getChit100() {
		return chit100;
	}

	public void setChit100(Integer chit100) {
		this.chit100 = chit100;
	}

	@Column(name="suit_32")
	public Integer getSuit32() {
		return suit32;
	}

	public void setSuit32(Integer suit32) {
		this.suit32 = suit32;
	}

	@Column(name="suit_68")
	public Integer getSuit68() {
		return suit68;
	}

	public void setSuit68(Integer suit68) {
		this.suit68 = suit68;
	}

	@Column(name="suit_98")
	public Integer getSuit98() {
		return suit98;
	}

	public void setSuit98(Integer suit98) {
		this.suit98 = suit98;
	}

	@Column(name="name")
	public String getName() {
		StringBuffer sb = new StringBuffer("");
		if(chit50!= null && chit50 >0){
			sb.append("50代金券  ").append(chit50).append(" 张,");
		}
		if(chit100!= null && chit100 >0){
			sb.append("100代金券  ").append(chit100).append(" 张,");
		}
		if(suit32!= null && suit32 >0){
			sb.append("套餐A ").append(suit32).append(" 张,");
		}
		if(suit68!= null && suit68 >0){
			sb.append("套餐B  ").append(suit68).append(" 张,");
		}
		if(suit98!= null && suit98 >0){
			sb.append("套餐C ").append(suit98).append(" 张,");
		}
		name = sb.toString();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="vendor")
	@Enumerated(EnumType.STRING)
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@Column(name="date")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Transient
	@Override
	public Serializable getId() {
		return tid;
	}

	@Override
	@Version
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}

}
