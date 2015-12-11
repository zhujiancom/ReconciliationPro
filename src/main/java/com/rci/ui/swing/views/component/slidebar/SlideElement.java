package com.rci.ui.swing.views.component.slidebar;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SlideElement<T> extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8339433722869328141L;

	private boolean isSelected;
	
	private T value;
	
	private URL selectedImageUrl;
	
	private URL unSelectedImageUrl;
	
	private Image selectedImage;
	
	private Image unSelectedImage;
	
	private MediaTracker mtracker;
	
	private int index;
	
	private int width;
	
	private int height;
	
	private int hgap = 10;
	
	private int vgap = 10;
	
	public SlideElement(String text,T value,boolean isSelected){
		super(text);
		this.value = value;
		this.isSelected = isSelected;
		selectedImageUrl = this.getClass().getClassLoader().getResource("skin/gray/images/64x64/desk2.png");
		unSelectedImageUrl = this.getClass().getClassLoader().getResource("skin/gray/images/64x64/desk.png");
		mtracker = new MediaTracker(this);
//		selectedImage = Toolkit.getDefaultToolkit().getImage(selectedImageUrl);
		selectedImage = new ImageIcon(selectedImageUrl).getImage();
//		unSelectedImage = Toolkit.getDefaultToolkit().getImage(unSelectedImageUrl);
		unSelectedImage = new ImageIcon(unSelectedImageUrl).getImage();
//		mtracker.addImage(selectedImage, 1);
//		mtracker.addImage(unSelectedImage, 2);
		this.width = selectedImage.getWidth(this);
		this.height = selectedImage.getWidth(this);
		setPreferredSize(new Dimension(width, height));
	}
	
	public SlideElement(String text,T value){
		this(text,value,false);
	}
	
	public SlideElement(String text){
		super(text);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if(isSelected){
			g2.setPaint(Color.WHITE);
			g2.drawImage(selectedImage, 0, 0, this);
		}else{
			g2.setPaint(Color.RED);
			g2.drawImage(unSelectedImage, 0, 0, this);
		}
		Font defaultFont = new Font("微软雅黑", Font.BOLD, 20);
		Rectangle2D rect = defaultFont.getStringBounds(getText(),g2.getFontRenderContext()); 
		LineMetrics lineMetrics = defaultFont.getLineMetrics(getText(),  
	            g2.getFontRenderContext()); 
		g2.drawString(getText(), (float) (width / 2 - rect.getWidth() / 2),  
	            (float) ((height / 2) + ((lineMetrics.getAscent() + lineMetrics  
	                    .getDescent()) / 2 - lineMetrics.getDescent())));
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHgap() {
		return hgap;
	}

	public int getVgap() {
		return vgap;
	}

	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	public void setVgap(int vgap) {
		this.vgap = vgap;
	}
}
