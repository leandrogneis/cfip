package edu.cfip.app.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.dao.springjpa.RepositorioLancamento;
import edu.cfip.core.model.Conta;
import edu.cfip.core.model.Contato;
import edu.cfip.core.model.Fatura;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.Natureza;
import edu.cfip.core.model.TipoMovimento;
import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoMascara;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmLancamentoPrevisao extends Formulario {
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private SSCampoTexto txtDescricao = new SSCampoTexto();
	private SSCampoDataHora txtDataPrevisao = new SSCampoDataHora();
	private SSCampoMascara txtParcelas = new SSCampoMascara();
	
	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Lancamento entidade;
	
	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento lanctoDao;
	
	
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboDestino = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboContato = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboFatura = new SSCaixaCombinacao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	public FrmLancamentoPrevisao() {
		init();
	}
	private void init() {
		// HERANÇA
		super.setTitulo("Previsões");
		super.setDescricao("Registra os lançamentos futuros");
		getRodape().add(chkNovo);
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		cboFatura.setRotulo("Fatura - Lote");
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.gridwidth = 1;
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtData.anchor = GridBagConstraints.WEST;
		gbc_txtData.insets = new Insets(5, 5, 0, 0);
		gbc_txtData.gridx = 0;
		gbc_txtData.gridy = 0;
		txtData.setColunas(8);
		panelCampos.add(txtData, gbc_txtData);
		
		GridBagConstraints gbc_cboFatura = new GridBagConstraints();
		gbc_cboFatura.gridwidth = 1;
		gbc_cboFatura.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboFatura.anchor = GridBagConstraints.WEST;
		gbc_cboFatura.insets = new Insets(5, 5, 0, 5);
		gbc_cboFatura.gridx = 1;
		gbc_cboFatura.gridy = 0;
		txtData.setColunas(8);
		panelCampos.add(cboFatura, gbc_cboFatura);
		
		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.gridwidth = 2;
		gbc_cboConta.insets = new Insets(5, 5, 0, 5);
		gbc_cboConta.fill = GridBagConstraints.BOTH;
		gbc_cboConta.gridx = 0;
		gbc_cboConta.gridy = 1;
		cboConta.setRotulo("Conta");
		panelCampos.add(cboConta, gbc_cboConta);
		
		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.gridwidth = 2;
		gbc_cboNatureza.insets = new Insets(5, 5, 0, 5);
		gbc_cboNatureza.fill = GridBagConstraints.BOTH;
		gbc_cboNatureza.gridx = 0;
		gbc_cboNatureza.gridy = 2;
		cboNatureza.setRotulo("Natureza");
		panelCampos.add(cboNatureza, gbc_cboNatureza);
		
		GridBagConstraints gbc_cboDestino = new GridBagConstraints();
		gbc_cboDestino.gridwidth = 2;
		gbc_cboDestino.insets = new Insets(5, 5, 0, 5);
		gbc_cboDestino.fill = GridBagConstraints.BOTH;
		gbc_cboDestino.gridx = 0;
		gbc_cboDestino.gridy = 3;
		cboDestino.setRotulo("Destino");
		panelCampos.add(cboDestino, gbc_cboDestino);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.gridwidth = 2;
		gbc_txtValor.weightx = 2.0;
		gbc_txtValor.insets = new Insets(5, 5, 0, 5);
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValor.gridx = 0;
		gbc_txtValor.gridy = 4;
		txtValor.setComponenteNegrito(true);
		panelCampos.add(txtValor, gbc_txtValor);

		txtData.setRotulo("Data Registro");
		txtValor.setColunas(10);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		
		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.gridwidth = 2;
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 5;
		txtDescricao.setColunas(20);
		txtDescricao.setRotulo("Descrição");
		panelCampos.add(txtDescricao, gbc_txtDescricao);
		
		GridBagConstraints gbc_cboContato = new GridBagConstraints();
		gbc_cboContato.weightx = 1.0;
		gbc_cboContato.gridwidth = 2;
		gbc_cboContato.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboContato.insets = new Insets(5, 5, 5, 5);
		gbc_cboContato.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboContato.gridx = 0;
		gbc_cboContato.gridy = 6;
		cboContato.setRotulo("Contato");
		panelCampos.add(cboContato, gbc_cboContato);
		
		
		GridBagConstraints gbc_txtDataPrevisao = new GridBagConstraints();
		gbc_txtDataPrevisao.weightx = 1.0;
		gbc_txtDataPrevisao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataPrevisao.insets = new Insets(5, 5, 5, 5);
		gbc_txtDataPrevisao.fill = GridBagConstraints.BOTH;
		gbc_txtDataPrevisao.gridx = 0;
		gbc_txtDataPrevisao.gridy = 7;
		txtDataPrevisao.setColunas(8);
		txtDataPrevisao.setForeground(Color.BLUE);
		txtDataPrevisao.setRotulo("Data Previsão");
		panelCampos.add(txtDataPrevisao, gbc_txtDataPrevisao);
		
		GridBagConstraints gbc_txtParcelas = new GridBagConstraints();
		gbc_txtParcelas.weightx = 1.0;
		gbc_txtParcelas.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtParcelas.insets = new Insets(5, 5, 5, 5);
		gbc_txtParcelas.fill = GridBagConstraints.BOTH;
		gbc_txtParcelas.gridx = 1;
		gbc_txtParcelas.gridy = 7;
		try {
			txtParcelas.setSelecionarAoEntrar(true);
			txtParcelas.setMascara("##-###");
			txtParcelas.setText("0101");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtParcelas.setColunas(4);
		panelCampos.add(txtParcelas, gbc_txtParcelas);
		
		
		cmdSair.setText("Cancelar");
		
		cmdSalvar.setText("Confirmar");
		// Listners = Comandos = Eventos
		cmdSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvar();
			}
		});
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		cboDestino.setEnabled(false);
		cboNatureza.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        habilitarDestino();
		    }
		});
		cboFatura.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        habilitarPrevisao();
		    }
		});
		txtParcelas.setRotulo("Parcelas");
		cboFatura.setEditavel(false);
		inicializa();
		
	}
	private void habilitarDestino() {
		Natureza natureza = (Natureza) cboNatureza.getValue();
		if(natureza!=null) {
			cboDestino.setValue(null);
			cboDestino.setEnabled(natureza!=null && natureza.getTipoMovimento().isTranferencia());
			if(natureza.getTipoMovimento()==TipoMovimento.CREDITO)
				txtValor.setComponenteCorFonte(Color.BLACK);
			else
				txtValor.setComponenteCorFonte(Color.RED);
		}
	}
	private void habilitarPrevisao() {
		Fatura fatura = (Fatura) cboFatura.getValue();
		txtParcelas.setEditavel(fatura==null);
		txtDataPrevisao.setEditavel(fatura==null);
	
		if(fatura!=null) {
			txtDataPrevisao.setDataHora(fatura.getVencimento());
			SSMensagem.avisa("Ao selecionar fatura, será gerada uma única parcela");
		}
	} 
	private void salvar() {
		try {
			entidade = new Lancamento();
			entidade.setValor(txtValor.getDouble());
			entidade.setDescricao(txtDescricao.getText());
			Conta conta = (Conta) cboConta.getValue();
			Conta destino = (Conta) cboDestino.getValue();
			Natureza natureza = (Natureza) cboNatureza.getValue();
			entidade.setConta(conta);
			if(destino!=null)
				entidade.setDestino(destino);
			
			Contato contato = (Contato) cboContato.getValue();
			if(contato!=null)
				entidade.setContato(contato);
			
			/*Fatura fat = (Fatura) cboFatura.getValue();
			if(fat!=null)
				entidade.setFatura(fat.getId());
			*/
			entidade.setData(txtData.getDataHora());
			entidade.setPrevisao(true);
			entidade.setQuitacao(txtDataPrevisao.getDataHora());
			entidade.setNatureza(natureza);
			entidade.setTipoMovimento(natureza.getTipoMovimento());
			entidade.setUsuario(MDI.getPerfilId());
			
			if(entidade.getConta()==null || entidade.getNatureza() == null 
			|| entidade.getData() == null || entidade.getValor() == null || 
			entidade.getDescricao()==null || entidade.getQuitacao() == null ||  entidade.getDescricao().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getTipoMovimento()==TipoMovimento.TRANSFERENCIA && destino==null) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(txtParcelas.getText()!= null) {
				String[] iniFim = txtParcelas.getText().split("-");
				entidade.setParcelaInicial(Integer.valueOf(iniFim[0].trim()));
				entidade.setParcelaFinal(Integer.valueOf(iniFim[1].trim()));
			}else {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getConta() == entidade.getDestino()) {
				SSMensagem.avisa("Origem e Destino são iguais");
				return;
			}
			lanctoDao.incluirLancamento(entidade);
			SSMensagem.informa("Lançamento registrado com sucesso!!");
			novo();
		} catch (Exception e) {
			SSMensagem.erro("Dados incompletos");
		}
	}

	private void novo() {
		if(chkNovo.isSelected()) {
			inicializa();
		}else
			super.fechar();
	}
	
	private void inicializa() {
		entidade = new Lancamento();
		txtData.requestFocus();
		txtData.setValue(new Date());
		txtValor.setValue(0.0d);
		txtData.setDataHora(new Date());
		txtDescricao.setText("");
	}
	private void sair() {
		super.fechar();
	}
	public void load() {
		List<Conta> contas = dao.listarContas(MDI.getPerfilId());
		cboConta.setItens( contas,"nome");
		cboDestino.setItens( contas,"nome");
		cboNatureza.setItens( dao.listarNaturezas(MDI.getPerfilId()),"nomeSigla");
		cboContato.setItens(dao.listarContatos(MDI.getPerfilId()),"nome");
		//cboFatura.setItens(dao.listarFaturas(MDI.getPerfilId()),"sigla");
	}
}
