package client.desktop.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.boxs.cfip.core.model.Usuario;

import client.desktop.app.Main;
import client.ss.desktop.Mensagem;
import client.ss.desktop.SSBotao;
import client.ss.desktop.SSCabecalho;
import client.ss.desktop.SSRodape;
import client.ss.desktop.imagens.Imagem;

public abstract class Formulario extends JPanel {
	//dialogo
	private Object respostaDialogo;
	private Formulario dono;
	
	private SSCabecalho cabecalho = new SSCabecalho();
	private JPanel conteudo = new JPanel();
	private SSRodape rodape = new SSRodape();
	private Usuario usuario;
	private Main app;
	public void setApp(Main app) {
		this.app = app;
	}

	private Main getApp() {
		return app;
	}

	private JDesktopPane getDesktop() {
		return getApp().getDesktop();
	}

	public void setTitulo(String titulo) {
		this.cabecalho.setTitulo(titulo);
	}

	public void setDescricao(String descricao) {
		this.cabecalho.setDescricao(descricao);
	}
	public void addComponente(JComponent componente) {
		this.rodape.add(componente);
	}
	public void addBotaoRodape(SSBotao botao) {
		this.rodape.add(botao);
	}

	public void botoesNaEsquerda() {
		this.rodape.setLayout(new FlowLayout(FlowLayout.LEFT));
	}

	public JPanel getConteudoTabela() {
		this.conteudo.setLayout(new BorderLayout());
		return conteudo;
	}

	public JPanel getConteudoGrid() {
		
		return conteudo;
	}

	public Formulario() {
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());

		this.setTitulo("Informe um titulo");
		this.setDescricao("Informe uma descrição");

		this.add(cabecalho, BorderLayout.NORTH);
		this.conteudo.setLayout(new GridBagLayout());
		//this.conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.add(conteudo, BorderLayout.CENTER);
		this.add(rodape, BorderLayout.SOUTH);
	}
	public void ocultarRodape() {
		rodape.setVisible(false);
	}
	public void exibir() {
		this.exibir(this);
	}
	public Formulario getDono() {
		return dono;
	}
	public void exibir(Formulario frm) {
		if (frm != this) {
			frm.setApp(this.getApp());
			frm.dono=this;
		}
		frm.usuario = this.app.getUsuario();
		frm.load();
		JInternalFrame internal = new JInternalFrame("CFIP - Controle Financeiro Pessoal");
		
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
		getDesktop().add(internal);
		getDesktop().getDesktopManager().activateFrame(internal);
		//getDesktop().moveToFront(internal);
	}
	public Object exibirDialogo(Formulario form){
		form.dono=this;
		form.usuario = this.app.getUsuario();
		form.load();
		this.respostaDialogo=null;
		JDialog dialog = new JDialog(app);
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
	public boolean isDialogo(Formulario form){
		return SwingUtilities.getAncestorOfClass(JDialog.class, form) !=null;
	}
	private void removerFormulario() {
		JInternalFrame iframe = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, this);
		getDesktop().remove(iframe);
		getDesktop().repaint();
	}
	private void removerDialogo() {
		JDialog dialog = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this);
		dialog.dispose();
		dialog.setVisible(false);
	}
	public void retornar() {
		if(isDialogo(this))
			removerDialogo();
		else
			removerFormulario();
	}
	public void fechar(Object resposta) {
		if(resposta!=null){
			dono.respostaDialogo = resposta;
			retornar();
		}else{
			Mensagem.avisa("Selecione um item da lista");
		}
	}
	public void fechar() {
		boolean resposta = Mensagem.pergunta("Deseja cancelar esta operação");
		if (resposta) {
			retornar();
		}
	}
	private void centralizar(JInternalFrame componente) {
		//Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dim = app.getSize();
		
		int x = dim.width / 2 - componente.getSize().width / 2;
		int y = dim.height / 2 - componente.getSize().height / 2;
		y = y - 50;
		componente.setLocation(x, y);
		componente.setVisible(true);
	}
	
	public Usuario getUsuario() {
		//return app.getUsuario()
		return usuario;
	}

	public Integer getUsuarioId() {
		return getUsuario().getId();
	}
	
	public <T> T getBean(Class bean) {
		return (T) app.getContext().getBean(bean);
	}
	public void load(Object param) {
		
	}
	public void load() {
		
	}
}
