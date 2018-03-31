package edu.cfip.app.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.app.desktop.FrmCfipLogin;
import edu.cfip.app.spring.config.AppConfig;
import edu.cfip.core.config.PersistenceConfig;
import edu.porgamdor.util.desktop.DesktopApp;

public class SpringDesktopApp extends DesktopApp {
	public static int USUARIO = 1;
	private static AnnotationConfigApplicationContext context;
	//CFIP - Conexao Postgre e HSQLDB (Multi Datasource)
	public static void main(String[] args) {
		try {
			if (DesktopApp.iniciarAplicacao()) {
				context = new AnnotationConfigApplicationContext(AppConfig.class, PersistenceConfig.class);
				FrmCfipLogin frm = context.getBean(FrmCfipLogin.class);
				frm.exibir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AnnotationConfigApplicationContext getContext() {
		return context;
	}

	public static <T> T getBean(Class bean) {
		return (T) context.getBean(bean);
	}

	public static <T> T getBean(String bean) {
		return (T) context.getBean(bean);
	}
}
