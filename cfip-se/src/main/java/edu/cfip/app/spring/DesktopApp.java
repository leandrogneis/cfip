package edu.cfip.app.spring;

import javax.swing.UIManager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.app.desktop.FrmLogin;
import edu.cfip.app.desktop.MDICfip;
import edu.cfip.app.spring.config.AppConfig;
import edu.cfip.core.config.PersistenceConfig;
import edu.cfip.core.model.Usuario;

public class DesktopApp {
	/**
	 * https://www.tutorialspoint.com/pg/jpa/jpa_jpql.htm
	 * 3. Usuários e Login
	 */
	public static int USUARIO=1;
	private static AnnotationConfigApplicationContext context;
	public static void main(String[] args) {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
			context = new AnnotationConfigApplicationContext(AppConfig.class,PersistenceConfig.class);
			FrmLogin frm = context.getBean(FrmLogin.class);
			frm.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void iniciarAplicacao(Usuario usuario) {
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
