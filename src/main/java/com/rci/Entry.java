package com.rci;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rci.ui.swing.RootFrame;

public class Entry {
	private static final Log logger = LogFactory.getLog(Entry.class);
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try{
			new ClassPathXmlApplicationContext("spring/spring-common.xml","spring/spring-db.xml");
			JFrame frame = new RootFrame();
			URL sysIconUrl = Entry.class.getClassLoader().getResource("skin/gray/images/24x24/logo.png");
			Image frameIcon = Toolkit.getDefaultToolkit().createImage(sysIconUrl);
			frame.setIconImage(frameIcon);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setVisible(true);
		}catch(Exception ex){
			logger.error("系统启动错误！",ex);
			ex.printStackTrace();
		}
		
//		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
//			   @Override
//			   public boolean dispatchKeyEvent(KeyEvent e) {
//			      // KeyEvent.KEY_TYPED、KEY_PRESSED、KEY_RELEASED
//			      System.out.println("e.getID() = " + e.getID());
//			      System.out.println("e.isControlDown() = " + e.isControlDown());
//			      System.out.println("e.isShiftDown() = " + e.isShiftDown());
//			      System.out.println(" e.getKeyCode() = " + e.getKeyCode());
//			      // e.consume();// 是否已经消费了此事件
//			      return e.isConsumed();
//			   }
//			});
	}

}
