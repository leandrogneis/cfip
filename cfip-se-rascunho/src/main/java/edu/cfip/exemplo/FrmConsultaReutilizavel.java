package edu.cfip.exemplo;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.FormularioConsulta;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;

public class FrmConsultaReutilizavel extends FormularioConsulta {
	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();

	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();

	public FrmConsultaReutilizavel() {
		setTitulo("Formulário de Consulta Customizado");
		setDescricao("Podemos usar herança em formulários como em qualquer objeto");
		
		txtFiltro.setRotulo("Nome");
		txtFiltro.setColunas(30);
		cmdBuscar.setText("Buscar");

		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		
		getTabela().getModeloTabela().addColumn("Sigla");
		getTabela().getModeloTabela().addColumn("Nome");
		getTabela().getModeloTabela().addColumn("Saldo");
		getTabela().getModeloColuna().getColumn(0).setPreferredWidth(90);
		getTabela().getModeloColuna().getColumn(1).setPreferredWidth(180);
		getTabela().getModeloColuna().getColumn(2).setPreferredWidth(70);
		getTabela().getModeloColuna().setCampo(0, "sigla");
		getTabela().getModeloColuna().setCampo(1, "nome");
		getTabela().getModeloColuna().setCampo(2, "saldo");
		getTabela().getModeloColuna().setFormato(2, Formato.MOEDA);
		getTabela().getModeloColuna().definirPositivoNegativo(2);

		// constraints - grid bag layout
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

		// adicionando componentes aos seus containers
		getFiltro().add(txtFiltro, gbcTxtFiltro);
		getFiltro().add(cmdBuscar, gbcCmdBuscar);
		
		getRodape().add(cmdIncluir);
		getRodape().add(cmdAlterar);
		getRodape().add(cmdFechar);

		// métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
	}

	private void sair() {
		super.fechar();
	}
}
