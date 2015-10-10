/**
 * 
 */
package com.rci.ui.swing.model;

import java.awt.Color;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.rci.enums.BusinessEnums.Vendor;
import com.rci.ui.swing.listeners.VendorCheckListener;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：VendorJCheckBox
 *
 * 包名称：com.rci.ui.swing.model
 *
 * Create Time: 2015年10月4日 下午4:35:43
 *
 */
public class VendorJCheckBox extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4784196219806541629L;
	private int index;
	private Vendor vendor;
	
	public VendorJCheckBox(int index,Vendor vendor,String paramString){
		super(paramString);
		this.index = index;
		this.vendor = vendor;
		this.setBackground(Color.WHITE);
	}

	/**
	 * @return the vendor
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void addItemListener(ItemListener l) {
		super.addItemListener(l);
		((VendorCheckListener)l).addCheckBox(this);
	}

}
