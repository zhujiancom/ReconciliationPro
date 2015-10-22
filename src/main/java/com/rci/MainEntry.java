package com.rci;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rci.ui.swing.MainFrame;

public class MainEntry {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring/spring-common.xml","spring/spring-db.xml");
		JFrame frame = new MainFrame();
		URL sysIconUrl = MainEntry.class.getClassLoader().getResource("skin/gray/images/24x24/logo.png");
		Image frameIcon = Toolkit.getDefaultToolkit().createImage(sysIconUrl);
		frame.setIconImage(frameIcon);
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		frame.setVisible(true);
	}

}
