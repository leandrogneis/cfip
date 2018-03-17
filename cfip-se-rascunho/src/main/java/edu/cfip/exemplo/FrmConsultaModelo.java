package edu.cfip.exemplo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;

public class FrmConsultaModelo extends Formulario {
	private JPanel filtro = new JPanel();
	private JScrollPane scroll;
	private SSGrade tabela = new SSGrade();
	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	private final SSBotao cmdIncluir = new SSBotao();
	private final SSBotao cmdAlterar = new SSBotao();
	public FrmConsultaModelo() {
		//mudar o layout
		setConteudoLayout(new BorderLayout());
		filtro.setLayout(new GridBagLayout());
		getConteudo().add(filtro,BorderLayout.NORTH);
		
		GridBagConstraints gbc_campoTexto = new GridBagConstraints();
		gbc_campoTexto.weightx = 1.0;
		gbc_campoTexto.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoTexto.insets = new Insets(5, 5, 5, 5);
		gbc_campoTexto.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoTexto.gridx = 0;
		gbc_campoTexto.gridy = 0;
		txtFiltro.setRotulo("Nome");
		filtro.add(txtFiltro, gbc_campoTexto);
		
		GridBagConstraints gbc_botao = new GridBagConstraints();
		gbc_botao.anchor = GridBagConstraints.SOUTHWEST;
		gbc_botao.fill = GridBagConstraints.HORIZONTAL;
		gbc_botao.insets = new Insets(0, 0, 5, 5);
		gbc_botao.gridx = 1;
		gbc_botao.gridy = 0;
		cmdBuscar.setText("Buscar");
		filtro.add(cmdBuscar, gbc_botao);
		
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
		tabela.getModeloColuna().definirPositivoNegativo(2);
		scroll = new JScrollPane();
		scroll.setViewportView(tabela);
		getConteudo().add(scroll,BorderLayout.CENTER);
		txtFiltro.setColunas(30);
		
		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		
		getRodape().add(cmdIncluir);
		cmdAlterar.setText("Alterar");
		
		getRodape().add(cmdAlterar);
		setAlinhamentoRodape(FlowLayout.LEFT);
	}
	public JPanel getFiltro() {
		return filtro;
	}
	
}
