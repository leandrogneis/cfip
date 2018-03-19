package edu.cfip.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import edu.cfip.app.spring.DesktopApp;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.util.Imagem;

@Component
public class MDICfip extends MDI {
	//private AnnotationConfigApplicationContext context;
	public MDICfip() {
		setTitle("CFIP - Controle Financeiro Pessoal");
		JMenu mnCadastros = new JMenu("Cadastros");
		mnCadastros.setIcon(Imagem.png( "0cadastros"));
		JMenuItem mnConta = new JMenuItem("Conta");
		mnConta.setIcon(Imagem.png("cfip", "conta"));
		mnConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirConta();
				exibirBean("frmContas");
			}
		});
		mnCadastros.add(mnConta);

		JMenuItem mnNatureza = new JMenuItem("Natureza");
		mnNatureza.setIcon(Imagem.png("cfip", "natureza"));
		mnNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirNatureza();
			}
		});

		mnCadastros.add(mnNatureza);

		JMenuItem mnDespesasRapidas = new JMenuItem("Despesa Rápida");
		mnDespesasRapidas.setIcon(Imagem.png("cfip", "despesarapida"));
		mnDespesasRapidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirDespesasRapidas();
			}
		});
		mnCadastros.add(mnDespesasRapidas);
		
		JMenuItem mntmContato = new JMenuItem("Contato");
		mntmContato.setIcon(Imagem.png("cfip", "contato"));
		mntmContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirContatos();
			}
		});
		
		JMenuItem mntmFatura = new JMenuItem("Fatura");
		mntmFatura.setIcon(Imagem.png("cfip", "fatura"));
		mntmFatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirFatura();
			}
		});
		mnCadastros.add(mntmFatura);
		mnCadastros.add(mntmContato);
		

		JMenu mnLancamentos = new JMenu("Lançamentos");
		mnLancamentos.setIcon(Imagem.png("1lancamentos"));
		

		JMenuItem mnDespesaRapida = new JMenuItem("Despesa Rápida");
		mnDespesaRapida.setIcon(Imagem.png("cfip", "despesarapida"));
		mnDespesaRapida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirLactoDespesaRapida();
			}
		});
		mnLancamentos.add(mnDespesaRapida);

		JMenuItem mnReceitas = new JMenuItem("Receitas");
		mnReceitas.setIcon(Imagem.png("cfip", "receita"));
		mnLancamentos.add(mnReceitas);
		mnReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirReceita();
			}
		});

		JMenuItem mnPrevisoes = new JMenuItem("Previsões");
		mnPrevisoes.setIcon(Imagem.png("cfip", "previsao"));
		mnPrevisoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirPrevisao();
			}
		});

		JMenuItem mnDespesas = new JMenuItem("Despesas");
		mnDespesas.setIcon(Imagem.png("cfip", "despesa"));
		mnLancamentos.add(mnDespesas);
		mnDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirDespesa();
			}
		});
		JMenuItem mnTranferencias = new JMenuItem("Transferências");
		mnTranferencias.setIcon(Imagem.png("cfip", "transferencia"));
		mnTranferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirTransferencia();
			}
		});
		mnLancamentos.add(mnTranferencias);
		mnLancamentos.add(mnPrevisoes);

		JMenuItem mnSaldos = new JMenuItem("Conta Ciclo");
		mnSaldos.setIcon(Imagem.png("cfip", "novociclo"));
		mnSaldos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirSaldo();
			}
		});
		mnLancamentos.add(mnSaldos);
		
		JMenuItem mnFaturas = new JMenuItem("Faturas");
		mnFaturas.setIcon(Imagem.png("cfip", "fatura"));
		mnFaturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirFaturas();
			}
		});
		mnLancamentos.add(mnFaturas);

		JMenu mnConsultas = new JMenu("Consultas");
		mnConsultas.setIcon(Imagem.png("2consultas"));
		

		JMenuItem mnConsultaLancamentos = new JMenuItem("Lançamentos");
		mnConsultaLancamentos.setIcon(Imagem.png("dinheiro"));
		mnConsultaLancamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirConsultaLancamentos();
			}
		});
		mnConsultas.add(mnConsultaLancamentos);

		JMenuItem mnConsultaPrevisoes = new JMenuItem("Previsões");
		mnConsultaPrevisoes.setIcon(Imagem.png("calendario10"));
		mnConsultaPrevisoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirConsultaPrevisoes();
			}
		});

		mnConsultas.add(mnConsultaPrevisoes);
		
		JMenuItem mnProjecoes = new JMenuItem("Projeções");
		mnProjecoes.setIcon(Imagem.png("cfip", "projecao"));
		mnProjecoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirProjecoes();
			}
		});
		mnConsultas.add(mnProjecoes);
		
		JMenuItem mnMovimentacoes = new JMenuItem("Movimentações");
		mnMovimentacoes.setIcon(Imagem.png("cfip", "resumo"));
		mnMovimentacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirMovimentacoes();
			}
		});
		mnConsultas.add(mnMovimentacoes);

		JMenu mnRelatorios = new JMenu("Relatórios");
		mnRelatorios.setIcon(Imagem.png( "3relatorios"));
		
		JMenu mnAjuda = new JMenu("Ajuda");
		mnAjuda.setIcon(Imagem.png( "5ajuda"));
		
		
		JMenu mnFerramentas = new JMenu("Ajustes");
		mnFerramentas.setIcon(Imagem.png("4ajustes"));
		
		
		JMenuItem mntmManual = new JMenuItem("Manual 1.0");
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//exibirManual();
			}

		});
		mntmManual.setIcon(Imagem.png("informacao"));
		mnAjuda.add(mntmManual);
		
		JMenuItem mntmUsurio = new JMenuItem("Usuário");
		mntmUsurio.setIcon(Imagem.png("cardeit"));
		mntmUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirUsuario();
			}
		});
		mnFerramentas.add(mntmUsurio);
		
		JSeparator separator_2 = new JSeparator();
		mnFerramentas.add(separator_2);
		
		JMenu mnConexoes = new JMenu("Conexões");
		mnFerramentas.add(mnConexoes);
		
		JMenuItem mntmConexo = new JMenuItem("Banco Dados");
		mnConexoes.add(mntmConexo);
		mnConexoes.setIcon(Imagem.png("conexao"));
		mntmConexo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//exibirConfiguracao();
			}
		});
		mntmConexo.setIcon(Imagem.png("dbconexao"));
		
		JSeparator separator = new JSeparator();
		mnFerramentas.add(separator);
		
		JMenu mnBancoDados = new JMenu("Banco Dados");
		mnBancoDados.setIcon(Imagem.png("dbajuste"));
		mnFerramentas.add(mnBancoDados);
		
		JMenuItem mnBackup = new JMenuItem("Backup");
		mnBancoDados.add(mnBackup);
		mnBackup.setIcon(Imagem.png("backup"));
		
		JMenuItem mnRestaurar = new JMenuItem("Restore");
		mnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirRestore();
			}
		});
		mnBancoDados.add(mnRestaurar);
		mnRestaurar.setIcon(Imagem.png("restaurar"));
		
		JSeparator separator_1 = new JSeparator();
		mnBancoDados.add(separator_1);
		
		JMenuItem mnSql = new JMenuItem("SQL");
		mnSql.setIcon(Imagem.png("executar"));
		mnSql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//sql();
			}
		});
		
		mnBancoDados.add(mnSql);
		mnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirBackup();
			}
		});
		getBarraMenu().add(mnCadastros);
		getBarraMenu().add(mnLancamentos);
		getBarraMenu().add(mnConsultas);
		getBarraMenu().add(mnRelatorios);
		getBarraMenu().add(mnFerramentas);
		getBarraMenu().add(mnAjuda);
	}
	private void exibirConta() {
		//Formulario form = context.getBean(FrmContas.class);
		//exibir(form);
		exibir((Formulario)DesktopApp.getBean(FrmContas.class));
	}
	private void exibir(Formulario formulario) {
		formulario.setMdi(this);
		formulario.exibir();
	}
	private void exibirBean(String bean) {
		Formulario form = (Formulario) DesktopApp.getBean(bean);
		form.setMdi(this);
		form.exibir();
	}
	
}
