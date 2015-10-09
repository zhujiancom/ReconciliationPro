/**
 * 
 */
package com.rci.ui.swing.model;

import javax.swing.JCheckBox;

import com.rci.enums.BusinessEnums.Vendor;

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
	private Vendor vendor;
	
	public VendorJCheckBox(Vendor vendor,String paramString){
		super(paramString);
		this.vendor = vendor;
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

}
