package edu.cfip.util.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

//Multiple Document Interface
public class MDI extends JFrame {
	private JDesktopPane areaTrabalho = new JDesktopPane(); 
	private Sessao sessao;
	private JPanel barraSessao = new JPanel();
	private JMenuBar barraMenu = new JMenuBar();
	public MDI() {
		areaTrabalho.setBackground(Color.LIGHT_GRAY);
		areaTrabalho.setVisible(true);
		barraSessao.setLayout(new FlowLayout());
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(barraSessao,BorderLayout.NORTH);
		getContentPane().add(areaTrabalho, BorderLayout.CENTER);
		setJMenuBar(barraMenu);
		setTitle("MDI -Multiple Document Interface");
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(870, 630));
		setLocationRelativeTo(null);
	}
	public JDesktopPane getAreaTrabalho() {
		return areaTrabalho;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	
}
