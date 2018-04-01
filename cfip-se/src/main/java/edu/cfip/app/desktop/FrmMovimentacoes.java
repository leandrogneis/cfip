package edu.cfip.app.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.dao.RepositorioLancamento;
import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.Natureza;
import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ambiente.Total;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmMovimentacoes extends Formulario {
	// rodape
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdBuscar = new SSBotao();
	// DAOs - NAO OFICIAL
	@Autowired
	private Repositorio dao;
	
	@Autowired
	private RepositorioLancamento lactoDao;

	private SSCampoDataHora txtDataDe = new SSCampoDataHora();
	private SSCampoDataHora txtDataAte = new SSCampoDataHora();
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	//
	private Total totalLancto = new Total();
	private SSGrade gridLancamento = new SSGrade();
	private JScrollPane scrollLancto = new JScrollPane();
	private SSCampoNumero txtLDespesas = new SSCampoNumero();
	private SSCampoNumero txtLReceitas = new SSCampoNumero();
	private SSCampoNumero txtLSaldoAtual = new SSCampoNumero();
	private JLabel lblDescLancto = new JLabel();
	//
	//
	private Total totalPrevisao = new Total();
	private SSGrade gridPrevisao = new SSGrade();
	private JScrollPane scrollPrevisao = new JScrollPane();
	private SSCampoNumero txtPDespesas = new SSCampoNumero();
	private SSCampoNumero txtPReceitas = new SSCampoNumero();
	private SSCampoNumero txtPSaldoAtual = new SSCampoNumero();
	private JLabel lblDescPrevisao = new JLabel();
	//

	public FrmMovimentacoes() {
		init();
	}

	private void init() {
		cboConta.setPreferredWidth(180);
		cboNatureza.setPreferredWidth(150);
		super.setTitulo("Movimentações");
		super.setDescricao("Listagem dos lançamentos e previsões registrados no sistema");
		getRodape().add(cmdFechar);
		scrollLancto.setPreferredSize(new Dimension(0, 100));
		scrollPrevisao.setPreferredSize(new Dimension(0, 100));
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());
		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		painelFiltro.setLayout(new GridBagLayout());
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.anchor = GridBagConstraints.NORTHWEST;
		gbcBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcBuscar.insets = new Insets(15, 5, 5, 5);
		gbcBuscar.gridx = 4;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);
		GridBagConstraints gbc_txtDataDe = new GridBagConstraints();
		gbc_txtDataDe.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataDe.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataDe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataDe.gridx = 0;
		gbc_txtDataDe.gridy = 0;
		txtDataDe.setColunas(8);
		txtDataDe.setRotulo("De");
		painelFiltro.add(txtDataDe, gbc_txtDataDe);

		GridBagConstraints gbc_txtDataAte = new GridBagConstraints();
		gbc_txtDataAte.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataAte.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataAte.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataAte.gridx = 1;
		gbc_txtDataAte.gridy = 0;
		txtDataAte.setColunas(8);
		txtDataAte.setRotulo("Até");
		painelFiltro.add(txtDataAte, gbc_txtDataAte);

		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.weightx = 1.0;
		gbc_cboConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboConta.insets = new Insets(5, 5, 5, 0);
		gbc_cboConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboConta.gridx = 2;
		gbc_cboConta.gridy = 0;
		cboConta.setRotulo("Conta");
		painelFiltro.add(cboConta, gbc_cboConta);

		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboNatureza.weightx = 1.0;
		gbc_cboNatureza.insets = new Insets(5, 5, 5, 0);
		gbc_cboNatureza.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboNatureza.gridx = 3;
		gbc_cboNatureza.gridy = 0;
		cboNatureza.setRotulo("Natureza");
		painelFiltro.add(cboNatureza, gbc_cboNatureza);

		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		conteudo.add(painelFiltro, BorderLayout.NORTH);

		JPanel pnlLancamentos = new JPanel(new BorderLayout());
		pnlLancamentos.setBorder(
				new TitledBorder(null, "Lan\u00E7amentos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblDescLancto.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescLancto.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDescLancto.setForeground(Color.BLUE);
		lblDescLancto.setText("SELECIONE UMA LINHA PARA MAIORES INFORMAÇÕES");
		pnlLancamentos.add(lblDescLancto, BorderLayout.NORTH);
		scrollLancto.setViewportView(gridLancamento);
		pnlLancamentos.add(scrollLancto, BorderLayout.CENTER);

		JPanel pnlPrevisao = new JPanel(new BorderLayout());
		pnlPrevisao.setBorder(new TitledBorder(null, "Previsões", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblDescPrevisao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescPrevisao.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDescPrevisao.setForeground(Color.BLUE);
		lblDescPrevisao.setText("SELECIONE UMA LINHA PARA MAIORES INFORMAÇÕES");
		pnlPrevisao.add(lblDescPrevisao, BorderLayout.NORTH);
		scrollPrevisao.setViewportView(gridPrevisao);
		pnlPrevisao.add(scrollPrevisao, BorderLayout.CENTER);

		JPanel pnlInformacoes = new JPanel(new BorderLayout());
		pnlInformacoes.add(pnlLancamentos, BorderLayout.NORTH);
		pnlInformacoes.add(pnlPrevisao, BorderLayout.CENTER);

		pnlInformacoes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		conteudo.add(pnlInformacoes, BorderLayout.CENTER);

		gridLancamento.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				exibirDescLancto();
			}
		});
		
		gridPrevisao.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				exibirDescPrevisao();
			}
		});
		// campos da tabela
		gridLancamento.getModeloTabela().addColumn("Data");
		gridLancamento.getModeloTabela().addColumn("Conta");
		gridLancamento.getModeloTabela().addColumn("Natureza");
		gridLancamento.getModeloTabela().addColumn("Inicial");
		gridLancamento.getModeloTabela().addColumn("Valor");
		gridLancamento.getModeloTabela().addColumn("Final");

		gridLancamento.getModeloColuna().getColumn(0).setPreferredWidth(55);
		gridLancamento.getModeloColuna().getColumn(1).setPreferredWidth(165);
		gridLancamento.getModeloColuna().getColumn(2).setPreferredWidth(120);
		gridLancamento.getModeloColuna().getColumn(3).setPreferredWidth(70);
		gridLancamento.getModeloColuna().getColumn(4).setPreferredWidth(70);
		gridLancamento.getModeloColuna().getColumn(5).setPreferredWidth(70);

		gridLancamento.getModeloColuna().setCampo(0, "data");
		gridLancamento.getModeloColuna().setFormato(0, "dd/MM/yy");
		gridLancamento.getModeloColuna().setCampo(1, "conta.nome");
		gridLancamento.getModeloColuna().setCampo(2, "natureza.nome");
		gridLancamento.getModeloColuna().setCampo(3, "saldoInicial");
		gridLancamento.getModeloColuna().setFormato(3, Formato.MOEDA);
		gridLancamento.getModeloColuna().setCampo(4, "valor");
		gridLancamento.getModeloColuna().setFormato(4, Formato.MOEDA);
		gridLancamento.getModeloColuna().setCampo(5, "saldoFinal");
		gridLancamento.getModeloColuna().setFormato(5, Formato.MOEDA);
		gridLancamento.getModeloColuna().definirPositivoNegativo(4);

		// campos da tabela
		gridPrevisao.getModeloTabela().addColumn("Quitação");
		gridPrevisao.getModeloTabela().addColumn("Conta");
		gridPrevisao.getModeloTabela().addColumn("Natureza");
		gridPrevisao.getModeloTabela().addColumn("Contato");
		gridPrevisao.getModeloTabela().addColumn("Valor");
		
		gridPrevisao.getModeloColuna().getColumn(0).setPreferredWidth(55);
		gridPrevisao.getModeloColuna().getColumn(1).setPreferredWidth(165);
		gridPrevisao.getModeloColuna().getColumn(2).setPreferredWidth(120);
		gridPrevisao.getModeloColuna().getColumn(3).setPreferredWidth(140);
		gridPrevisao.getModeloColuna().getColumn(4).setPreferredWidth(70);
		
		gridPrevisao.getModeloColuna().setCampo(0, "quitacao");
		gridPrevisao.getModeloColuna().setFormato(0, "dd/MM/yy");
		gridPrevisao.getModeloColuna().setCampo(1, "conta.nome");
		gridPrevisao.getModeloColuna().setCampo(2, "natureza.nome");
		gridPrevisao.getModeloColuna().setCampo(3, "contato.nome");
		gridPrevisao.getModeloColuna().setCampo(4, "valor");
		gridPrevisao.getModeloColuna().setFormato(4, Formato.MOEDA);
		gridPrevisao.getModeloColuna().definirPositivoNegativo(4);

		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");

		//
		JPanel pnlSaldoLancto = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlSaldoLancto.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JPanel pnlSaldoPrevisao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlSaldoPrevisao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		pnlLancamentos.add(pnlSaldoLancto, BorderLayout.SOUTH);
		pnlPrevisao.add(pnlSaldoPrevisao, BorderLayout.SOUTH);

		txtLDespesas.setRotulo("Despesa");
		definir(txtLDespesas, Color.RED);
		txtLReceitas.setRotulo("Receita");
		definir(txtLReceitas, Color.BLUE);
		txtLSaldoAtual.setRotulo("Saldo");
		definir(txtLSaldoAtual, Color.BLUE);

		txtPDespesas.setRotulo("Despesa");
		definir(txtPDespesas, Color.RED);
		txtPReceitas.setRotulo("Receita");
		definir(txtPReceitas, Color.BLUE);
		txtPSaldoAtual.setRotulo("Saldo");
		definir(txtPSaldoAtual, Color.BLUE);

		pnlSaldoLancto.add(txtLReceitas);
		pnlSaldoLancto.add(txtLDespesas);
		pnlSaldoLancto.add(txtLSaldoAtual);

		pnlSaldoPrevisao.add(txtPReceitas);
		pnlSaldoPrevisao.add(txtPDespesas);
		pnlSaldoPrevisao.add(txtPSaldoAtual);

	}

	private void definir(SSCampoNumero txt, Color cor) {
		txt.setEditavel(false);
		txt.setComponenteCorFonte(cor);
		txt.setComponenteNegrito(true);
		txt.setColunas(6);
		txt.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txt.setFormato(Formato.MOEDA);
	}

	private void exibirDescLancto() {
		try {
			Lancamento l = (Lancamento) gridLancamento.getLinhaSelecionada();
			if (l != null) {
				lblDescLancto.setText(l.getDescricao());
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}
	private void exibirDescPrevisao() {
		try {
			Lancamento l = (Lancamento) gridPrevisao.getLinhaSelecionada();
			if (l != null) {
				lblDescPrevisao.setText(l.getDescricao());
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}
	@Override
	public void load() {
		cboConta.setItens(dao.listarContas(MDI.getPerfilId()), "nome");
		cboNatureza.setItens(dao.listarNaturezas(MDI.getPerfilId()), "nome");
		txtDataDe.setDataHora(new Date());
		txtDataAte.setDataHora(new Date());

	}

	private void sair() {
		super.fechar();
	}

	private void listar() {
		List<Lancamento> lanctos = new ArrayList<Lancamento>();
		List<Lancamento> previsoes = new ArrayList<Lancamento>();

		try {
			// lista = dao.listarOldLancamentos(getUsuarioId());
			Conta conta = (Conta) cboConta.getValue();
			Natureza nat = (Natureza) cboNatureza.getValue();
			Integer cId = null;
			Integer nId = null;
			if (conta != null)
				cId = conta.getId();

			if (nat != null)
				nId = nat.getId();

			lanctos = lactoDao.listarLancamentos(MDI.getPerfilId(), txtDataDe.getDataHora(), txtDataAte.getDataHora(), cId,
					nId);
			if(lanctos.size()==0)
				SSMensagem.avisa("Nenhum lançamento encontrado");
			
			gridLancamento.setValue(lanctos);
			totalLancto = lactoDao.totais(lanctos);

			previsoes = lactoDao.listarPrevisoes(MDI.getPerfilId(), txtDataDe.getDataHora(), txtDataAte.getDataHora(), cId,
					nId);
			
			if(previsoes.size()==0)
				SSMensagem.avisa("Nenhuma previsão encontrada");
			
			gridPrevisao.setValue(previsoes);
			totalPrevisao = lactoDao.totais(previsoes);

			txtLSaldoAtual.setValue(totalLancto.getSaldo());
			txtLSaldoAtual.setComponenteCorFonte(totalLancto.getSaldo() < 0.0d ? Color.RED : Color.BLUE);
			txtLDespesas.setValue(totalLancto.getDebito());
			txtLReceitas.setValue(totalLancto.getCredito());

			txtPSaldoAtual.setValue(totalPrevisao.getSaldo());
			txtPSaldoAtual.setComponenteCorFonte(totalPrevisao.getSaldo() < 0.0d ? Color.RED : Color.BLUE);
			txtPDespesas.setValue(totalPrevisao.getDebito());
			txtPReceitas.setValue(totalPrevisao.getCredito());

		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}

	}

}
