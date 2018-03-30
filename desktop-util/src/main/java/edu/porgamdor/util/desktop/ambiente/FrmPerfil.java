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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCabecalho;
import edu.porgamdor.util.desktop.ss.SSCampoMascara;
import edu.porgamdor.util.desktop.ss.SSCampoSenha;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Imagem;
import edu.porgamdor.util.desktop.ss.util.Texto;

public class FrmPerfil extends JFrame {
	private SSBotao cmdConfirmar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private SSCampoTexto txtEmail = new SSCampoTexto();
	private SSCampoTexto txtTelefone = new SSCampoTexto();
	private SSCampoTexto txtLogin = new SSCampoTexto();
	private SSCampoSenha txtSenha = new SSCampoSenha();
	private SSCampoMascara txtCpf = new SSCampoMascara();
	private final SSCampoTexto txtNome = new SSCampoTexto();
	private final SSCampoSenha txtRepeteSenha = new SSCampoSenha();
	protected Perfil perfil;

	public FrmPerfil() {
		init();
	}

	public void setPerfil(Perfil entidade) {
		this.perfil = entidade;
		exibir();
	}

	private void init() {
		this.setIconImage(Imagem.png("cfip", "janela").getImage());
		setTitle("CFIP");
		setSize(new Dimension(259, 400));
		setLocationRelativeTo(null);
		txtSenha.setTudoMaiusculo(false);
		txtLogin.setTudoMaiusculo(false);
		txtLogin.setText("login");
		txtLogin.setRotulo("Login");
		JPanel login = new JPanel();
		login.setBorder(new EmptyBorder(5, 5, 5, 5));
		login.setLayout(new BorderLayout(0, 0));
		setContentPane(login);

		SSCabecalho cabecalho = new SSCabecalho();
		cabecalho.setDescricao("Registro de usuários no sistema");
		cabecalho.setTitulo("CFIP - USUÁRIO");
		login.add(cabecalho, BorderLayout.NORTH);

		JPanel conteudo = new JPanel();
		conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		login.add(conteudo, BorderLayout.CENTER);
		GridBagLayout gbl_conteudo = new GridBagLayout();

		conteudo.setLayout(gbl_conteudo);
		txtEmail.setColunas(10);

		GridBagConstraints gbc_txtLogin = new GridBagConstraints();
		gbc_txtLogin.gridwidth = 2;
		gbc_txtLogin.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtLogin.insets = new Insets(5, 5, 0, 5);
		gbc_txtLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLogin.gridx = 0;
		gbc_txtLogin.gridy = 2;
		conteudo.add(txtLogin, gbc_txtLogin);

		txtEmail.setRotulo("E-mail");
		txtEmail.setTudoMaiusculo(false);
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.gridwidth = 2;
		gbc_txtUsuario.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtUsuario.insets = new Insets(5, 5, 0, 5);
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsuario.gridx = 0;
		gbc_txtUsuario.gridy = 1;
		conteudo.add(txtEmail, gbc_txtUsuario);

		txtSenha.setRotulo("Senha (1234)");

		GridBagConstraints gbc_txtSenha = new GridBagConstraints();
		gbc_txtSenha.gridwidth = 2;
		gbc_txtSenha.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtSenha.insets = new Insets(5, 5, 0, 5);
		gbc_txtSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSenha.gridx = 0;
		gbc_txtSenha.gridy = 3;
		conteudo.add(txtSenha, gbc_txtSenha);

		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.gridwidth = 2;
		gbc_txtNome.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtNome.insets = new Insets(5, 5, 0, 5);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 0;
		txtNome.setRotulo("Nome");
		conteudo.add(txtNome, gbc_txtNome);

		GridBagConstraints gbc_txtRepeteSenha = new GridBagConstraints();
		gbc_txtRepeteSenha.gridwidth = 2;
		gbc_txtRepeteSenha.weightx = 1.0;
		gbc_txtRepeteSenha.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtRepeteSenha.insets = new Insets(5, 5, 5, 5);
		gbc_txtRepeteSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepeteSenha.gridx = 0;
		gbc_txtRepeteSenha.gridy = 4;
		txtRepeteSenha.setRotulo("Repete Senha");
		conteudo.add(txtRepeteSenha, gbc_txtRepeteSenha);

		GridBagConstraints gbc_txtCpf = new GridBagConstraints();
		gbc_txtCpf.weightx = 1.0;
		gbc_txtCpf.weighty = 1.0;
		gbc_txtCpf.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtCpf.insets = new Insets(5, 5, 5, 5);
		gbc_txtCpf.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCpf.gridx = 0;
		gbc_txtCpf.gridy = 5;
		conteudo.add(txtCpf, gbc_txtCpf);
		txtCpf.setRotulo("CPF");

		GridBagConstraints gbc_txtTel = new GridBagConstraints();
		gbc_txtTel.weightx = 1.0;
		gbc_txtTel.weighty = 1.0;
		gbc_txtTel.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtTel.insets = new Insets(5, 5, 5, 5);
		gbc_txtTel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTel.gridx = 1;
		gbc_txtTel.gridy = 5;
		conteudo.add(txtTelefone, gbc_txtTel);
		txtTelefone.setRotulo("Telefone");

		JPanel botoes = new JPanel();
		botoes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_botoes = (FlowLayout) botoes.getLayout();
		fl_botoes.setAlignment(FlowLayout.RIGHT);
		login.add(botoes, BorderLayout.SOUTH);

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
		txtEmail.setTudoMaiusculo(false);
		txtSenha.setTudoMaiusculo(false);
		txtRepeteSenha.setTudoMaiusculo(false);

	}

	public void confirmar(ActionListener listener) {
		cmdConfirmar.addActionListener(listener);
		fechar();
	}

	public void validarFormulario() throws Exception {
		if (perfil != null) {
			if (txtLogin.getText() == null || txtLogin.getText().trim().isEmpty()) {
				throw new LoginException("Informe o Login");
			}
			if (txtSenha.getText() == null || txtSenha.getText().trim().isEmpty()) {
				throw new LoginException("Informe a senha");
			}
			if (!txtSenha.getSenha().equals(txtRepeteSenha.getSenha())) {
				throw new LoginException("Senhas não conferem");
			}
			perfil.setEmail(txtEmail.getText());
			perfil.setNome(txtNome.getText());
			// criptogragia
			perfil.setSenha(Texto.md5(txtSenha.getText()));
			perfil.setLogin(txtLogin.getText());
			perfil.setCpf(txtCpf.getClipText());
			perfil.setTelefone(txtTelefone.getText());
		}
	}

	private void exibir() {
		txtLogin.setText(perfil.getLogin());
		txtEmail.setText(perfil.getEmail());
		txtNome.setText(perfil.getNome());
		txtTelefone.setText(perfil.getTelefone());
		txtCpf.setText(perfil.getCpf());
		if (perfil.getId() == null) {
			txtSenha.setText(perfil.getSenha());
			txtRepeteSenha.setText(perfil.getSenha());
		}
	}

	private void fechar() {
		this.dispose();
	}
	public void prosseguir() {
		SSMensagem.informa("Usuario registrado com sucesso\nAcesse o sistema");
		fechar();
	}
}
