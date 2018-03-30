package edu.cfip.app.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.app.desktop.FrmCfipLogin;
import edu.cfip.app.desktop.MDICfip;
import edu.cfip.app.spring.config.AppConfig;
import edu.cfip.core.config.PersistenceConfig;
import edu.cfip.core.model.Usuario;
import edu.porgamdor.util.desktop.DesktopApp;

public class SpringDesktopApp extends DesktopApp {
	/**
	 * https://www.tutorialspoint.com/pg/jpa/jpa_jpql.htm
	 * 3. Usuários e Login
	 */
	public static int USUARIO=1;
	private static AnnotationConfigApplicationContext context;
	public static void main(String[] args) {
		try {
			configurarAplicacao();
			context = new AnnotationConfigApplicationContext(AppConfig.class,PersistenceConfig.class);
			iniciarAplicacao();
			FrmCfipLogin frm = context.getBean(FrmCfipLogin.class);
			frm.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void atualizarAplicacao(Usuario usuario) {
		MDICfip mdi = context.getBean(MDICfip.class);
		//AQUI PODEMOS INICIAR A NOSSA SESSAO ... 
		mdi.setVisible(true); 
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
