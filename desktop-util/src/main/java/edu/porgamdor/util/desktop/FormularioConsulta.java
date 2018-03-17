package edu.porgamdor.util.desktop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.porgamdor.util.desktop.ss.SSGrade;

public class FormularioConsulta extends Formulario {
	private JPanel filtro = new JPanel();
	private JScrollPane scroll= new JScrollPane();
	private SSGrade tabela = new SSGrade();
	public FormularioConsulta() {
		//definir propriedades
		setConteudoLayout(new BorderLayout());
		filtro.setLayout(new GridBagLayout());
		scroll.setViewportView(tabela);
		getConteudo().add(filtro,BorderLayout.NORTH);
		getConteudo().add(scroll,BorderLayout.CENTER);
		setAlinhamentoRodape(FlowLayout.LEFT);
	}
	public JPanel getFiltro() {
		return filtro;
	}
	public SSGrade getTabela() {
		return tabela;
	}
}
