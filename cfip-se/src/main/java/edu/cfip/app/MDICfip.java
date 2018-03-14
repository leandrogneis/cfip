package edu.cfip.app;

import edu.cfip.exemplo.FormExemplo;
import edu.cfip.util.desktop.Formulario;
import edu.cfip.util.desktop.MDI;
import edu.cfip.util.desktop.ss.SSMensagem;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MDICfip extends MDI {
	public MDICfip() {
		
		JMenu mnFormularios = new JMenu("Formularios");
		getBarraMenu().add(mnFormularios);
		
		JMenuItem mntmInternal = new JMenuItem("Internal");
		mntmInternal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirFormulario();
			}
		});
		mnFormularios.add(mntmInternal);
		
		JMenuItem mntmDialog = new JMenuItem("Dialog");
		mntmDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SSMensagem.informa("Clicado Dialog");
			}
		});
		mnFormularios.add(mntmDialog);
		setTitle("CFIP - Controle Financeiro Pessoal");
	}
	public static void main(String[] args) {
		MDICfip mdi = new MDICfip();
		mdi.setVisible(true);
	}
	private void abrirFormulario() {
		Formulario form = new FormExemplo();
		form.setMdi(this);
		form.exibir();
	}
}
