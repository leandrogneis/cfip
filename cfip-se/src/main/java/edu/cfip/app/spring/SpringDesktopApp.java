package edu.cfip.app.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.cfip.app.desktop.FrmCfipLogin;
import edu.cfip.app.spring.config.AppConfig;
import edu.cfip.core.config.PersistenceConfig;
import edu.porgamdor.util.desktop.DesktopApp;

public class SpringDesktopApp extends DesktopApp {
	/**
	 * 1 CFIP - Explorando os recursos utilitários (DRY)
	 * 1 CFIP - Configurção a nível de execução
	 * 
	 * 2 CFIP - Conexao Postgre e HSQLDB (Multi Datasource)
	 * 3 CFIP - Conceito de Perfil e Sessão
	 * 
	 * 1 CONFIGURAÇÃO INICIAL DO AMBIENTE
	 * 1 CADASTRO DE USUARIO - PERFIL VIA APLICAÇÃO
	 * 1 CONHECER AS CLASSES UTEIS DO NOSSO AMBIENTE
	 * 
	 * 2 CONFIGURAR DATA SOURCE DINAMICAMENTE
	 * 3 PERFIL E SESSAO
	 * 3 REGISTRAR O USUARIO LOGADO NA NOSSA SESSÃO
	 */
	//3 public static int USUARIO = 1;
	
	private static AnnotationConfigApplicationContext context;
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
