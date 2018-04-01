package edu.cfip.app.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.model.Contato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ambiente.TipoOperacao;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmContato extends Formulario {
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtTelefone = new SSCampoTexto();
	private SSCampoTexto txtId = new SSCampoTexto();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Contato entidade;
	private TipoOperacao operacao;
	@Autowired
	private Repositorio dao;
	private JCheckBox chkNovo = new JCheckBox("Novo?");

	public FrmContato() {
		init();
	}
	
	private void init() {
		txtId.setComponenteNegrito(true);
		txtId.setComponenteCorFonte(Color.BLUE);
		txtId.setColunas(5);
		txtId.setEditavel(false);
		// HERANÇA
		super.setTitulo("Cadastro de Contato");
		super.setDescricao("Registro dos Clientes e Fornecedores");
		getRodape().add(chkNovo);
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);

		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.weightx = 1.0;
		gbc_txtNome.gridwidth = 2;
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.anchor = GridBagConstraints.WEST;
		gbc_txtNome.insets = new Insets(5, 5, 0, 5);
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 0;
		getConteudo().add(txtNome, gbc_txtNome);
		txtNome.setColunas(5);

		GridBagConstraints gbc_txtTelefone = new GridBagConstraints();
		gbc_txtTelefone.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtTelefone.weighty = 1.0;
		gbc_txtTelefone.insets = new Insets(5, 5, 0, 5);
		gbc_txtTelefone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefone.gridx = 0;
		gbc_txtTelefone.gridy = 1;
		getConteudo().add(txtTelefone, gbc_txtTelefone);

		txtNome.setRotulo("Nome");
		txtTelefone.setColunas(30);
		txtTelefone.setRotulo("Telefone");
		
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtId.weighty = 1.0;
		gbc_txtId.insets = new Insets(5, 5, 0, 5);
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtId.gridx = 1;
		gbc_txtId.gridy = 1;
		txtId.setRotulo("ID");
		getConteudo().add(txtId, gbc_txtId);
		
		
		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");

		// Listners = Comandos = Eventos
		cmdSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvar();
			}
		});
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		
	}
	public void setEntidade(Contato entidade) {
		this.entidade = entidade;
		if (entidade != null)
			operacao = TipoOperacao.ALTERACAO;
		else
			criar();
		
		atribuir();
	}
	private void atribuir() {
		try {
			txtNome.setValue(entidade.getNome());
			txtId.setValue(entidade.getId());
			txtTelefone.setText(entidade.getTelefone());
			txtNome.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void criar() {
		operacao = TipoOperacao.INCLUSAO;
		entidade = new Contato();
		atribuir();
	}
	private void salvar() {
		try {
			if (entidade == null) {
				entidade = new Contato();
			}
			entidade.setNome(txtNome.getText());
			entidade.setTelefone(txtTelefone.getText());
			entidade.setUsuario(getUsuarioId());
			
			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getTelefone() == null
					|| entidade.getTelefone().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			dao.gravar(operacao, entidade);
			SSMensagem.informa("Contato registrado com sucesso!!");
			novo();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void novo() {
		if(chkNovo.isSelected()) {
			criar();
		}else
			super.fechar();
	}
	private void sair() {
		super.fechar();
	}
	public void load(Object param) {}
}
	