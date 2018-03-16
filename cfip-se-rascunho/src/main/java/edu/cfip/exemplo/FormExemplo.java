package edu.cfip.exemplo;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.porgamdor.util.desktop.Formulario;

public class FormExemplo extends Formulario {
	private JTextField textField;
	private JTextField textField_1;
	public FormExemplo() {
		GridBagLayout gridBagLayout = (GridBagLayout) getConteudo().getLayout();
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		FlowLayout flowLayout = (FlowLayout) getRodape().getLayout();
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		getRodape().add(btnSair);
		
		JButton btnSalvar = new JButton("Salvar");
		getRodape().add(btnSalvar);
		
		JLabel lblLabel = new JLabel("Label");
		GridBagConstraints gbc_lblLabel = new GridBagConstraints();
		gbc_lblLabel.insets = new Insets(5, 5, 5, 5);
		gbc_lblLabel.gridx = 0;
		gbc_lblLabel.gridy = 0;
		getConteudo().add(lblLabel, gbc_lblLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		getConteudo().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblLabel_1 = new JLabel("Label 2");
		GridBagConstraints gbc_lblLabel_1 = new GridBagConstraints();
		gbc_lblLabel_1.insets = new Insets(5, 5, 5, 5);
		gbc_lblLabel_1.gridx = 0;
		gbc_lblLabel_1.gridy = 2;
		getConteudo().add(lblLabel_1, gbc_lblLabel_1);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(5, 5, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 3;
		getConteudo().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
	}
	private void sair() {
		super.cancelar();
		//super.fechar();
	}
}
