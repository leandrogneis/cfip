package edu.cfip.form.exemplo;

import edu.cfip.util.desktop.Formulario;
import edu.cfip.util.desktop.ss.SSCampoTexto;
import edu.cfip.util.desktop.ss.SSMensagem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import edu.cfip.util.desktop.ss.SSBotao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormExemplo extends Formulario {
	public FormExemplo() {
		GridBagLayout gridBagLayout = (GridBagLayout) getConteudo().getLayout();
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		setTitulo("Fomulario Exemplo");
		setDescricao("Descrição formulario");
		
		SSCampoTexto campoTexto = new SSCampoTexto();
		GridBagConstraints gbc_campoTexto = new GridBagConstraints();
		gbc_campoTexto.insets = new Insets(5, 5, 5, 5);
		gbc_campoTexto.fill = GridBagConstraints.BOTH;
		gbc_campoTexto.gridx = 0;
		gbc_campoTexto.gridy = 0;
		getConteudo().add(campoTexto, gbc_campoTexto);
		
		SSCampoTexto campoTexto_1 = new SSCampoTexto();
		GridBagConstraints gbc_campoTexto_1 = new GridBagConstraints();
		gbc_campoTexto_1.insets = new Insets(5, 5, 5, 5);
		gbc_campoTexto_1.fill = GridBagConstraints.BOTH;
		gbc_campoTexto_1.gridx = 0;
		gbc_campoTexto_1.gridy = 1;
		getConteudo().add(campoTexto_1, gbc_campoTexto_1);
		
		SSCampoTexto campoTexto_2 = new SSCampoTexto();
		GridBagConstraints gbc_campoTexto_2 = new GridBagConstraints();
		gbc_campoTexto_2.insets = new Insets(5, 5, 5, 5);
		gbc_campoTexto_2.fill = GridBagConstraints.BOTH;
		gbc_campoTexto_2.gridx = 0;
		gbc_campoTexto_2.gridy = 2;
		getConteudo().add(campoTexto_2, gbc_campoTexto_2);
		
		SSBotao btAo = new SSBotao();
		btAo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SSMensagem.informa("Opa, você clicou no botão Salvar");
			}
		});
		btAo.setText("Salvar");
		getRodape().add(btAo);
		
		SSBotao btAo_1 = new SSBotao();
		btAo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		btAo_1.setText("Sair");
		getRodape().add(btAo_1);
	}
	private void sair() {
		super.fechar();
	}
}
