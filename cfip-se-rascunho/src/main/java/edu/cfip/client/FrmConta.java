package edu.cfip.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;

public class FrmConta extends Formulario {
	private SSCampoTexto txtNome;
	private SSCampoNumero txtId = new SSCampoNumero();
	private SSCampoTexto txtSigla = new SSCampoTexto();
	private SSCampoNumero txtSaldo = new SSCampoNumero();
	private SSBotao btCancelar = new SSBotao();
	private SSBotao btSalvar = new SSBotao();
	public FrmConta() {
		setTitulo("Formulario Conta");
		setDescricao("Cadastro das contas do sistema");
		GridBagLayout gridBagLayout = (GridBagLayout) getConteudo().getLayout();
		txtId.setRotulo("Código");
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtId.insets = new Insets(5, 5, 0, 5);
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtId.gridx = 0;
		gbc_txtId.gridy = 0;
		getConteudo().add(txtId, gbc_txtId);
		
		txtNome = new SSCampoTexto();
		txtNome.setRotulo("Nome");
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.insets = new Insets(5, 5, 0, 5);
		gbc_txtNome.fill = GridBagConstraints.BOTH;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 1;
		getConteudo().add(txtNome, gbc_txtNome);
		
		
		txtSigla.setRotulo("Sigla");
		GridBagConstraints gbc_txtSigla = new GridBagConstraints();
		gbc_txtSigla.insets = new Insets(5, 5, 0, 5);
		gbc_txtSigla.fill = GridBagConstraints.BOTH;
		gbc_txtSigla.gridx = 0;
		gbc_txtSigla.gridy = 2;
		getConteudo().add(txtSigla, gbc_txtSigla);
		
		
		txtSaldo.setRotulo("Saldo");
		GridBagConstraints gbc_txtSaldo = new GridBagConstraints();
		gbc_txtSaldo.weightx = 1.0;
		gbc_txtSaldo.insets = new Insets(5, 5, 5, 5);
		gbc_txtSaldo.fill = GridBagConstraints.BOTH;
		gbc_txtSaldo.gridx = 0;
		gbc_txtSaldo.gridy = 3;
		getConteudo().add(txtSaldo, gbc_txtSaldo);
		
		
		btSalvar.setText("Salvar");
		getRodape().add(btSalvar);
		
		
		btCancelar.setText("Fechar");
		getRodape().add(btCancelar);
		init();
	}
	private void init() {
		
	}
}
