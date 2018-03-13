package edu.cfip.util.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edu.cfip.util.desktop.ss.SSCabecalho;
import edu.cfip.util.desktop.ss.SSRodape;
import edu.cfip.util.desktop.ss.util.Imagem;
//http://download.eclipse.org/windowbuilder/WB/integration/4.7/
public class Formulario extends JPanel {
	private SSCabecalho cabecalho = new SSCabecalho();
	private JPanel conteudo = new JPanel();
	private SSRodape rodape = new SSRodape();
	private MDI mdi;
	public Formulario() {
		init();
	}
	private void init() {
		this.conteudo.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setTitulo("Informe um titulo");
		this.setDescricao("Informe uma descrição");
		this.add(cabecalho, BorderLayout.NORTH);
		this.add(conteudo, BorderLayout.CENTER);
		this.add(rodape, BorderLayout.SOUTH);
	}
	public void setTitulo(String titulo) {
		this.cabecalho.setTitulo(titulo);
	}
	public void setDescricao(String descricao) {
		this.cabecalho.setDescricao(descricao);
	}
	public void setMdi(MDI mdi) {
		this.mdi = mdi;
	}
	public void setConteudoLayout(LayoutManager layout) {
		conteudo.setLayout(layout);
	}
	public void exibir() {
		this.exibir("Sistema");
	}
	public void exibir(String titulo) {
		this.exibir(this,titulo);
	}
	public void exibir(Formulario frm,String titulo) {
		if (frm != this) {
			frm.setMdi(this.mdi);
		}
		JInternalFrame internal = new JInternalFrame(titulo);
		internal.setVisible(true);
		internal.setResizable(true);
		internal.setFrameIcon(Imagem.png("cfip", "janela"));
		internal.setContentPane(frm);
		try {
			internal.setSelected(true);
			internal.pack();
			centralizar(internal);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		mdi.getAreaTrabalho().add(internal);
		mdi.getAreaTrabalho().getDesktopManager().activateFrame(internal);
	}
	private void centralizar(JInternalFrame componente) {
		Dimension dim = mdi.getSize();
		int x = dim.width / 2 - componente.getSize().width / 2;
		int y = dim.height / 2 - componente.getSize().height / 2;
		y = y - 50; //opcional
		componente.setLocation(x, y);
		componente.setVisible(true);
	}
}
