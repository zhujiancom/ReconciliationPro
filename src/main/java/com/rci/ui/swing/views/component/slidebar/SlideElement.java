package com.rci.ui.swing.views.component.slidebar;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SlideElement extends JButton implements KeyListener,MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8339433722869328141L;
	
	public enum State{
		SELECTED,HOVER,NORMAL
	}
	
	private boolean isChecked = false;

	private State state;
	
	private Object value;
	
	private Image selectedImage;
	
	private Image hoverImage;
	
	private Image unSelectedImage;
	
	private int index;
	
	private int width;
	
	private int height;
	
	private int hgap = 10;
	
	private int vgap = 10;
	
	private SlideBarListener listener;
	
	private Font defaultFont;
	
	public SlideElement(String text,Object value,Font font,State state){
		super(text);
		this.value = value;
		this.state = state;
		this.defaultFont = font;
		initComponent();
	}
	public SlideElement(String text,Object value,State state){
		this(text,value,new Font("微软雅黑", Font.BOLD, 12),state);
	}
	
	public SlideElement(String text,Object value,Font font){
		this(text,value,font,State.NORMAL);
	}
	
	public SlideElement(String text,Object value){
		this(text,value,State.NORMAL);
	}
	
	
	private void initComponent(){
		if(State.SELECTED.equals(state)){
			setChecked(true);
		}
		URL selectedImageUrl = this.getClass().getClassLoader().getResource("skin/gray/images/64x64/desk2.png");
		URL unSelectedImageUrl = this.getClass().getClassLoader().getResource("skin/gray/images/64x64/desk.png");
		URL hoverImageUrl = this.getClass().getClassLoader().getResource("skin/gray/images/64x64/desk3.png");
		selectedImage = new ImageIcon(selectedImageUrl).getImage();
		unSelectedImage = new ImageIcon(unSelectedImageUrl).getImage();
		hoverImage = new ImageIcon(hoverImageUrl).getImage();
		this.width = selectedImage.getWidth(this);
		this.height = selectedImage.getWidth(this);
		setPreferredSize(new Dimension(width, height));
//		final SlideElement currentElement = this;
//		this.addMouseListener(new MouseAdapter() {
				
//			@Override
//			public void mouseClicked(MouseEvent event ) {
//				if(event.getClickCount() == 1){
//					if(listener != null){
//						listener.fireUIUpdate(currentElement);
//						currentElement.setState(State.SELECTED);
//						currentElement.setChecked(true);
//					}
//				}
//			}

//			@Override
//			public void mouseEntered(MouseEvent e) {
//				setState(State.HOVER);
//			}

//			@Override
//			public void mouseExited(MouseEvent e) {
//				if(isChecked){
//					setState(State.SELECTED);
//				}else{
//					setState(State.NORMAL);
//				}
//			}
//		});
		addKeyListener(this);
		addMouseMotionListener(this);
	}
	
	
	
	/* 
	 * @see javax.swing.AbstractButton#addActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addActionListener(ActionListener paramActionListener) {
		super.addActionListener(paramActionListener);
		listener = (SlideBarListener)paramActionListener;
		listener.registerElement(this);
	}
	
	public SlideElement(String text){
		super(text);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		setContentAreaFilled(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createEmptyBorder());
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if(State.NORMAL.equals(state)){
			g2.setPaint(Color.RED);
			g2.drawImage(unSelectedImage, 0, 0, this);
		}else if(State.HOVER.equals(state)){
			g2.setPaint(Color.WHITE);
			g2.drawImage(hoverImage, 0, 0, this);
		}else if(State.SELECTED.equals(state)){
			g2.setPaint(Color.WHITE);
			g2.drawImage(selectedImage, 0, 0, this);
		}
		
		g2.setFont(getDefaultFont());
		Rectangle2D rect = getDefaultFont().getStringBounds(getText(),g2.getFontRenderContext());
		LineMetrics lineMetrics = getDefaultFont().getLineMetrics(getText(),  
	            g2.getFontRenderContext());
		float fx = (float) (width/2 - rect.getWidth()/2);
		float fy = (height/2)+ ((lineMetrics.getAscent() + lineMetrics.getDescent()) / 2 - lineMetrics.getDescent());
		g2.drawString(getText(), fx, fy);
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

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37).append(this.value).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj != null && SlideElement.class.isAssignableFrom(obj.getClass())){
			SlideElement o = (SlideElement) obj;
			isEqual = new EqualsBuilder().append(this.value, o.value).isEquals();
		}
		return isEqual;
	}
	/* 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
	}
	/* 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.requestFocusInWindow();
	}
	/* 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		int elementCount = listener.getElementCount();
		int _idx = this.getIndex();
		if(keycode == KeyEvent.VK_RIGHT){
			_idx++;
			if(_idx >= elementCount){
				_idx = elementCount - 1;
			}
		}
		if(keycode == KeyEvent.VK_LEFT){
			_idx--;
			if(_idx < 0){
				_idx = 0;
			}
		}
		listener.moveTo(_idx);
	}
	/* 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	/* 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
