package edu.cfip.app.spring;

import javax.swing.UIManager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.app.desktop.MDICfip;
import edu.cfip.app.spring.config.AppConfig;

public class DesktopApp {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		String lf = UIManager.getSystemLookAndFeelClassName();
		try {
			//UIManager.setLookAndFeel(lf);
			MDICfip mdi = context.getBean(MDICfip.class);
			mdi.setContext(context);
			mdi.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
