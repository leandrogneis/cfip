package edu.cfip.client;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;

public class FrmConta extends Formulario {
	// inputs
	private SSCampoNumero txtId = new SSCampoNumero();
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtSigla = new SSCampoTexto();
	private SSCampoNumero txtSaldo = new SSCampoNumero();

	// bototes
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdSalvar = new SSBotao();

	public FrmConta() {
		init();
	}

	private void init() {
		// CABECALHO
		setTitulo("Formulario Conta");
		setDescricao("Cadastro das contas do sistema");

		// PROPRIEDADES
		txtId.setRotulo("Código");
		txtNome.setRotulo("Nome");
		txtSigla.setRotulo("Sigla");
		txtSaldo.setRotulo("Saldo");

		cmdSalvar.setText("Salvar");
		cmdFechar.setText("Fechar");
		txtSaldo.setFormato(Formato.MOEDA);

		//
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtId.insets = new Insets(5, 5, 0, 5);
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtId.gridx = 0;
		gbc_txtId.gridy = 0;
		getConteudo().add(txtId, gbc_txtId);

		//
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.insets = new Insets(5, 5, 0, 5);
		gbc_txtNome.fill = GridBagConstraints.BOTH;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 1;
		getConteudo().add(txtNome, gbc_txtNome);

		//
		GridBagConstraints gbc_txtSigla = new GridBagConstraints();
		gbc_txtSigla.insets = new Insets(5, 5, 0, 5);
		gbc_txtSigla.fill = GridBagConstraints.BOTH;
		gbc_txtSigla.gridx = 0;
		gbc_txtSigla.gridy = 2;
		getConteudo().add(txtSigla, gbc_txtSigla);

		//
		GridBagConstraints gbc_txtSaldo = new GridBagConstraints();
		gbc_txtSaldo.weightx = 1.0;
		gbc_txtSaldo.insets = new Insets(5, 5, 5, 5);
		gbc_txtSaldo.fill = GridBagConstraints.BOTH;
		gbc_txtSaldo.gridx = 0;
		gbc_txtSaldo.gridy = 3;
		getConteudo().add(txtSaldo, gbc_txtSaldo);

		// rodape
		getRodape().add(cmdSalvar);
		getRodape().add(cmdFechar);
		// métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
	}
	private void sair() {
		super.cancelar();
	}
}
