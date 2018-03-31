package edu.porgamdor.util.desktop.ambiente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import edu.porgamdor.util.desktop.DesktopApp;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCabecalho;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoSenha;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Imagem;
public class FrmConfiguracao extends JFrame {
	private JPanel content = new JPanel();
	private SSBotao cmdConfirmar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	
	private JPanel form = new JPanel();
	private SSCaixaCombinacao cboAmbiente = new SSCaixaCombinacao();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Ambiente ambiente;
	
	private JPanel pnlServer = new JPanel();
	private SSCampoTexto txtDbLogin = new SSCampoTexto();
	private SSCampoSenha txtDbSenha = new SSCampoSenha();
	private SSCampoTexto txtUrl = new SSCampoTexto();
	private SSCampoSenha txtDbRepeteSenha = new SSCampoSenha();
	public FrmConfiguracao() {
		init();
	}
	private void init() {
		txtDbLogin.setTudoMaiusculo(false);
		txtDbSenha.setTudoMaiusculo(false);
		txtDbRepeteSenha.setTudoMaiusculo(false);
		txtUrl.setTudoMaiusculo(false);
		
		setTitle("CFIP");
		this.setIconImage(Imagem.png("cfip", "janela").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(270, 380));
		setLocationRelativeTo(null);
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.setLayout(new BorderLayout(0, 0));
		setContentPane(content);

		SSCabecalho cabecalho = new SSCabecalho();
		cabecalho.setDescricao("Configuração inicial do ambiente");
		cabecalho.setTitulo("CFIP - CONFIGURAÇÃO");
		content.add(cabecalho, BorderLayout.NORTH);

		JPanel botoes = new JPanel();
		botoes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_botoes = (FlowLayout) botoes.getLayout();
		fl_botoes.setAlignment(FlowLayout.RIGHT);
		content.add(botoes, BorderLayout.SOUTH);
		cmdConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmar();
			}
		});

		cmdConfirmar.setText("Confirmar");
		cmdConfirmar.setIcone("ok");

		botoes.add(cmdConfirmar);
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();

			}
		});

		cmdSair.setText("Sair");
		cmdSair.setIcone("fechar");
		botoes.add(cmdSair);
		form.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		content.add(form, BorderLayout.CENTER);
		GridBagLayout gbl_form = new GridBagLayout();
		form.setLayout(gbl_form);

		GridBagConstraints gbc_cboAmbiente = new GridBagConstraints();
		gbc_cboAmbiente.insets = new Insets(5, 5, 0, 5);
		gbc_cboAmbiente.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboAmbiente.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboAmbiente.gridx = 0;
		gbc_cboAmbiente.gridy = 0;
		cboAmbiente.setRotulo("Ambiente");
		form.add(cboAmbiente, gbc_cboAmbiente);

		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(5, 0, 5, 0);
		gbc_tabbedPane.weightx = 1.0;
		gbc_tabbedPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_tabbedPane.weighty = 1.0;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		form.add(tabbedPane, gbc_tabbedPane);
		pnlServer.setLayout(new GridBagLayout());
		GridBagConstraints gbc_txtUrl = new GridBagConstraints();
		gbc_txtUrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUrl.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtUrl.insets = new Insets(0, 5, 0, 5);
		gbc_txtUrl.gridx = 0;
		gbc_txtUrl.gridy = 0;
		
		txtUrl.setRotulo("URL");
		pnlServer.add(txtUrl, gbc_txtUrl);

		GridBagConstraints gbc_txtDbLogin = new GridBagConstraints();
		gbc_txtDbLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbLogin.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDbLogin.insets = new Insets(5, 5, 0, 5);
		gbc_txtDbLogin.gridx = 0;
		gbc_txtDbLogin.gridy = 1;
		
		txtDbLogin.setRotulo("Usuário do banco de dados");
		pnlServer.add(txtDbLogin, gbc_txtDbLogin);

		GridBagConstraints gbc_txtDbSenha = new GridBagConstraints();
		gbc_txtDbSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbSenha.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDbSenha.insets = new Insets(5, 5, 0, 5);
		gbc_txtDbSenha.gridx = 0;
		gbc_txtDbSenha.gridy = 2;
		
		txtDbSenha.setTudoMaiusculo(false);
		
		txtDbSenha.setRotulo("Senha do banco de dados");
		txtDbSenha.setColunas(10);
		pnlServer.add(txtDbSenha, gbc_txtDbSenha);

		GridBagConstraints gbc_txtDbRepeteSenha = new GridBagConstraints();
		gbc_txtDbRepeteSenha.weighty = 1.0;
		gbc_txtDbRepeteSenha.weightx = 1.0;
		gbc_txtDbRepeteSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbRepeteSenha.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtDbRepeteSenha.insets = new Insets(5, 5, 0, 5);
		gbc_txtDbRepeteSenha.gridx = 0;
		gbc_txtDbRepeteSenha.gridy = 3;
		
		txtDbRepeteSenha.setTudoMaiusculo(false);
		
		txtDbRepeteSenha.setRotulo("Confirma Senha");
		pnlServer.add(txtDbRepeteSenha, gbc_txtDbRepeteSenha);
		tabbedPane.addTab("BANCO", null, pnlServer, null);
		
		cboAmbiente.setItens(Ambiente.ambientes, "nome");
		cboAmbiente.setValue(Ambiente.LOCAL);
		cboAmbiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				definirAmbiente();
			}
		});
		
	}
	private void definirAmbiente() {
		ambiente = (Ambiente) cboAmbiente.getValue();
		if (ambiente != null) {
			txtUrl.setText(ambiente.getDbUrl());
			txtDbLogin.setText(ambiente.getDbUser());
			txtDbSenha.setText(ambiente.getDbPass());
			txtDbRepeteSenha.setText(ambiente.getDbPass());
		}
	}
	private void confirmar() {
		try {
			if(txtUrl.getText()==null || txtUrl.getText().trim().isEmpty()){
				SSMensagem.avisa("Informe uma URL");
				return ;
			}
			if(txtDbLogin.getText()==null || txtDbLogin.getText().trim().isEmpty()){
				SSMensagem.avisa("Informe o usuário do banco de dados");
				return ;
			}
			if(txtDbSenha.getText()==null || txtDbSenha.getText().trim().isEmpty()){
				SSMensagem.avisa("Informe a senha do banco de dados");
				return ;
			}
			if(!txtDbSenha.getText().equals(txtDbRepeteSenha.getSenha())){
				SSMensagem.avisa("Senhas não conferem");
				return; 
			}
			if(!SSMensagem.pergunta("Concluir a configuração atual")){
				return;
			}
			
			ambiente.setDbUrl(txtUrl.getText());
			ambiente.setDbUser(txtDbLogin.getText());
			ambiente.setDbPass(txtDbSenha.getText());
			ambiente.setStatus(Ambiente.OK);
			DesktopApp.configurarAmbiente(ambiente);
			SSMensagem.informa("Acesse o sistema com as novas configurações");
			fechar();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void fechar() {
		System.exit(0);
	}
}
