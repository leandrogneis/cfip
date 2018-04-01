package client.desktop.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.boxs.cfip.core.dao.Entidades;
import com.boxs.cfip.core.model.Conta;
import com.boxs.cfip.core.model.Contato;
import com.boxs.cfip.core.util.Formato;

import client.desktop.util.Formulario;
import client.ss.desktop.Mensagem;
import client.ss.desktop.PosicaoRotulo;
import client.ss.desktop.SSBotao;
import client.ss.desktop.SSCampoTexto;
import client.ss.desktop.SSGrade;
import client.ss.infraestrutura.util.Validacao;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmContatos extends Formulario {
	// rodape
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdExcluir = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	
	// conteudo - topo - filtro
	private SSCampoTexto txtFiltroNome = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
	@Autowired
	private Entidades dao;

	public FrmContatos() {
		init();
	}
	private void init() {
		
		super.setTitulo("Consulta de Contatos");
		super.setDescricao("Descrição das contatos do sistema");
		super.botoesNaEsquerda();
		super.addBotaoRodape(cmdIncluir);
		super.addBotaoRodape(cmdAlterar);
		super.addBotaoRodape(cmdExcluir);
		super.addBotaoRodape(cmdFechar);
		
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudoTabela();

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		scroll.setViewportView(grid);

		conteudo.add(scroll, BorderLayout.CENTER);

		painelFiltro.setLayout(new GridBagLayout());
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbcNome = new GridBagConstraints();
		gbcNome.weightx = 1.0;
		gbcNome.insets = new Insets(5, 5, 5, 5);
		gbcNome.fill = GridBagConstraints.HORIZONTAL;
		gbcNome.gridx = 0;
		gbcNome.gridy = 0;
		painelFiltro.add(txtFiltroNome, gbcNome);

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.insets = new Insets(0, 0, 0, 5);
		gbcBuscar.gridx = 1;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);

		// campos da tabela
		//grid.getModeloTabela().addColumn("Código");
		grid.getModeloTabela().addColumn("Código");
		grid.getModeloTabela().addColumn("Nome");
		grid.getModeloTabela().addColumn("Telefone");
		grid.getModeloColuna().getColumn(0).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(150);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(70);
		grid.getModeloColuna().setCampo(0, "id");
		grid.getModeloColuna().setCampo(1, "nome");
		grid.getModeloColuna().setCampo(2, "telefone");
		txtFiltroNome.setRotulo("Nome");
		txtFiltroNome.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		cmdIncluir.setText("Novo");
		cmdAlterar.setText("Alterar");
		cmdExcluir.setText("Excluir");
		cmdExcluir.setVisible(false);
		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");

		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluir();
			}
		});
		cmdAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		cmdExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		txtFiltroNome.requestFocus();
	}

	private void sair() {
		super.retornar();
		//super.fechar(); //SE QUISER PERGUTAR ANTES
	}

	private void incluir() {
		abrirCadastro(null);
	}
	private void excluir() {
		Conta entidade= (Conta) grid.getLinhaSelecionada();
		if(entidade==null) {
			Mensagem.avisa("Selecione um item para a exclusão");
			return;
		}
		if(Mensagem.pergunta("Confirma excluir o item selecionado")) {
			dao.excluir(entidade.getClass(), entidade.getId());
			Mensagem.informa("Item excluído com sucesso");
			listar();
		}
	}
	private void alterar() {
		Contato entidade= (Contato) grid.getLinhaSelecionada();
		abrirCadastro(entidade);
	}
	

	private void abrirCadastro(Contato entidade) {
		FrmContato frm = getBean(FrmContato.class);
		frm.setEntidade(entidade);
		this.exibir(frm);
	}

	private void listar() {
		List<Contato> lista = new ArrayList<Contato>();
		try {
			String nome = txtFiltroNome.getText();
			if (Validacao.vazio(nome)) {
				lista = dao.listarContatos(getUsuarioId());

			} else {
				lista = dao.listarContatos(getUsuarioId(), nome);
			}
			if(lista.size()==0)
				Mensagem.avisa("Nenhum dado encontrado");
			
			grid.setValue(lista);
		} catch (Exception e) {
			e.printStackTrace();
			Mensagem.erro(e.getMessage());
		}

	}
	
}
