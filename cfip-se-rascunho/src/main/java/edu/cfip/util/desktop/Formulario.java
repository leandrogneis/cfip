package edu.cfip.util.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.beans.PropertyVetoException;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import edu.cfip.util.desktop.ss.SSCabecalho;
import edu.cfip.util.desktop.ss.SSMensagem;
import edu.cfip.util.desktop.ss.SSRodape;
import edu.cfip.util.desktop.ss.util.Imagem;
//WindowBuilder
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
		this.setDescricao("Informe uma descri��o");
		
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
	public JPanel getConteudo() {
		return conteudo;
	}
	public SSRodape getRodape() {
		return rodape;
	}
	public void setConteudoLayout(LayoutManager layout) {
		conteudo.setLayout(layout);
	}
	public void exibir() {
		this.exibir(this);
	}
	public void exibir(Formulario frm) {
		JInternalFrame internal = new JInternalFrame("CFIP - Formul�rio");
		internal.setVisible(true);
		internal.setResizable(true);
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
	public void cancelar() {
		boolean resposta = SSMensagem.pergunta("Deseja cancelar esta opera��o");
		if (resposta) {
			fechar();
		}
	}
	public void fechar() {
		removerFormulario();
	}
	private void removerFormulario() {
		JInternalFrame iframe = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, this);
		mdi.getAreaTrabalho().remove(iframe);
		mdi.getAreaTrabalho().repaint();
	}
	private void centralizar(JInternalFrame componente) {
		Dimension dim = mdi.getSize();
		int x = dim.width / 2 - componente.getSize().width / 2;
		int y = dim.height / 2 - componente.getSize().height / 2;
		y = y - 50; //opcional
		componente.setLocation(x, y);
		componente.setVisible(true);
	}
	public void onLoad() {
		System.out.println("onLoad");
	}
}
