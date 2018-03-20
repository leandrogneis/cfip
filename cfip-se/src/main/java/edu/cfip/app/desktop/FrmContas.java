package edu.cfip.app.desktop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.cfip.app.spring.DesktopApp;
import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.model.Conta;
import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
public class FrmContas extends Formulario {
	@Autowired
	private Repositorio dao;
	
	//JA PODERIA VIR DE FormularioConsulta
	private JPanel filtro = new JPanel();
	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();
	
	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	
	public FrmContas() {
		//JA PODERIA VIR DE FormularioConsulta
		setConteudoLayout(new BorderLayout());
		setAlinhamentoRodape(FlowLayout.LEFT);
		filtro.setLayout(new GridBagLayout());
		
		txtFiltro.setRotulo("Nome");
		txtFiltro.setColunas(30);
		cmdBuscar.setText("Buscar");
		
		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		
		tabela.getModeloTabela().addColumn("Sigla");
		tabela.getModeloTabela().addColumn("Nome");
		tabela.getModeloTabela().addColumn("Saldo");
		
		tabela.getModeloColuna().getColumn(0).setPreferredWidth(90);
		tabela.getModeloColuna().getColumn(1).setPreferredWidth(180);
		tabela.getModeloColuna().getColumn(2).setPreferredWidth(70);
		
		tabela.getModeloColuna().setCampo(0, "sigla");
		tabela.getModeloColuna().setCampo(1, "nome");
		tabela.getModeloColuna().setCampo(2, "saldo");
		
		tabela.getModeloColuna().setFormato(2, Formato.MOEDA);
		//tabela.getModeloColuna().definirPositivoNegativo(2);
		
		//constraints - grid bag layout
		GridBagConstraints gbcTxtFiltro = new GridBagConstraints();
		gbcTxtFiltro.weightx = 1.0;
		gbcTxtFiltro.anchor = GridBagConstraints.NORTHWEST;
		gbcTxtFiltro.insets = new Insets(5, 5, 5, 5);
		gbcTxtFiltro.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFiltro.gridx = 0;
		gbcTxtFiltro.gridy = 0;
		
		GridBagConstraints gbcCmdBuscar = new GridBagConstraints();
		gbcCmdBuscar.anchor = GridBagConstraints.SOUTHWEST;
		gbcCmdBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcCmdBuscar.insets = new Insets(0, 0, 5, 5);
		gbcCmdBuscar.gridx = 1;
		gbcCmdBuscar.gridy = 0;
		
		
		//adicionando componentes aos seus containers
		filtro.add(txtFiltro, gbcTxtFiltro);
		filtro.add(cmdBuscar, gbcCmdBuscar);
		
		scroll.setViewportView(tabela);
		
		getConteudo().add(filtro,BorderLayout.NORTH);
		getConteudo().add(scroll,BorderLayout.CENTER);
		
		getRodape().add(cmdIncluir);
		getRodape().add(cmdAlterar);
		getRodape().add(cmdFechar);
		
		//métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		cmdIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluir();
			}
		});
		cmdAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
	}
	public JPanel getFiltro() {
		return filtro;
	}
	private void sair() {
		super.fechar();
	}
	private void listar() {
		tabela.removeAll();
		List<Conta> lista = new ArrayList<Conta>();
		/*
		lista.add(new Conta("CARTEIRA", "CRT", 800.0));
		lista.add(new Conta("CONTA CORRENTE", "CCR", 123.0));
		lista.add(new Conta("POUPANCA", "PUP", 123.45));
		tabela.setValue(lista);
		*/
		lista = dao.listarContas();
		tabela.setValue(lista);
		System.out.println("CONSULTANDO AS CONTAS EM NOSSO BANCO DE DADOS");
	}
	private void incluir() {
		exibirCadastro(null);
	}
	private void alterar() {
		Conta entidade= (Conta) tabela.getLinhaSelecionada();
		if(entidade==null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		exibirCadastro(entidade);
	}
	private void exibirCadastro(Conta entidade) {
		//FrmConta frm = new FrmConta();
		Formulario frm = DesktopApp.getBean(FrmConta.class);
		frm.setEntidade(entidade);
		this.exibir(frm);
	}
	
}
