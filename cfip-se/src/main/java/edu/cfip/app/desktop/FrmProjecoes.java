package edu.cfip.app.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
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
import edu.porgamdor.util.desktop.ss.tabela.TipoSelecao;
import edu.porgamdor.util.desktop.ss.util.DataHora;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmProjecoes extends Formulario {
	// rodape
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade gridContas = new SSGrade();
	private SSGrade gridLancamentos = new SSGrade();
	// DAOs - NAO OFICIAL
	@Autowired
	private Repositorio dao;
	
	@Autowired
	private RepositorioLancamento lactoDao;

	private SSCampoDataHora txtDataDe = new SSCampoDataHora();
	private SSCampoDataHora txtDataAte = new SSCampoDataHora();
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private JLabel lblDesc = new JLabel();
	
	private SSCampoNumero txtSaldoContas = new SSCampoNumero();
	//
	private Total totalLancamentos=new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	//

	public FrmProjecoes() {
		init();
	}

	private void init() {
		cboConta.setPreferredWidth(180);
		cboNatureza.setPreferredWidth(150);
		super.setTitulo("Projeções");
		super.setDescricao("Análise financeira futura");
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		gridLancamentos.setTipoSelecao(TipoSelecao.SELECAO_MULTIPLA);
		JScrollPane scrollLancamentos = new JScrollPane();
		scrollLancamentos.setViewportView(gridLancamentos);
		JScrollPane scrollSaldos = new JScrollPane();
		scrollSaldos.setPreferredSize(new Dimension(0, 100));
		scrollLancamentos.setPreferredSize(new Dimension(0, 170));
		scrollSaldos.setViewportView(gridContas);
		JPanel pnlConteudo= new JPanel(new BorderLayout());
		
		pnlConteudo.add(scrollSaldos,BorderLayout.NORTH);
		pnlConteudo.add(scrollLancamentos,BorderLayout.CENTER);
		conteudo.add(pnlConteudo, BorderLayout.CENTER);
		gridLancamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		        exibirDescricao();
		    }
		});


		GridBagLayout gbl_painelFiltro = new GridBagLayout();
		painelFiltro.setLayout(gbl_painelFiltro);
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.anchor = GridBagConstraints.NORTHWEST;
		gbcBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcBuscar.insets = new Insets(15, 5, 5, 5);
		gbcBuscar.gridx = 4;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);
		
		gridContas.getModeloTabela().addColumn("Sigla");
		gridContas.getModeloTabela().addColumn("Nome");
		gridContas.getModeloTabela().addColumn("Saldo");
		gridContas.getModeloColuna().getColumn(0).setPreferredWidth(130);
		gridContas.getModeloColuna().getColumn(1).setPreferredWidth(335);
		gridContas.getModeloColuna().getColumn(2).setPreferredWidth(100);
		gridContas.getModeloColuna().setCampo(0, "sigla");
		gridContas.getModeloColuna().setCampo(1, "nome");
		gridContas.getModeloColuna().setCampo(2, "saldo");
		gridContas.getModeloColuna().setFormato(2, Formato.MOEDA);
		gridContas.getModeloColuna().definirPositivoNegativo(2);

		gridLancamentos.getModeloTabela().addColumn("Data");
		gridLancamentos.getModeloTabela().addColumn("Previsão");
		gridLancamentos.getModeloTabela().addColumn("Conta");
		gridLancamentos.getModeloTabela().addColumn("Natureza");
		gridLancamentos.getModeloTabela().addColumn("Valor");
		
		gridLancamentos.getModeloColuna().getColumn(0).setPreferredWidth(65);
		gridLancamentos.getModeloColuna().getColumn(1).setPreferredWidth(65);
		gridLancamentos.getModeloColuna().getColumn(2).setPreferredWidth(175);
		gridLancamentos.getModeloColuna().getColumn(3).setPreferredWidth(160);
		gridLancamentos.getModeloColuna().getColumn(4).setPreferredWidth(100);
		
		gridLancamentos.getModeloColuna().setCampo(0, "data");
		gridLancamentos.getModeloColuna().setFormato(0, "dd/MM/yy");
		gridLancamentos.getModeloColuna().setCampo(1, "quitacao");
		gridLancamentos.getModeloColuna().setFormato(1, "dd/MM/yy");
		gridLancamentos.getModeloColuna().setCampo(2, "conta.nome");
		gridLancamentos.getModeloColuna().setCampo(3, "natureza.nome");
		gridLancamentos.getModeloColuna().setCampo(4, "valor");
		gridLancamentos.getModeloColuna().setFormato(4, Formato.MOEDA);
		gridLancamentos.getModeloColuna().definirPositivoNegativo(4);
		
		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");
		
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
		
		FlowLayout pnlSaldoLayout = new FlowLayout();
		pnlSaldoLayout.setAlignment(FlowLayout.RIGHT);
		JPanel pnlSaldo= new JPanel(pnlSaldoLayout);
		pnlSaldo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		pnlConteudo.add(pnlSaldo,BorderLayout.SOUTH);
		
		txtDespesas.setComponenteCorFonte(Color.RED);
		txtDespesas.setComponenteNegrito(true);
		txtDespesas.setEditavel(false);
		txtDespesas.setColunas(6);
		txtDespesas.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtDespesas.setRotulo("Despesa:");
		
		txtReceitas.setComponenteNegrito(true);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtReceitas.setEditavel(false);
		txtReceitas.setColunas(6);
		txtReceitas.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtReceitas.setRotulo("Receita:");
		
		txtSaldoAtual.setComponenteNegrito(true);
		txtSaldoAtual.setComponenteCorFonte(Color.BLUE);
		txtSaldoAtual.setEditavel(false);
		txtSaldoAtual.setColunas(6);
		txtSaldoAtual.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtSaldoAtual.setRotulo("Saldo:");
		
		txtSaldoContas.setEditavel(false);
		txtSaldoContas.setColunas(6);
		txtSaldoContas.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtSaldoContas.setRotulo("Contas:");
		txtSaldoContas.setComponenteNegrito(true);
		
		
		txtDespesas.setFormato(Formato.MOEDA);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtReceitas.setFormato(Formato.MOEDA);
		txtSaldoContas.setFormato(Formato.MOEDA);
			
		pnlSaldo.add(txtSaldoContas);
		pnlSaldo.add(txtReceitas);
		pnlSaldo.add(txtDespesas);
		pnlSaldo.add(txtSaldoAtual);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);
		
		//
		
	}

	@Override
	public void load() {
		cboConta.setItens(dao.listarContas(MDICfip.getPerfilId()), "nome");
		cboNatureza.setItens(dao.listarNaturezas(MDICfip.getPerfilId()), "nome");
		txtDataDe.setDataHora(DataHora.primeiroDiaDoMes());
		txtDataAte.setDataHora(DataHora.ultimoDiaDoMes());

	}
	private void exibirDescricao() {
		try {
			Lancamento l = (Lancamento) gridLancamentos.getLinhaSelecionada();
			if (l != null) {
				lblDesc.setText(l.getDescricao());
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	private void sair() {
		super.fechar();
	}
	

	private void listar() {
		List<Lancamento> lista = new ArrayList<Lancamento>();
		List<Conta> contas = new ArrayList<Conta>();
		try {
			//lista = dao.listarOldLancamentos(getUsuarioId());
			Conta conta = (Conta) cboConta.getValue();
			Natureza nat = (Natureza) cboNatureza.getValue();
			Integer cId=null;
			Integer nId=null;
			if(conta!=null)
				cId = conta.getId();
			
			if(nat!=null)
				nId = nat.getId();
			
			lista = lactoDao.listarPrevisoes(MDI.getPerfilId(), txtDataDe.getDataHora(), txtDataAte.getDataHora(), cId,nId);
			contas = dao.listarContas(MDI.getPerfilId(), cId);
			gridContas.setValue(contas);
			gridLancamentos.setValue(lista);
			totalLancamentos = lactoDao.totais(lista);
			if(lista.size()==0)
				SSMensagem.avisa("Nenhum dado encontrado");
			
			
			Double saldo = lactoDao.contaTotais(contas);
			txtSaldoContas.setValue(saldo);
			saldo=saldo + totalLancamentos.getSaldo();
			txtSaldoAtual.setValue(saldo);
			txtSaldoAtual.setComponenteCorFonte(saldo < 0.0d ? Color.RED: Color.BLUE);
			txtDespesas.setValue(totalLancamentos.getDebito());
			txtReceitas.setValue(totalLancamentos.getCredito());
		} catch (Exception e) {
			e.printStackTrace();
			//Mensagem.erro(e.getMessage());
		}

	}
}
