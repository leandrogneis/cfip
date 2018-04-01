package edu.cfip.app.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.dao.springjpa.RepositorioLancamento;
import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.Natureza;
import edu.cfip.core.model.TipoMovimento;
import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmLancamentoTransferencia extends Formulario {
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private SSCampoTexto txtDescricao = new SSCampoTexto();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Lancamento entidade;

	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento daoLancto;

	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboDestino = new SSCaixaCombinacao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");

	public FrmLancamentoTransferencia() {
		init();
	}

	private void init() {
		// HERANÇA
		super.setTitulo("Transferência");
		super.setDescricao("Transferências entre contas");
		getRodape().add(chkNovo);
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtData.anchor = GridBagConstraints.WEST;
		gbc_txtData.insets = new Insets(5, 5, 0, 5);
		gbc_txtData.gridx = 0;
		gbc_txtData.gridy = 0;
		txtData.setColunas(10);
		panelCampos.add(txtData, gbc_txtData);

		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.insets = new Insets(5, 5, 0, 5);
		gbc_cboConta.fill = GridBagConstraints.BOTH;
		gbc_cboConta.gridx = 0;
		gbc_cboConta.gridy = 1;
		cboConta.setRotulo("Conta");
		panelCampos.add(cboConta, gbc_cboConta);

		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.insets = new Insets(5, 5, 0, 5);
		gbc_cboNatureza.fill = GridBagConstraints.BOTH;
		gbc_cboNatureza.gridx = 0;
		gbc_cboNatureza.gridy = 2;
		cboNatureza.setRotulo("Natureza");
		panelCampos.add(cboNatureza, gbc_cboNatureza);

		GridBagConstraints gbc_cboDestino = new GridBagConstraints();
		gbc_cboDestino.insets = new Insets(5, 5, 0, 5);
		gbc_cboDestino.fill = GridBagConstraints.BOTH;
		gbc_cboDestino.gridx = 0;
		gbc_cboDestino.gridy = 3;
		cboDestino.setRotulo("Destino");
		panelCampos.add(cboDestino, gbc_cboDestino);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.weightx = 2.0;
		gbc_txtValor.insets = new Insets(5, 5, 0, 5);
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValor.gridx = 0;
		gbc_txtValor.gridy = 4;
		txtValor.setComponenteCorFonte(Color.RED);
		txtValor.setComponenteNegrito(true);
		panelCampos.add(txtValor, gbc_txtValor);

		txtData.setRotulo("Data Registro");
		txtValor.setColunas(10);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.weighty = 1.0;
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 5;
		txtDescricao.setColunas(15);
		txtDescricao.setRotulo("Descrição");
		panelCampos.add(txtDescricao, gbc_txtDescricao);

		cmdSair.setText("Cancelar");

		cmdSalvar.setText("Confirmar");
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
		inicializa();

	}

	private void salvar() {
		try {
			entidade = new Lancamento();
			entidade.setValor(txtValor.getDouble());
			entidade.setDescricao(txtDescricao.getText());
			Conta conta = (Conta) cboConta.getValue();
			Conta destino = (Conta) cboDestino.getValue();
			Natureza natureza = (Natureza) cboNatureza.getValue();
			entidade.setConta(conta);
			if (destino != null)
				entidade.setDestino(destino);

			entidade.setData(txtData.getDataHora());
			entidade.setNatureza(natureza);
			entidade.setTipoMovimento(natureza.getTipoMovimento());
			entidade.setUsuario(MDI.getPerfilId());

			if (entidade.getConta() == null || entidade.getNatureza() == null || entidade.getData() == null
					|| entidade.getValor() == null || entidade.getDescricao() == null
					|| entidade.getDescricao().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if (entidade.getTipoMovimento() == TipoMovimento.TRANSFERENCIA && destino == null) {
				SSMensagem.avisa("Dados incompletos");

				return;
			}
			if (entidade.getConta() == entidade.getDestino()) {
				SSMensagem.avisa("Origem e Destino são iguais");
				return;
			}
			daoLancto.incluirLancamento(entidade);
			SSMensagem.informa("Lançamento registrado com sucesso!!");
			novo();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void novo() {
		if (chkNovo.isSelected()) {
			inicializa();
		} else
			super.fechar();
	}

	private void inicializa() {
		entidade = new Lancamento();
		txtData.requestFocus();
		txtData.setValue(new Date());
		txtValor.setValue(0.0d);
		txtData.setDataHora(new Date());
		txtDescricao.setText("");
	}

	private void sair() {
		super.fechar();
	}

	public void load() {
		List<Conta> contas = dao.listarContas(MDI.getPerfilId());
		cboConta.setItens(contas, "nome");
		cboDestino.setItens(contas, "nome");
		cboNatureza.setItens(dao.listarNaturezas(MDI.getPerfilId(), TipoMovimento.TRANSFERENCIA), "nomeSigla");

	}
}
