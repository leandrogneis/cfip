package edu.cfip.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import edu.cfip.exemplo.FrmConsultaModelo;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSMensagem;

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
		String lf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lf);
			MDICfip mdi = new MDICfip();
			mdi.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void abrirFormulario() {
		Formulario form = new FrmConsultaModelo();
		form.setMdi(this);
		form.exibir();
	}
}
