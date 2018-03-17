package client.desktop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.boxs.cfip.core.config.PersistenceConfig;
import com.boxs.cfip.core.util.Ambiente;

import client.desktop.app.acesso.FrmLogin;
import client.desktop.app.config.FrmConfiguracao;
import client.desktop.config.AppConfig;
import client.ss.desktop.Splash;

public class DesktopApp {
	private static Logger LOG = Logger.getLogger(DesktopApp.class.getName());
	private static AnnotationConfigApplicationContext context;
	private static String nomeAmbiente;
	public static void main(String[] args) {
		String lf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lf);
			LOG.info("Iniciando Splash");
			Splash splash = new Splash();
			LOG.info("Carregou o Splash");
			if(!arquivoConfiguracao().exists()) {
				configurarAmbiente(Ambiente.LOCAL);
				LOG.info("Não localizaou o arquivo de configuração");
			}
			if (context == null) {
				context = new AnnotationConfigApplicationContext(PersistenceConfig.class, AppConfig.class);
				LOG.info("Iniciando o contexto");
				splash.dispose();
				LOG.info("Fechando o Splash");
			}
			if(configurarAmbiente()) {
				LOG.info("Iniciando o processo de configuração");
				exibirConfiguracao();
			}else {
				LOG.info("Iniciando a tela de LOGIN");
				nomeAmbiente=getConfiguracao().getProperty(Ambiente.ENV_NAME);
				exibirLogin();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getStackTrace().toString());
		}
	}
	public static AnnotationConfigApplicationContext getContext() {
		return context;
	}
	public static void closeContext() {
		((ConfigurableApplicationContext)context ).close();
	}
	public static String getNomeAmbiente(){
		return nomeAmbiente;
	}
	public static boolean isBancoLocal(){
		return "LOCAL".equalsIgnoreCase(nomeAmbiente);
	}
	private static boolean configurarAmbiente() throws Exception {
		return getConfiguracao().getProperty(Ambiente.STATUS).equals("NOK");
	}
	private static void exibirConfiguracao() {
		FrmConfiguracao frm = context.getBean(FrmConfiguracao.class);
		try {
			frm.setParametros(null, "LOCAL");;
		} catch (Exception e) {
			e.printStackTrace();
		}
		frm.setVisible(true);
	}
	private static void exibirLogin() {
		FrmLogin frm = context.getBean(FrmLogin.class);
		frm.setVisible(true);
	}
	private static File arquivoConfiguracao() throws Exception{
		//File diretorio = isWindows() ? new File("/cfip/conf/") : new File("/cfip/conf/");
		File diretorio = new File("/cfip/conf/");
		File arquivo = new File(diretorio, "env.properties");
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
		File backup = new File("/cfip/backup/");
		if (!backup.exists()) {
			backup.mkdir();
		}
		
		return arquivo;
	}
	public static void configurarAmbiente(Ambiente ambiente) throws Exception {
		String configuracao = configuracao(ambiente);
		if(!configuracao.trim().isEmpty()){
			FileWriter writer=null;
			BufferedWriter bf=null;
			File arquivo = arquivoConfiguracao();
			arquivo.createNewFile();
			writer = new FileWriter(arquivo);
			bf = new BufferedWriter(writer);
			bf.write(configuracao);
			bf.flush();
			bf.close();
			bf=null;
			writer=null;
		}
	}
	private static String configuracao(Ambiente ambiente) {
		StringBuilder sb =  new StringBuilder();
		sb.append(Ambiente.VERSION +"=" + ambiente.getVersion()+"\n");
		sb.append(Ambiente.ENV_NAME +"=" + ambiente.getName()+"\n");
		sb.append(Ambiente.DB_DRIVER +"=" + ambiente.getDbDriver()+"\n");
		sb.append(Ambiente.DB_URL +"="+ ambiente.getDbUrl()  +"\n");
		sb.append(Ambiente.DB_USER +"="+ ambiente.getDbUser()  +"\n");
		sb.append(Ambiente.DB_PASS +"="+ ambiente.getDbPass()  +"\n");
		sb.append(Ambiente.DB_DIALECT +"=" + ambiente.getDbDialect()+"\n");
		sb.append(Ambiente.DB_DDL +"=" + ambiente.getDbDdl()+"\n");
		sb.append(Ambiente.DB_SHOWSQL +"=" + ambiente.getDbShowSql()+"\n");
		sb.append(Ambiente.STATUS +"=" + ambiente.getStatus()+"\n");
		return sb.toString();
	}
	private static Properties getConfiguracao() throws Exception {
		Properties config = new Properties();
		InputStream input = null;
		input = new FileInputStream(arquivoConfiguracao());
		config.load(input);
		return config;
	}


}
