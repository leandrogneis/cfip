package edu.cfip.app.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.cfip.core.dao.Repositorio;
import edu.cfip.core.dao.RepositorioLancamento;
import edu.cfip.core.model.Lancamento;
import edu.cfip.core.model.TipoMovimento;
import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmCompensar extends Formulario {
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private JTextArea txtDescricao = new JTextArea();
	
	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Lancamento entidade;
	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento lanctoDao;
	
	public FrmCompensar() {
		init();
	}
	private void init() {
		// HERANÇA
		super.setTitulo("Compensação");
		super.setDescricao("Compensa Previsão");
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtData.anchor = GridBagConstraints.WEST;
		gbc_txtData.insets = new Insets(5, 5, 0, 5);
		gbc_txtData.gridx = 0;
		gbc_txtData.gridy = 0;
		txtData.setColunas(10);
		txtData.setComponenteCorFonte(Color.BLUE);
		txtData.setComponenteNegrito(true);
		txtData.setForeground(Color.BLUE);
		panelCampos.add(txtData, gbc_txtData);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.weightx = 2.0;
		gbc_txtValor.insets = new Insets(5, 5, 0, 5);
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValor.gridx = 0;
		gbc_txtValor.gridy = 1;
		txtValor.setEditavel(false);
		txtValor.setComponenteCorFonte(Color.BLUE);
		txtValor.setComponenteNegrito(true);
		panelCampos.add(txtValor, gbc_txtValor);

		txtData.setRotulo("Data");
		txtValor.setColunas(10);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		
		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.weighty = 1.0;
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 2;
		txtDescricao.setLineWrap(true);
		txtDescricao.setColumns(20);
		txtDescricao.setRows(4);
		txtDescricao.setWrapStyleWord(true);
		//txtDescricao.setEnabled(false);
		txtDescricao.setText("DESCRIÇÃO");
		txtDescricao.setForeground(Color.BLUE);
		JScrollPane scrooll = new JScrollPane(txtDescricao);
		scrooll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelCampos.add(scrooll, gbc_txtDescricao);
		
		
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
		txtData.requestFocus();
		txtData.setValue(new Date());
		txtValor.setValue(0.0d);
	}
	private void salvar() {
		if(SSMensagem.pergunta("Confirma compensar este lançamento ?")) {
			lanctoDao.compensarLancamento(entidade.getId(),txtData.getDataHora());
			SSMensagem.informa("Lançamento compensado com sucesso!!");
			super.fechar();
		}
		
	}
	public void setId(Integer id) {
		entidade = dao.buscar(Lancamento.class, id);
		if(entidade!=null) {
			txtData.setDataHora(new Date());
			txtValor.setRotulo(entidade.getTipoMovimento() + " - R$ Valor");
			txtValor.setValue(entidade.getValor());
			txtDescricao.setText(entidade.getTipoMovimento() + "\n" + entidade.getDescricao());
			if(entidade.getTipoMovimento() == TipoMovimento.DEBITO) {
				txtData.setComponenteCorFonte(Color.RED);
				txtValor.setComponenteCorFonte(Color.RED);
				txtDescricao.setForeground(Color.RED);
			}
		}
	}
	private void sair() {
		super.fechar();
	}
	
}
