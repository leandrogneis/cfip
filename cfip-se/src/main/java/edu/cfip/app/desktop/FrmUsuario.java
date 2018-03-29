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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.springjpa.UsuarioRepositorio;
import edu.cfip.core.model.Usuario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCabecalho;
import edu.porgamdor.util.desktop.ss.SSCampoMascara;
import edu.porgamdor.util.desktop.ss.SSCampoSenha;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Imagem;
import edu.porgamdor.util.desktop.ss.util.Texto;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmUsuario extends JFrame {
	private SSBotao cmdConfirmar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private SSCampoTexto txtEmail = new SSCampoTexto();
	private SSCampoTexto txtTelefone = new SSCampoTexto();
	private SSCampoTexto txtLogin = new SSCampoTexto();
	private SSCampoSenha txtSenha = new SSCampoSenha();
	private SSCampoMascara txtCpfCnpj = new SSCampoMascara();
	private final SSCampoTexto txtNome = new SSCampoTexto();
	private final SSCampoSenha txtRepeteSenha = new SSCampoSenha();
	private Usuario entidade;
	@Autowired
	private UsuarioRepositorio dao;

	public FrmUsuario() {
		init();
	}

	public void setUsuario(Usuario entidade) {
		this.entidade = entidade;
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
		conteudo.add(txtCpfCnpj, gbc_txtCpf);
		txtCpfCnpj.setRotulo("CPF\\CNPJ");

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
		txtEmail.setTudoMaiusculo(false);
		txtSenha.setTudoMaiusculo(false);
		txtRepeteSenha.setTudoMaiusculo(false);

	}

	private void exibir() {
		txtLogin.setText(entidade.getLogin());
		txtEmail.setText(entidade.getEmail());
		txtNome.setText(entidade.getNome());
		txtTelefone.setText(entidade.getTelefone());
		txtCpfCnpj.setText(entidade.getCpfCnpj());
		if (entidade.getId() == null) {
			txtSenha.setText(entidade.getSenha());
			txtRepeteSenha.setText(entidade.getSenha());
		}
	}

	private void confirmar() {
		try {
			// entidade = new Usuario();
			if (txtLogin.getText() == null || txtLogin.getText().trim().isEmpty()) {
				SSMensagem.avisa("Informe o Login");
				return;
			}
			if (txtSenha.getText() == null || txtSenha.getText().trim().isEmpty()) {
				SSMensagem.avisa("Informe a senha");
				return;
			}
			if (!txtSenha.getSenha().equals(txtRepeteSenha.getSenha())) {
				SSMensagem.avisa("Senhas não conferem");
				return;
			}
			entidade.setEmail(txtEmail.getText());
			entidade.setNome(txtNome.getText());
			//criptogragia
			entidade.setSenha(Texto.md5(txtSenha.getText()));
			entidade.setLogin(txtLogin.getText());
			entidade.setCpfCnpj(txtCpfCnpj.getClipText());
			entidade.setTelefone(txtTelefone.getText());

			if (entidade.getId() == null) {
				boolean cadastrar = true;
				// isso porque estamos usando SpringDataJPA
				if (dao.findFistByLogin(entidade.getLogin()) != null) {
					cadastrar = false;
				}
				if (dao.findFistByEmail(entidade.getEmail()) != null) {
					cadastrar = false;
				}
				if (cadastrar) {
					dao.save(entidade);
					SSMensagem.informa("Usuario registrado com sucesso\nAcesse o sistema");
				} else
					SSMensagem.avisa(
							"O usuário " + entidade.getLogin() + " ou " + entidade.getEmail() + " Já está cadastrado");

			} else {
				dao.save(entidade);
				SSMensagem.informa("Dados alterados com sucesso");
			}
			fechar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void fechar() {
		this.dispose();
	}
}
