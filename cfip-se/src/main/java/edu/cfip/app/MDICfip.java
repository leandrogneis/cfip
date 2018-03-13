package edu.cfip.app;

import edu.cfip.util.desktop.MDI;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		mnForms.add(mnDialog);
	}
	public static void main(String[] args) {
		new MDICfip().setVisible(true);
	}
	private void abrirFormulario() {
		
	}
}
