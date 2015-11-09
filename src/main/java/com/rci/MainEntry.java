package com.rci;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rci.ui.swing.MainFrame;

public class MainEntry {
	private static final Log logger = LogFactory.getLog(MainEntry.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try{
			new ClassPathXmlApplicationContext("spring/spring-common.xml","spring/spring-db.xml");
			JFrame frame = new MainFrame();
			URL sysIconUrl = MainEntry.class.getClassLoader().getResource("skin/gray/images/24x24/logo.png");
			Image frameIcon = Toolkit.getDefaultToolkit().createImage(sysIconUrl);
			frame.setIconImage(frameIcon);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
			/* 全屏设置1 */
//			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
//	                .getDefaultScreenDevice();
//			frame.setUndecorated(true);
//			gd.setFullScreenWindow(frame);
			
			/* 全屏设置 2*/
//			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//			frame.setBounds(0, 0, dimension.width, dimension.height);
			
			/* 指定窗口尺寸 */
			
			
//			frame.setUndecorated(true);
			frame.setVisible(true);
		}catch(Exception ex){
			logger.error(ex);
		}
	}

}
