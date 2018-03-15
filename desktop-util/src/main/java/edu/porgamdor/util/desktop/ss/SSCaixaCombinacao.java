package edu.porgamdor.util.desktop.ss;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import edu.porgamdor.util.desktop.ss.util.Reflexao;


/**
 *@beaninfo
 *   attribute: isContainer false
 * description: Caixa de combinação.
 *
 * @author  Frank Marlon M. dos Santos
 * @version 1.0 08/18/08
 */
public class SSCaixaCombinacao extends SSComponenteRotulado {
    private JComboBox cboCombo = new JComboBox();
    private List itens;
    private boolean primeiroElementoVazio = true;
    private String campoExibicao;
    private boolean somenteLeitura;
    private JTextField txtCampo; 
        
    public SSCaixaCombinacao() {
        super();
        setComponente(cboCombo);        
    }
    
    public void limparItens() {
        DefaultComboBoxModel model = (DefaultComboBoxModel)cboCombo.getModel();
        model.removeAllElements();
        updateUI();
    }
    public void setPreferredWidth(int width ) {
    	this.setPreferredSize(new Dimension(width, this.getPreferredSize().height));
    }
    public void setItens(List itens) {
        this.itens = itens;
        //Object value = getValue();
        carregarCombo();
        //setValue(value);
    }
    
    public void setItens(List itens, String campoExibicao) {        
        setCampoExibicao(campoExibicao);        
        setItens(itens);
    }

    public Object getItens() {
        return itens;
    }
    
    public void setItens(Object itens[]) {
        setItens(Arrays.asList(itens));
    }
    
    public void setItens(Object itens[], String campoExibicao) {
        setItens(Arrays.asList(itens), campoExibicao);
    }
    
    public void setValue(Object valor) {
        Object novoValor = valor;
        Object valorAtual = getValue();
        
        if (valorAtual == novoValor)
            return;
        if (valorAtual != null && valorAtual.equals(novoValor))
            return;
        
        ItemSelecao item = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)cboCombo.getModel();
        item = new ItemSelecao((valor == null? null: valor.toString()), valor);
        int posicao = model.getIndexOf(item);
        item = (ItemSelecao)model.getElementAt(posicao);
        
        cboCombo.setSelectedItem(item);
        if (txtCampo != null)
            txtCampo.setText(item.getRotulo());
        
    }
    
    public Object getValue() {
        ItemSelecao item = (ItemSelecao)cboCombo.getSelectedItem();
        if (item == null)
            return null;
        else
            return item.getValor();        
    }
    
    private void carregarCombo() {
        DefaultComboBoxModel model = (DefaultComboBoxModel)cboCombo.getModel();
        limparItens();
        Object[] array = new Object[0];
        
        if (isPrimeiroElementoVazio()) {
            model.addElement(new ItemSelecao("", null));
        }
        
        if (itens instanceof Collection)
            array = ((Collection)itens).toArray();
        if (itens instanceof List)
            array = ((List)itens).toArray();
        
        ItemSelecao item;
        String rotulo;
        
        for (Object obj : array) {
            rotulo = null;
            
            if (getCampoExibicao() != null) {
                Object valor = buscarCampo(getCampoExibicao(), obj);
                if (valor == null)
                    rotulo = null;
                else
                    rotulo = valor.toString();
            } else {
                rotulo = obj != null? obj.toString(): null;
            }
            
            item = new ItemSelecao(rotulo, obj);
            
            model.addElement(item);
        }
        
    }
    
    private Object buscarCampo(String nomeCampo, Object objeto) {                
        Object valor = null;
                        
        if (nomeCampo != null) {            
            try {
                valor = Reflexao.buscarValorCampoRecursivo(objeto, nomeCampo);                            
            } catch (NoSuchFieldException e) {
                System.out.println(nomeCampo + ": " + e.toString());
                return null;
            } catch (IllegalAccessException e) {
                System.out.println(nomeCampo + ": " + e.toString());
                return null;
            }
        }        
        return valor;
    }

    public void setPrimeiroElementoVazio(boolean primeiroElementoVazio) {
        this.primeiroElementoVazio = primeiroElementoVazio;
    }

    public boolean isPrimeiroElementoVazio() {
        return primeiroElementoVazio;
    }

    public void setCampoExibicao(String campoExibicao) {
        this.campoExibicao = campoExibicao;
    }

    public String getCampoExibicao() {
        return campoExibicao;
    }

    public String getText() {
        if (cboCombo.getSelectedItem() == null)
            return null;
        else
            return cboCombo.getSelectedItem().toString();
    }

    public void setText(String text) {
        ItemSelecao item;
        DefaultComboBoxModel model = (DefaultComboBoxModel)cboCombo.getModel();
        
        for (int i = 0; i < model.getSize(); i++) {
            item = (ItemSelecao)model.getElementAt(i);            
            if (item != null && item.getRotulo() != null && item.getRotulo().equalsIgnoreCase(text)) {
                cboCombo.setSelectedIndex(i);
                if (txtCampo != null)
                    txtCampo.setText(text);
            }
        }
    }

    public boolean isEditavel() {
        return cboCombo.isEditable();
    }

    public void setEditavel(boolean editavel) {
        cboCombo.setEditable(editavel);
    }
    
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        cboCombo.setEnabled(enabled);
    }
    
    public void addActionListener(ActionListener l) {
        cboCombo.addActionListener(l);
    }
    
    public void removeActionListener(ActionListener l) {
        cboCombo.removeActionListener(l);
    }
    
    public ActionListener[] getActionListeners() {
        return cboCombo.getActionListeners();
    }
    
    public void setPopupComRolagem(boolean rolagem) {
        if (rolagem)
            cboCombo.setUI(new SSCaixaCombinacaoUI());        
    }


    public void setSomenteLeitura(boolean somenteLeitura) {
        boolean alterado = this.somenteLeitura != somenteLeitura;
        this.somenteLeitura = somenteLeitura;
        if (alterado)
            atualizarVisualizacao();
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    private void atualizarVisualizacao() {
        if (somenteLeitura) {
            if (txtCampo == null) {
                txtCampo = new JTextField();
                txtCampo.setEditable(false);
                txtCampo.setFocusable(false);
                txtCampo.setForeground(SSFormulario.getCorFonteSomenteLeitura());                
                //txtCampo.setBackground(SystemColor.control);
            }
            ItemSelecao item = (ItemSelecao)cboCombo.getSelectedItem();
            if (item != null)
                txtCampo.setText(item.getRotulo());
            setComponente(txtCampo);            
        } else
            setComponente(cboCombo);
        getComponente().setEnabled(this.isEnabled());
    }
}

