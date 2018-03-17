package edu.cfip.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import edu.cfip.exemplo.FrmConsultaModelo;
import edu.cfip.exemplo.FrmConsultaReutilizavel;
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
		
		JMenuItem mntmConsultaModelo = new JMenuItem("Consulta Modelo");
		mnFormularios.add(mntmConsultaModelo);
		mntmConsultaModelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirConsultaModelo();
			}
		});
		JMenuItem mntmConsultaReutilizavel = new JMenuItem("Consulta Reutilizavel");
		mnFormularios.add(mntmConsultaReutilizavel);
		mntmConsultaReutilizavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirConsultaReutilizavel();
			}
		});
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
	private void abrirConsultaReutilizavel() {
		Formulario form = new FrmConsultaReutilizavel();
		form.setMdi(this);
		form.exibir();
	}
	private void abrirConsultaModelo() {
		Formulario form = new FrmConsultaModelo();
		form.setMdi(this);
		form.exibir();
	}
}
