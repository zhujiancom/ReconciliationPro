package com.rci.bean.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="core_tb_dict_item")
public class DictItem extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2258275601343756117L;
	
	private Integer version;
	
	private Long dtId;
	
	/* �ֵ������ */
	private String itemCode;

	private String itemCname;
	
	private String itemEname;
	
	/* ���������*/
	private String groupCode;
	
	/* ��� */
	private Integer seq;
	
	/* ����*/
	private String description;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="dtid", nullable=false,updatable=false)
	public Long getDtId() {
		return dtId;
	}

	public void setDtId(Long dtId) {
		this.dtId = dtId;
	}

	@Column(name="item_code")
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_c_name")
	public String getItemCname() {
		return itemCname;
	}

	public void setItemCname(String itemCname) {
		this.itemCname = itemCname;
	}

	@Column(name="item_e_name")
	public String getItemEname() {
		return itemEname;
	}

	public void setItemEname(String itemEname) {
		this.itemEname = itemEname;
	}

	@Column(name="group_code")
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="seq")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public Serializable getId() {
		return dtId;
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
