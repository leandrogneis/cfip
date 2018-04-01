package edu.porgamdor.util.desktop;

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

import edu.porgamdor.util.desktop.ss.SSCabecalho;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.SSRodape;
import edu.porgamdor.util.desktop.ss.util.Imagem;
//WindowBuilder
//http://download.eclipse.org/windowbuilder/WB/integration/4.7/
public abstract class Formulario extends JPanel {
	private Object respostaDialogo;
	private Formulario dono;
	
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
		this.setTitulo("Informe um título");
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
	public MDI getMdi() {
		return mdi;
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
	public void setAlinhamentoRodape(int alinhamento) {
		rodape.setAlinhamento(alinhamento);
	}
	public void exibir() {
		this.exibir(this);
	}
	public void exibir(Formulario frm) {
		if (frm != this) {
			frm.setMdi(this.getMdi());
		}
		JInternalFrame internal = new JInternalFrame("CFIP - Formulário");
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
	public void fechar(Object resposta) {
		if(resposta!=null){
			dono.respostaDialogo = resposta;
			fechar();
		}else{
			SSMensagem.avisa("Selecione um item da lista");
		}
	}
	public void cancelar() {
		boolean resposta = SSMensagem.pergunta("Deseja cancelar esta operação");
		if (resposta) {
			fechar();
		}
	}
	public void fechar() {
		if(isDialogo(this))
			removerDialogo();
		else
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
	public Object dialogo(Formulario form){
		form.dono=this;
		form.load();
		this.respostaDialogo=null;
		JDialog dialog = new JDialog(mdi);
		dialog.setIconImage(Imagem.png("cfip", "janela").getImage());
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.setModal(true);
        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);        
        dialog.dispose();
        return  respostaDialogo;
	}
	private void removerDialogo() {
		JDialog dialog = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this);
		dialog.dispose();
		dialog.setVisible(false);
	}
	public boolean isDialogo(Formulario form){
		return SwingUtilities.getAncestorOfClass(JDialog.class, form) !=null;
	}
	public void setEntidade(Object entidade) {
		
	}
	public void load() {
		
	}
}
