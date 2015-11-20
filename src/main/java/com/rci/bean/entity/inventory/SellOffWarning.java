package com.rci.bean.entity.inventory;

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

import com.rci.bean.entity.base.BaseEntity;
import com.rci.enums.BusinessEnums.State;

/**
 * 
 * remark (备注):沽清警告记录
 * 
 * @author zj
 * 
 *         项目名称：ReconciliationPro
 * 
 *         类名称：SellOff
 * 
 *         包名称：com.rci.bean.entity.inventory
 * 
 *         Create Time: 2015年11月20日 下午2:48:01
 * 
 */
@Entity
@Table(name = "bus_tb_selloff_warning")
public class SellOffWarning extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2349286395320327517L;

	private Long swid;

	private String ino;

	private String iname;

	private Date so_date;

	private State state;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "swid", nullable = false, updatable = false)
	public Long getSwid() {
		return swid;
	}

	@Column(name = "ino")
	public String getIno() {
		return ino;
	}

	@Column(name = "iname")
	public String getIname() {
		return iname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "so_date")
	public Date getSo_date() {
		return so_date;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	public State getState() {
		return state;
	}

	public void setSwid(Long swid) {
		this.swid = swid;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public void setSo_date(Date so_date) {
		this.so_date = so_date;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	@Transient
	public Serializable getId() {
		// TODO Auto-generated method stub
		return swid;
	}

}
