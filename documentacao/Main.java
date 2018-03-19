package client.desktop.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.boxs.cfip.core.dao.Entidades;
import com.boxs.cfip.core.model.Usuario;
import com.boxs.cfip.core.run.HSQLDB;

import client.desktop.DesktopApp;
import client.desktop.app.acesso.FrmUsuario;
import client.desktop.app.config.FrmConfiguracao;
import client.desktop.util.Arquivo;
import client.desktop.util.Formulario;
import client.ss.desktop.Mensagem;
import javax.swing.JToolBar;
import java.awt.FlowLayout;
import java.awt.Insets;

import client.ss.desktop.SSBotao;
import client.ss.desktop.imagens.Imagem;

import java.awt.Font;
import javax.swing.JSeparator;

@Component
public class Main extends JFrame {
	private JDesktopPane desktop;
	private Usuario usuario;
	@Autowired
	private Entidades dao;
	private AnnotationConfigApplicationContext context;
	private JLabel imagemFundo = new JLabel();
	private ImageIcon imgFundo;
	private JPanel conteudo = new JPanel();
	private JLabel lblNome = new JLabel("NOME");
	private JLabel lblUsuario = new JLabel("USUARIO");
	private JLabel lblAmbiente = new JLabel("AMBIENTE");
	private JMenu mnBancoDados;
	public Main() {
		try {
			this.setIconImage(Imagem.png("cfip", "janela").getImage());
			conteudo.setLayout(new BorderLayout());
			//https://www.iconfinder.com/iconsets/fugue - procurar importar
			//https://www.iconfinder.com/iconsets/fugue
			// https://www.iconfinder.com/iconsets/gnome-desktop-icons-png
			//https://www.iconfinder.com/icons/36090/group_people_user_users_icon#size=16
			//https://www.iconfinder.com/iconsets/fatcow
			//https://www.iconfinder.com/icons/1889193/analytics_chart_finance_financial_financial_report_flipchart_report_icon#size=16
			//https://www.iconfinder.com/icons/41190/cancel_round_icon#size=16
			//https://www.iconfinder.com/iconsets/fatcow
			setTitle("CFIP - Controle Financeiro Pessoal");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(new Rectangle(870, 630));
			setLocationRelativeTo(null);
			// setExtendedState(JFrame.MAXIMIZED_BOTH);

			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu mnCadastros = new JMenu("Cadastros");
			mnCadastros.setIcon(Imagem.png( "0cadastros"));

			menuBar.add(mnCadastros);
			JMenuItem mnConta = new JMenuItem("Conta");
			mnConta.setIcon(Imagem.png("cfip", "conta"));
			mnConta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirConta();
				}
			});
			mnCadastros.add(mnConta);

			JMenuItem mnNatureza = new JMenuItem("Natureza");
			mnNatureza.setIcon(Imagem.png("cfip", "natureza"));
			mnNatureza.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirNatureza();
				}
			});

			mnCadastros.add(mnNatureza);

			JMenuItem mnDespesasRapidas = new JMenuItem("Despesa Rápida");
			mnDespesasRapidas.setIcon(Imagem.png("cfip", "despesarapida"));
			mnDespesasRapidas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirDespesasRapidas();
				}
			});
			mnCadastros.add(mnDespesasRapidas);
			
			JMenuItem mntmContato = new JMenuItem("Contato");
			mntmContato.setIcon(Imagem.png("cfip", "contato"));
			mntmContato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirContatos();
				}
			});
			
			JMenuItem mntmFatura = new JMenuItem("Fatura");
			mntmFatura.setIcon(Imagem.png("cfip", "fatura"));
			mntmFatura.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirFatura();
				}
			});
			mnCadastros.add(mntmFatura);
			mnCadastros.add(mntmContato);
			

			JMenu mnLancamentos = new JMenu("Lançamentos");
			mnLancamentos.setIcon(Imagem.png("1lancamentos"));
			menuBar.add(mnLancamentos);

			JMenuItem mnDespesaRapida = new JMenuItem("Despesa Rápida");
			mnDespesaRapida.setIcon(Imagem.png("cfip", "despesarapida"));
			mnDespesaRapida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirLactoDespesaRapida();
				}
			});
			mnLancamentos.add(mnDespesaRapida);

			JMenuItem mnReceitas = new JMenuItem("Receitas");
			mnReceitas.setIcon(Imagem.png("cfip", "receita"));
			mnLancamentos.add(mnReceitas);
			mnReceitas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirReceita();
				}
			});

			JMenuItem mnPrevisoes = new JMenuItem("Previsões");
			mnPrevisoes.setIcon(Imagem.png("cfip", "previsao"));
			mnPrevisoes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirPrevisao();
				}
			});

			JMenuItem mnDespesas = new JMenuItem("Despesas");
			mnDespesas.setIcon(Imagem.png("cfip", "despesa"));
			mnLancamentos.add(mnDespesas);
			mnDespesas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirDespesa();
				}
			});
			JMenuItem mnTranferencias = new JMenuItem("Transferências");
			mnTranferencias.setIcon(Imagem.png("cfip", "transferencia"));
			mnTranferencias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirTransferencia();
				}
			});
			mnLancamentos.add(mnTranferencias);
			mnLancamentos.add(mnPrevisoes);

			JMenuItem mnSaldos = new JMenuItem("Conta Ciclo");
			mnSaldos.setIcon(Imagem.png("cfip", "novociclo"));
			mnSaldos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirSaldo();
				}
			});
			mnLancamentos.add(mnSaldos);
			
			JMenuItem mnFaturas = new JMenuItem("Faturas");
			mnFaturas.setIcon(Imagem.png("cfip", "fatura"));
			mnFaturas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirFaturas();
				}
			});
			mnLancamentos.add(mnFaturas);

			JMenu mnConsultas = new JMenu("Consultas");
			mnConsultas.setIcon(Imagem.png("2consultas"));
			menuBar.add(mnConsultas);

			JMenuItem mnConsultaLancamentos = new JMenuItem("Lançamentos");
			mnConsultaLancamentos.setIcon(Imagem.png("dinheiro"));
			mnConsultaLancamentos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirConsultaLancamentos();
				}
			});
			mnConsultas.add(mnConsultaLancamentos);

			JMenuItem mnConsultaPrevisoes = new JMenuItem("Previsões");
			mnConsultaPrevisoes.setIcon(Imagem.png("calendario10"));
			mnConsultaPrevisoes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirConsultaPrevisoes();
				}
			});

			mnConsultas.add(mnConsultaPrevisoes);
			
			JMenuItem mnProjecoes = new JMenuItem("Projeções");
			mnProjecoes.setIcon(Imagem.png("cfip", "projecao"));
			mnProjecoes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirProjecoes();
				}
			});
			mnConsultas.add(mnProjecoes);
			
			JMenuItem mnMovimentacoes = new JMenuItem("Movimentações");
			mnMovimentacoes.setIcon(Imagem.png("cfip", "resumo"));
			mnMovimentacoes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirMovimentacoes();
				}
			});
			mnConsultas.add(mnMovimentacoes);

			JMenu mnRelatorios = new JMenu("Relatórios");
			mnRelatorios.setIcon(Imagem.png( "3relatorios"));
			menuBar.add(mnRelatorios);

			JMenu mnAjuda = new JMenu("Ajuda");
			mnAjuda.setIcon(Imagem.png( "5ajuda"));
			
			
			JMenu mnFerramentas = new JMenu("Ajustes");
			mnFerramentas.setIcon(Imagem.png("4ajustes"));
			menuBar.add(mnFerramentas);
			menuBar.add(mnAjuda);
			
			JMenuItem mntmManual = new JMenuItem("Manual 1.0");
			mntmManual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					exibirManual();
				}

			});
			mntmManual.setIcon(Imagem.png("informacao"));
			mnAjuda.add(mntmManual);
			
			JMenuItem mntmUsurio = new JMenuItem("Usuário");
			mntmUsurio.setIcon(Imagem.png("cardeit"));
			mntmUsurio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirUsuario();
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
					exibirConfiguracao();
				}
			});
			mntmConexo.setIcon(Imagem.png("dbconexao"));
			
			JSeparator separator = new JSeparator();
			mnFerramentas.add(separator);
			
			mnBancoDados = new JMenu("Banco Dados");
			mnBancoDados.setIcon(Imagem.png("dbajuste"));
			mnFerramentas.add(mnBancoDados);
			
			JMenuItem mnBackup = new JMenuItem("Backup");
			mnBancoDados.add(mnBackup);
			mnBackup.setIcon(Imagem.png("backup"));
			
			JMenuItem mnRestaurar = new JMenuItem("Restore");
			mnRestaurar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirRestore();
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
					sql();
				}
			});
			
			mnBancoDados.add(mnSql);
			mnBackup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					exibirBackup();
				}
			});
			

			desktop = new JDesktopPane();
			desktop.setVisible(true);
			imgFundo = Imagem.jpg("cfip", "cfip");
			imagemFundo.setIcon(imgFundo);
			desktop.add(imagemFundo);
			desktop.setBackground(Color.LIGHT_GRAY);
			desktop.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					desktopPane_componentResized(e);
				}
			});
			conteudo.add(desktop, BorderLayout.CENTER);
			setContentPane(conteudo);

			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			conteudo.add(panel, BorderLayout.NORTH);

			JLabel lblUser = new JLabel("USUÁRIO:");
			lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel.add(lblUser);
			panel.add(lblUsuario);

			JLabel lblName = new JLabel("NOME:");
			lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel.add(lblName);
			panel.add(lblNome);

			JLabel lblEnv = new JLabel("AMBIENTE:");
			lblEnv.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel.add(lblEnv);
			panel.add(lblAmbiente);
			
			JLabel lblVersao = new JLabel("Versão: 1.0");
			lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel.add(lblVersao);
			
			
			
			lblUsuario.setForeground(Color.BLUE);
			lblNome.setForeground(Color.BLUE);
			lblAmbiente.setForeground(Color.BLUE);
			
			lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblAmbiente.setFont(new Font("Tahoma", Font.BOLD, 11));
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
	
	private void desktopPane_componentResized(ComponentEvent e) {
		if (imgFundo == null)
			return;

		int top, left;

		top = (desktop.getWidth() / 2) - (imgFundo.getIconWidth() / 2);
		left = (desktop.getHeight() / 2) - (imgFundo.getIconHeight() / 2);

		imagemFundo.setBounds(top, left - 20, imgFundo.getIconWidth(), imgFundo.getIconHeight());
	}

	public void setContext(AnnotationConfigApplicationContext context) {
		this.context = context;
	}

	public AnnotationConfigApplicationContext getContext() {
		return context;
	}

	public JDesktopPane getDesktop() {
		return desktop;
	}

	private void exibirManual() {
		try {
			Arquivo.abrirPdf();
		} catch (Exception e) {
			Mensagem.erro("Erro ao tentar visualizar o Manual");
		}
		
	}
	private void exibirMovimentacoes() {
		exibir(context.getBean(FrmMovimentacoes.class));
	}
	private void exibirProjecoes() {
		exibir(context.getBean(FrmProjecoes.class));
	}
	private void exibirNatureza() {
		exibir(context.getBean(FrmNaturezas.class));
	}
	private void exibirFatura() {
		exibir(context.getBean(FrmFatura.class));
	}
	private void exibirReceita() {
		FrmLancamentoCredito frm = context.getBean(FrmLancamentoCredito.class);
		exibir(frm);
	}

	private void exibirDespesa() {
		FrmLancamentoDebito frm = context.getBean(FrmLancamentoDebito.class);
		exibir(frm);
	}

	private void exibirTransferencia() {
		exibir(context.getBean(FrmLancamentoTransferencia.class));
	}

	private void exibirPrevisao() {
		FrmLancamentoPrevisao frm = context.getBean(FrmLancamentoPrevisao.class);
		exibir(frm);
	}

	private void exibirSaldo() {
		FrmSaldos frm = context.getBean(FrmSaldos.class);
		exibir(frm);
	}

	private void exibirConsultaLancamentos() {
		FrmLancamentos frm = context.getBean(FrmLancamentos.class);
		exibir(frm);
	}
	private void exibirBackup() {
		FrmBackup frm = context.getBean(FrmBackup.class);
		exibir(frm);
	}
	private void exibirRestore() {
		FrmRestore frm = context.getBean(FrmRestore.class);
		exibir(frm);
	}
	private void sql() {
		//http://www.avajava.com/tutorials/lessons/how-do-i-run-another-application-from-java.html
		try {
		if(Mensagem.pergunta("Esta operação encerra a aplicação\nDeseja prosseguir?")) {
			DesktopApp.closeContext();
			HSQLDB.main(new String[] {});
			this.dispose();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void exibirConsultaPrevisoes() {
		FrmPrevisoes frm = context.getBean(FrmPrevisoes.class);
		exibir(frm);
	}

	private void exibirConta() {
		exibir(context.getBean(FrmContas.class));
	}
	private void exibirFaturas() {
		exibir(context.getBean(FrmFaturas.class));
	}
	private void exibirDespesasRapidas() {
		exibir(context.getBean(FrmDespesasRapidas.class));
	}
	private void exibirContatos() {
		exibir(context.getBean(FrmContatos.class));
	}
	private void exibirLactoDespesaRapida() {
		exibir(context.getBean(FrmLancamentoDespesaRapida.class));
	}

	private void exibir(Formulario formulario) {
		formulario.setApp(this);
		formulario.exibir();
	}
	private void exibirUsuario() {
		FrmUsuario frm = context.getBean(FrmUsuario.class);
		frm.setEntidade(getUsuario());
		frm.setVisible(true);
	}
	private void exibirConfiguracao() {
		FrmConfiguracao frm = context.getBean(FrmConfiguracao.class);
		frm.setParametros(getUsuario(),lblAmbiente.getText());
		frm.setVisible(true);
	}
	public void setUsuario(Usuario user) {
		usuario = user;
		if (usuario != null) {
			lblUsuario.setText(usuario.getLogin());
			lblNome.setText(usuario.getNome().toUpperCase());
			lblAmbiente.setText(DesktopApp.getNomeAmbiente());
			mnBancoDados.setEnabled(DesktopApp.isBancoLocal());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
