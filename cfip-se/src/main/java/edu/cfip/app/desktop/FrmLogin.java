package edu.cfip.app.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.app.spring.DesktopApp;
import edu.cfip.core.dao.springjpa.UsuarioRepositorio;
import edu.cfip.core.model.Usuario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCabecalho;
import edu.porgamdor.util.desktop.ss.SSCampoSenha;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Texto;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmLogin extends JFrame {
	@Autowired
	private UsuarioRepositorio repositorio;
	
	private JPanel login = new JPanel();
	private SSBotao btOk = new SSBotao();
	private SSBotao btSair = new SSBotao();
	private SSCampoTexto txtUsuario = new SSCampoTexto();
	private SSCampoSenha txtSenha = new SSCampoSenha();
	public FrmLogin() {
		//this.setIconImage(Imagem.png("cfip", "janela").getImage());
		setTitle("CFIP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(259, 261));
		setLocationRelativeTo(null);
		
		login.setBorder(new EmptyBorder(5, 5, 5, 5));
		login.setLayout(new BorderLayout(0, 0));
		setContentPane(login);
		
		SSCabecalho cabecalho = new SSCabecalho();
		cabecalho.setTitulo("GESTOR - LOGIN");
		cabecalho.setDescricao("Acesse o sistema");
		
		login.add(cabecalho, BorderLayout.NORTH);
		
		JPanel conteudo = new JPanel();
		conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		login.add(conteudo, BorderLayout.CENTER);
		GridBagLayout gbl_conteudo = new GridBagLayout();
		conteudo.setLayout(gbl_conteudo);
		txtUsuario.setColunas(10);
		
		
		txtUsuario.setRotulo("Usuário");
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.weightx = 1.0;
		gbc_txtUsuario.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtUsuario.insets = new Insets(5, 5, 0, 5);
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsuario.gridx = 0;
		gbc_txtUsuario.gridy = 0;
		conteudo.add(txtUsuario, gbc_txtUsuario);
		
		txtSenha.setRotulo("Senha");
		GridBagConstraints gbc_txtSenha = new GridBagConstraints();
		gbc_txtSenha.weighty = 1.0;
		gbc_txtSenha.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtSenha.insets = new Insets(5, 5, 0, 5);
		gbc_txtSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSenha.gridx = 0;
		gbc_txtSenha.gridy = 1;
		conteudo.add(txtSenha, gbc_txtSenha);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		login.add(panel_1, BorderLayout.SOUTH);
		btOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		
		
		btOk.setText("LOGIN");
		btOk.setIcone("login");
		
		panel_1.add(btOk);
		btSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
				
			}
		});
		
		btSair.setText("SAIR");
		btSair.setIcone("fechar");
		panel_1.add(btSair);
		txtUsuario.setTudoMaiusculo(false);
		txtSenha.setTudoMaiusculo(false);
		//txtUsuario.setText("login");
		//txtSenha.setText("1234");	
	}
	private void login(){
		try{
			String login=txtUsuario.getText();
			String senha=txtSenha.getText();
			Usuario usuario =repositorio.login(login, senha);
			if(usuario!=null) {
				this.dispose();
				DesktopApp.iniciarAplicacao(usuario);
			}else
				SSMensagem.avisa("Usuário ou Senha Inválida");
		}catch (Exception e) {
			SSMensagem.avisa(e.getMessage());
		}
		
	}
	private void fechar(){
		System.exit(0);
	}
}
