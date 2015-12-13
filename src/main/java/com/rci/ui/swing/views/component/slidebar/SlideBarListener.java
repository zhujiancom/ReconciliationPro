/**
 * 
 */
package com.rci.ui.swing.views.component.slidebar;

import java.awt.event.ActionListener;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SlideBarListener
 *
 * 包名称：com.rci.ui.swing.views.component.slidebar
 *
 * Create Time: 2015年12月12日 上午11:11:41
 *
 */
public interface SlideBarListener extends ActionListener {
	void registerElement(SlideElement element);
	
	void fireUIUpdate(SlideElement currentElement);
}
