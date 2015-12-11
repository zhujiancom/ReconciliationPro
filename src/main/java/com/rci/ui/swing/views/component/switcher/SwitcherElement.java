/**
 * 
 */
package com.rci.ui.swing.views.component.switcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SwitcherElement
 *
 * 包名称：com.rci.ui.swing.views.component.switcher
 *
 * Create Time: 2015年12月8日 上午12:04:07
 *
 */
public class SwitcherElement extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8977376510124218442L;
	private static final int DEFAULT_WIDTH=50;
	private static final int DEFAULT_HEIGHT=20;
	
	private String name;
	
	private boolean isSelected;
	
	private int width;
	
	private int height;
	
	private int index;
	
	private ActionListener listener;
	
	public SwitcherElement(String name){
		this(name,false);
	}
	
	public SwitcherElement(String name,boolean isSelected){
		this(name,isSelected,0);
	}
	
	public SwitcherElement(String name,int index){
		this(name,false,index);
	}
	
	public SwitcherElement(String name,boolean isSelected,int index){
		this(name,isSelected,DEFAULT_WIDTH,DEFAULT_HEIGHT,index);
	}
	
	public SwitcherElement(String name,boolean isSelected,int width,int height,int index){
		super(name);
		this.name = name;
		this.isSelected = isSelected;
		this.width = width;
		this.height = height;
		this.index = index;
		initComponent();
	}

	private void initComponent() {
		setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	protected void paintComponent(Graphics paramGraphics) {
		Graphics2D g2 = (Graphics2D) paramGraphics;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		int width = this.getPreferredSize().width;  
        int height = this.getPreferredSize().height;
        if(index != 0){
        	setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.LIGHT_GRAY));
        }else{
        	setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        if(isSelected){
        	g2.setPaint(Color.WHITE);
        	g2.setBackground(Color.BLUE);
        	g2.clearRect(0, 0, width, height);
        }else{
        	g2.setBackground(Color.WHITE);
        	g2.setPaint(Color.BLACK);
        	g2.clearRect(0, 0, width, height);
        }
        Font defaultFont = new Font("微软雅黑", Font.PLAIN, 12);
        g2.setFont(defaultFont);
        Rectangle2D rect = defaultFont.getStringBounds(name,g2.getFontRenderContext()); 
        LineMetrics lineMetrics = defaultFont.getLineMetrics(name,  
                g2.getFontRenderContext());  
        g2.drawString(  
                name,  
                (float) (width / 2 - rect.getWidth() / 2),  
                (float) ((height / 2) + ((lineMetrics.getAscent() + lineMetrics  
                        .getDescent()) / 2 - lineMetrics.getDescent()))); 
	}

	public ActionListener getListener() {
		return listener;
	}

	public void setListener(ActionListener listener) {
		this.listener = listener;
		addActionListener(listener);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
