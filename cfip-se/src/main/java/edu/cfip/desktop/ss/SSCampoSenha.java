package edu.cfip.desktop.ss;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import edu.cfip.desktop.ss.evento.AlteracaoEvento;
import edu.cfip.desktop.ss.evento.AlteracaoListener;
import edu.cfip.desktop.ss.evento.PesquisaEvento;
import edu.cfip.desktop.ss.evento.PesquisaListener;
import edu.cfip.desktop.ss.util.Texto;


/**
 *@beaninfo
 *   attribute: isContainer false
 * description: Caixa de texto.
 *
 * @author  Frank Marlon M. dos Santos
 * @version 1.0 08/18/08
 * @see CampoMascara
 */
public class SSCampoSenha extends SSComponenteRotulado implements SSTextoComponente {    
    private JPasswordField txtCaixaTexto = new JPasswordField();
    private boolean selecionarAoEntrar = true;    
    private String valorAnterior;
    private boolean pesquisa;
    private SSBotao btnPesquisa;
        
    public SSCampoSenha() {        
        try {
            jbInit();
            setTudoMaiusculo(false);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    public char[] getDigitos(){
    	
    	return txtCaixaTexto.getPassword();
    }
    public String getSenha(){
    	return new String(txtCaixaTexto.getPassword());
    }
    private void jbInit() throws Exception {
    	
        setComponente(txtCaixaTexto);
        //txtCaixaTexto.setColumns(15);        
        txtCaixaTexto.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        txtCaixaTexto_focusGained(e);
                    }

                    public void focusLost(FocusEvent e) {
                        txtCaixaTexto_focusLost(e);
                    }
                });        
        txtCaixaTexto.setDocument(new DocCampoTexto());       
        
    }
    
    public void setHorizontalAlignment(int alinhamento) {
        txtCaixaTexto.setHorizontalAlignment(alinhamento);
    }
    
    public int getHorizontalAlignment() {
        return txtCaixaTexto.getHorizontalAlignment();
    }
    
    public int getColunas() {
        return txtCaixaTexto.getColumns();
    }
    
    public void setColunas(int colunas) {
        txtCaixaTexto.setColumns(colunas);
    }     

    public void setSelecionarAoEntrar(boolean selecionarAoEntrar) {
        this.selecionarAoEntrar = selecionarAoEntrar;
    }

    public boolean isSelecionarAoEntrar() {
        return selecionarAoEntrar;
    }

    public DocCampoTexto getDocCampoTexto() {
        return (SSCampoSenha.DocCampoTexto)txtCaixaTexto.getDocument();
    }

    private void txtCaixaTexto_focusGained(FocusEvent e) {
        if (isSelecionarAoEntrar()) {
            if (txtCaixaTexto.getText() != null)
                txtCaixaTexto.select(0, txtCaixaTexto.getText().length());;
        }
        setAlterado(false);
        valorAnterior = this.getText();
    }

    public String getText() {
        return txtCaixaTexto.getText();
    }

    public void setText(String text) {
        txtCaixaTexto.setText(text);
    }
    
    /**
     * Adds the specified action listener to receive 
     * action events from this textfield.
     *
     * @param l the action listener to be added
     */ 
    public synchronized void addActionListener(ActionListener l) {
        txtCaixaTexto.addActionListener(l);
    }

    public synchronized void removeActionListener(ActionListener l) {
        txtCaixaTexto.removeActionListener(l);
    }
    
    public synchronized ActionListener[] getActionListeners() {
        return txtCaixaTexto.getActionListeners();
    }
    
    public synchronized void addAlteracaoListener(AlteracaoListener l) {
        listenerList.add(AlteracaoListener.class, l);
    }

    public synchronized void removeAlteracaoListener(AlteracaoListener l) {
        listenerList.remove(AlteracaoListener.class, l);
    }
    
    public synchronized AlteracaoListener[] getAlteracaoListeners() {
        return listenerList.getListeners(AlteracaoListener.class);
    }
    
    public synchronized void addPesquisaListener(PesquisaListener pl) {        
        listenerList.add(PesquisaListener.class, pl);
    }

    public synchronized void removePesquisaListener(PesquisaListener pl) {        
        listenerList.remove(PesquisaListener.class, pl);
    }
    
    public synchronized PesquisaListener[] getPesquisaListeners() {        
        return listenerList.getListeners(PesquisaListener.class);
    }
            
    protected void setCaixaTexto(JPasswordField txtCaixaTexto) {
        this.remove(this.txtCaixaTexto);
        this.txtCaixaTexto = txtCaixaTexto;
        try {
            jbInit();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    protected JTextField getCaixaTexto() {
        return txtCaixaTexto;
    }

    public void setTamanhoMaximo(int tamanhoMaximo) {        
        getDocCampoTexto().setTamanhoMaximo(tamanhoMaximo);
    }

    public int getTamanhoMaximo() {
        return getDocCampoTexto().getTamanhoMaximo();
    }

    public void setTudoMaiusculo(boolean tudoMaiusculo) {
        getDocCampoTexto().setTudoMaisculo(tudoMaiusculo);
    }
    
    public void setAcentos(boolean acentos) {
        getDocCampoTexto().setAcentos(acentos);
    }
    public boolean isAcentos() {
        return getDocCampoTexto().isAcentos();
    }
    public boolean isTudoMaiusculo() {
        return getDocCampoTexto().isTudoMaisculo();
    }

    public boolean isEditavel() {
        return txtCaixaTexto.isEditable();
    }

    public void setEditavel(boolean editavel) {
        txtCaixaTexto.setEditable(editavel);        
        
        if (editavel) {
            //setComponenteCorFundo(SystemColor.activeCaptionText);            
            setComponenteCorFonte(SystemColor.controlText);
        } else {
            setComponenteCorFundo(SystemColor.control);
            //setComponenteCorFonte(FormularioUtil.getCorFonteSomenteLeitura());
        }
        setFocusable(editavel);
        if (btnPesquisa != null)
            btnPesquisa.setEnabled(editavel);
        //setOpaque(editavel);
    }

    private void txtCaixaTexto_focusLost(FocusEvent e) {
        String valorAtual = getText();
        if (valorAnterior == valorAtual)
            return;
        if (valorAtual != null && valorAtual.equals(valorAnterior))
            return;
        if (valorAnterior != null && valorAnterior.equals(valorAtual))
            return;        
    }

    public void setPesquisa(boolean pesquisa) {
        this.pesquisa = pesquisa;
        
        if (pesquisa) {
            if (btnPesquisa == null)
                adicionarBotaoPesquisa();
            else {
                if (!btnPesquisa.isVisible())
                    btnPesquisa.setVisible(true);
            }
        } else {
            removerBotaoPesquisa();
        }       
    }

    public boolean isPesquisa() {
        return pesquisa;
    }
    
    private void criarBotaoPesquisa() {
        if (btnPesquisa == null) {
            btnPesquisa = new SSBotao();
            btnPesquisa.setText(null);
            btnPesquisa.setFocusable(false);
            btnPesquisa.setIcone("pasta");
            btnPesquisa.setMargin(new Insets(0, 0, 0, 0));
            
            btnPesquisa.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PesquisaListener[] pls = listenerList.getListeners(PesquisaListener.class);
                    for (PesquisaListener pl : pls) {
                        pl.pesquisaListener(new PesquisaEvento(e.getSource()));
                    }
                }
            });
            
        }
    }
    
    private void adicionarBotaoPesquisa() {
        criarBotaoPesquisa();                
        int topo, esquerda, largura, altura;
                
        altura = (int)txtCaixaTexto.getSize().getHeight() - 1;
        largura = altura;
        
        esquerda = txtCaixaTexto.getWidth() - largura - 1;
        topo = 0;
        
        //btnPesquisa.setBounds(esquerda, topo, largura, altura);                
        //txtCaixaTexto.add(btnPesquisa);
        int constantePreenchimento = GridBagConstraints.BOTH;

        GridBagConstraints constraints;
        
        if (getRotuloPosicao() == PosicaoRotulo.TOPO)
            constraints = new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,
                                                                constantePreenchimento, new Insets(0, 0, 2, 0), 0, 0);
        else
            constraints = new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,
                                                              constantePreenchimento, new Insets(0, 0, 2, 0), 0, 0);

        this.add(btnPesquisa, constraints);
        updateUI();
    }

    private void removerBotaoPesquisa() {
        this.remove(btnPesquisa);
        btnPesquisa = null;
        updateUI();
    }
    
    public void setSelectionStart(int inicio) {
        txtCaixaTexto.setSelectionStart(inicio);
    }
    
    public void setSelectionEnd(int fim) {
        txtCaixaTexto.setSelectionEnd(fim);
    }
    
    public void select(int inicio, int fim) {
        txtCaixaTexto.select(inicio, fim);
    }
    
    private void dispararValorAlteradoListener(String valorAnterior, String valorAtual) {
        //Dispara ação valor alterado
        AlteracaoListener[] listeners = getAlteracaoListeners(); 
        
        //Dispara ação
        for (AlteracaoListener listener : listeners) {
            listener.alteracaoListener(new AlteracaoEvento(this, valorAnterior, valorAtual));
        }
    }  
    
    public Object getValue() {
        
    	return getText();
    }
  
    public void setValue(Object value) {        
        setText(value != null? value.toString(): null);
    }
    
    public class DocCampoTexto extends PlainDocument {
        private int tamanhoMaximo;
        // optional uppercase conversion
        private boolean tudoMaisculo = true;
        private boolean acentos=false;
        
        DocCampoTexto() {
            super();            
        }
        
        public void insertString(int offset, String  str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;            
            
            if (tamanhoMaximo > 0) {
                if ((getLength() + str.length()) > tamanhoMaximo)
                    return;
            }
            if(!acentos)
            	str = Texto.retirarAcentos(str); 
            
            if (tudoMaisculo) str = str.toUpperCase();
            super.insertString(offset, str, attr);
        }
        
        

        public void setTamanhoMaximo(int tamanhoMaximo) {
            this.tamanhoMaximo = tamanhoMaximo;
        }

        public int getTamanhoMaximo() {
            return tamanhoMaximo;
        }

        public void setTudoMaisculo(boolean tudoMaisculo) {
            this.tudoMaisculo = tudoMaisculo;
        }
        public boolean isAcentos() {
			return acentos;
		}
        public void setAcentos(boolean acentos) {
			this.acentos = acentos;
		}
        public boolean isTudoMaisculo() {
            return tudoMaisculo;
        }

        @Override
        protected void removeUpdate(AbstractDocument.DefaultDocumentEvent chng) {
            super.removeUpdate(chng);
            setAlterado(true);
            dispararValorAlteradoListener(valorAnterior, txtCaixaTexto.getText());
        }


        @Override
        protected void insertUpdate(AbstractDocument.DefaultDocumentEvent chng, AttributeSet attr) {
            super.insertUpdate(chng, attr);
            setAlterado(true);
            dispararValorAlteradoListener(valorAnterior, txtCaixaTexto.getText());
        }
    }

}
