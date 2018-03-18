package edu.cfip.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import org.springframework.stereotype.Component;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;

@Component
public class MDICfip extends MDI {
	public MDICfip() {
		
		JMenu mnCadastros = new JMenu("Cadastros");
		getBarraMenu().add(mnCadastros);
		
		JMenuItem mnContas = new JMenuItem("Contas");
		mnContas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirContas();
			}
		});
		mnCadastros.add(mnContas);
		setTitle("CFIP - Controle Financeiro Pessoal");
	}
	
	private void abrirContas() {
		Formulario form = new FrmContas();
		form.setMdi(this);
		form.exibir();
	}
	public static void main(String[] args) {
		String lf = UIManager.getSystemLookAndFeelClassName();
		try {
			//UIManager.setLookAndFeel(lf);
			MDICfip mdi = new MDICfip();
			mdi.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
