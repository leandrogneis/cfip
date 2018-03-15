package client.desktop.app;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.boxs.cfip.core.dao.Entidades;
import com.boxs.cfip.core.model.Conta;
import com.boxs.cfip.core.model.Contato;
import com.boxs.cfip.core.util.Formato;
import com.boxs.cfip.core.util.TipoOperacao;

import client.desktop.util.Formulario;
import client.ss.desktop.Mensagem;
import client.ss.desktop.SSBotao;
import client.ss.desktop.SSCampoNumero;
import client.ss.desktop.SSCampoTexto;
import client.ss.infraestrutura.util.Validacao;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmConta extends Formulario {
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtSigla = new SSCampoTexto();
	private SSCampoNumero txtSaldoInicial = new SSCampoNumero();
	private SSCampoNumero txtSaldo = new SSCampoNumero();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Conta entidade;
	private TipoOperacao operacao;
	@Autowired
	private Entidades dao;
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	public FrmConta() {
		init();
	}
	private void init() {
		// HERANÇA
		super.setTitulo("Cadastro de Contas");
		super.setDescricao("Registro das contas no sistema");
		super.addComponente(chkNovo);
		super.addBotaoRodape(cmdSalvar);
		super.addBotaoRodape(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudoGrid();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		
		GridBagConstraints gbcTxtNome = new GridBagConstraints();
		gbcTxtNome.weightx = 2.0;
		gbcTxtNome.insets = new Insets(5, 5, 0, 5);
		gbcTxtNome.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtNome.gridx = 0;
		gbcTxtNome.gridy = 0;
		panelCampos.add(txtNome, gbcTxtNome);

		txtNome.setColunas(30);
		txtNome.setRotulo("Nome");

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.BOTH;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 1;
		txtSigla.setRotulo("Sigla");
		panelCampos.add(txtSigla, gbc_txtDescricao);

		GridBagConstraints gbc_saldoInicial = new GridBagConstraints();
		gbc_saldoInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_saldoInicial.insets = new Insets(5, 5, 0, 5);
		gbc_saldoInicial.fill = GridBagConstraints.BOTH;
		gbc_saldoInicial.gridx = 0;
		gbc_saldoInicial.gridy = 2;
		txtSaldoInicial.setEditavel(false);
		txtSaldoInicial.setForeground(Color.BLUE);
		txtSaldoInicial.setRotulo("Saldo Inicial");
		panelCampos.add(txtSaldoInicial, gbc_saldoInicial);

		GridBagConstraints gbc_saldo = new GridBagConstraints();
		gbc_saldo.weighty = 1.0;
		gbc_saldo.anchor = GridBagConstraints.NORTHWEST;
		gbc_saldo.insets = new Insets(5, 5, 5, 5);
		gbc_saldo.fill = GridBagConstraints.HORIZONTAL;
		gbc_saldo.gridx = 0;
		gbc_saldo.gridy = 3;
		txtSaldo.setEditavel(false);
		txtSaldo.setForeground(Color.BLUE);
		txtSaldo.setRotulo("Saldo Atual");
		panelCampos.add(txtSaldo, gbc_saldo);

		txtSaldo.setFormato(Formato.MOEDA);
		txtSaldoInicial.setFormato(Formato.MOEDA);

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

	public void setEntidade(Conta entidade) {
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
			txtSigla.setText(entidade.getSigla());
			txtSaldoInicial.setValue(entidade.getSaldoInicial().getValor());
			txtSaldo.setValue(entidade.getSaldo());
			txtNome.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void criar() {
		operacao = TipoOperacao.INCLUSAO;
		entidade = new Conta();
		atribuir();
	}
	private void salvar() {
		try {
			if (entidade == null) {
				entidade = new Conta();
			}
			entidade.setNome(txtNome.getText());
			entidade.setSigla(txtSigla.getText());
			entidade.setUsuario(getUsuarioId());

			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getSigla() == null
					|| entidade.getSigla().isEmpty()) {
				Mensagem.avisa("Dados incompletos");
				return;
			}
			dao.gravar(operacao, entidade);
			Mensagem.informa("Conta registrado com sucesso!!");
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
			super.retornar();
	}
	private void sair() {
		super.fechar();
	}
	public void load(Object param) {
	}
}
