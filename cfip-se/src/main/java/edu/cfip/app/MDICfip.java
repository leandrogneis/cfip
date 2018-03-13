package edu.cfip.app;

import edu.cfip.util.desktop.MDI;

public class MDICfip extends MDI {
	public MDICfip() {
		super();
		setTitle("CFIP - Controle Financeiro Pessoal");
	}
	public static void main(String[] args) {
		new MDICfip().setVisible(true);
	}
}
