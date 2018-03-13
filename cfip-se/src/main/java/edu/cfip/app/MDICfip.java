package edu.cfip.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import edu.cfip.form.exemplo.FormExemplo;
import edu.cfip.util.desktop.Formulario;
import edu.cfip.util.desktop.MDI;

public class MDICfip extends MDI {
	public MDICfip() {
		super();
		setTitle("CFIP - Controle Financeiro Pessoal");
		
		JMenu mnForms = new JMenu("Formulários");
		getBarraMenu().add(mnForms);
		
		JMenuItem mnInternal = new JMenuItem("Internal");
		mnInternal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormulario();
			}
		});
		mnForms.add(mnInternal);
		
		JMenuItem mnDialog = new JMenuItem("Dialog");
		mnDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirDialog();
			}
		});
		mnForms.add(mnDialog);
	}
	public static void main(String[] args) {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
			new MDICfip().setVisible(true);
		}catch (Exception e) {
			
		}
	}
	private void abrirFormulario() {
		Formulario form= new FormExemplo();
		form.setMdi(this);
		form.exibir();
	}
	private void abrirDialog() {
		Formulario form= new FormExemplo();
		form.setMdi(this);
		form.exibirDialogo();
	}
}
