package edu.cfip.util.desktop.ss.evento;

import java.awt.AWTEvent;


public class AlteracaoEvento extends AWTEvent {    
    private Object valorAnterior;
    private Object novoValor;

    public AlteracaoEvento(Object source, Object valorAnterior, Object novoValor) {
        super(source, 200801);
        this.valorAnterior = valorAnterior;
        this.novoValor = novoValor;
    }

    public void setValorAnterior(Object valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public Object getValorAnterior() {
        return valorAnterior;
    }

    public void setNovoValor(Object valorNovo) {
        this.novoValor = valorNovo;
    }

    public Object getNovoValor() {
        return novoValor;
    }
}
